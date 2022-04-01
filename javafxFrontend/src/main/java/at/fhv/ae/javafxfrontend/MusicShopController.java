package at.fhv.ae.javafxfrontend;

import at.fhv.ae.shared.AuthorizationException;
import at.fhv.ae.shared.dto.basket.BasketItemRemoteDTO;
import at.fhv.ae.shared.dto.release.DetailedReleaseRemoteDTO;
import at.fhv.ae.shared.dto.release.RecordingRemoteDTO;
import at.fhv.ae.shared.dto.release.ReleaseSearchResultDTO;
import at.fhv.ae.shared.rmi.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import javafx.util.Pair;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class MusicShopController {

    private final RemoteReleaseSearchService releaseSearchService;
    private final RemoteBasketService basketService;
    private final RemoteSellService sellService;

    // search fields
    @FXML TextField searchTitle;
    @FXML TextField searchArtist;
    @FXML ComboBox<String> searchGenre;

    // container for search table & details
    @FXML StackPane searchStackPane;

    // search table
    @FXML TableView<ReleaseSearchResultDTO> searchResultsView;
    @FXML TableColumn<ReleaseSearchResultDTO, Double> searchColPrice;
    @FXML TableColumn<ReleaseSearchResultDTO, String> searchColAddToBasket;

    // search details
    @FXML Label detailTitle;
    @FXML TableView<Pair<String, String>> detailView;
    @FXML TableView<RecordingRemoteDTO> detailRecordings;
    @FXML TableColumn<RecordingRemoteDTO, String> detailRecordingsColArtists;
    @FXML TableColumn<RecordingRemoteDTO, String> detailRecordingsColGenres;

    // basket
    @FXML TableView<BasketItemRemoteDTO> basketView;
    @FXML TableColumn<BasketItemRemoteDTO, QuantityColumnInfo> basketColQuantity;
    @FXML TableColumn<BasketItemRemoteDTO, Double> basketColPrice;
    @FXML TableColumn<BasketItemRemoteDTO, UUID> basketColRemove;

    public MusicShopController() throws NotBoundException, MalformedURLException, RemoteException {

        releaseSearchService = (RemoteReleaseSearchService) Naming.lookup("rmi://localhost/release-search-service");
        basketService = (RemoteBasketService) Naming.lookup("rmi://localhost/basket-service");
        sellService = (RemoteSellService) Naming.lookup("rmi://localhost/sell-service");
    }

    private <T> String formatCurrency(T amount) {
        return DecimalFormat.getCurrencyInstance(Locale.GERMANY).format(amount);
    }

    private <S, T> Callback<TableColumn<S,T>, TableCell<S,T>> currencyCellFactory() {
        return column -> new TableCell<>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);

                if(item == null || empty)
                    setText(null);
                else
                    setText(formatCurrency(item));
            }
        };
    }

    @FXML
    public void initialize() throws RemoteException {

        // populate combo box selection
        searchGenre.getItems().setAll(releaseSearchService.knownGenres());

        // double click / hit enter on a search result for details
        Runnable userActionOnSearchResults = () -> {
            ReleaseSearchResultDTO selectedResult = searchResultsView.getSelectionModel().getSelectedItem();
            if(selectedResult != null) {
                try {
                    showDetailsOf(selectedResult);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        searchResultsView.setOnKeyReleased(event -> {
            if(event.getCode().equals(KeyCode.ENTER))
                userActionOnSearchResults.run();
        });
        searchResultsView.setOnMouseClicked(event -> {
            if(event.getClickCount() >= 2)
                userActionOnSearchResults.run();
        });

        // format price columns as currency
        searchColPrice.setCellFactory(currencyCellFactory());
        basketColPrice.setCellFactory(currencyCellFactory());

        // search result table - add to basket button column
        searchColAddToBasket.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if(item == null || empty) {
                    this.setGraphic(null);
                    return;
                }

                var button = new Button("Add");
                button.setOnAction(e -> {
                    try {
                        addToBasket(this.getItem());
                    } catch (RemoteException remoteException) {
                        throw new RuntimeException(remoteException);
                    }
                });

                this.setGraphic(button);
            }
        });

        // search detail table
        detailRecordingsColArtists.setCellValueFactory(
                data -> new ReadOnlyStringWrapper(String.join(", ", data.getValue().getArtists()))
        );
        detailRecordingsColGenres.setCellValueFactory(
                data -> new ReadOnlyStringWrapper(String.join(", ", data.getValue().getGenres()))
        );

        // basket table - quantity column
        basketColQuantity.setCellValueFactory(data -> new SimpleObjectProperty<>(
                new QuantityColumnInfo(data.getValue().getReleaseId(), () -> 1, () -> data.getValue().getStock(), data.getValue().getQuantity())
        ));

        basketColQuantity.setCellFactory(column -> new TableCell<>() {
            @Override
            public void updateItem(QuantityColumnInfo item, boolean empty) {
                super.updateItem(item, empty);

                if(item == null || empty) {
                    this.setGraphic(null);
                    return;
                }

                var spinner = new Spinner<Integer>(item.min(), item.max(), item.value());
                spinner.valueProperty().addListener((observable, oldVal, newVal) -> {
                    item.setValue(newVal);
                    try {
                        basketService.changeQuantityOfItem(item.productId(), newVal);
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                });
                this.setGraphic(spinner);
            }
        });

        // basket table - remove button
        basketColRemove.setCellFactory(column -> new TableCell<>() {
            @Override
            public void updateItem(UUID item, boolean empty) {
                super.updateItem(item, empty);

                if(item == null || empty) {
                    this.setGraphic(null);
                    return;
                }

                var button = new Button("Remove");
                button.setOnAction(e -> {
                    try {
                        basketService.removeItemFromBasket(this.getItem());
                        fetchBasket();
                    } catch (RemoteException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                this.setGraphic(button);
            }
        });

        // initialize basket
        fetchBasket();
    }

    public void search() throws RemoteException {
         searchResultsView.getItems().setAll(
                 releaseSearchService.query(
                         searchTitle.getText(),
                         searchArtist.getText(),
                         searchGenre.getValue()));
    }

    public void reset() {
        searchTitle.clear();
        searchArtist.clear();
        searchGenre.setValue("");
        searchResultsView.getItems().clear();
    }

    public void showDetailsOf(ReleaseSearchResultDTO result) throws RemoteException {

        DetailedReleaseRemoteDTO details = releaseSearchService.getDetails(UUID.fromString(result.getId()));

        detailTitle.setText(details.getTitle());

        detailView.getItems().setAll(List.of(
                new Pair<>("Price", formatCurrency(details.getPrice())),
                new Pair<>("Medium", details.getMedium()),
                new Pair<>("Stock", Integer.toString(details.getStock()))));

        detailRecordings.getItems().setAll(details.getRecordings());

        switchSearchView();

    }

    public void switchSearchView() {
        searchStackPane.getChildren().add(1, searchStackPane.getChildren().remove(0));
    }

    private void fetchBasket() throws RemoteException {
        basketView.getItems().setAll(basketService.itemsInBasket());
    }

    public void addToBasket(String id) throws RemoteException {
        basketService.addItemToBasket(UUID.fromString(id),1);
        fetchBasket();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Release added");
            alert.setContentText("Release added");
            alert.showAndWait();
    }

    public void addToBasket() throws RemoteException {
        addToBasket(searchResultsView.getSelectionModel().getSelectedItem().getId());
    }

    public void clearBasket() throws RemoteException {
        basketService.clearBasket();
        fetchBasket();
    }

    public void sell() throws RemoteException {

        boolean success = sellService.sellItemsInBasket();

        Alert alert = new Alert(success ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
        alert.setTitle(success ? "Items sold" : "Error confirming Sale");
        alert.setContentText(alert.getTitle());
        alert.showAndWait();

        fetchBasket();
    }
}
