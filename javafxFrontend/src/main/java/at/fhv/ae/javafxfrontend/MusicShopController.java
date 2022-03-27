package at.fhv.ae.javafxfrontend;

import at.fhv.ae.shared.dto.basket.BasketItemRemoteDTO;
import at.fhv.ae.shared.dto.release.DetailedReleaseRemoteDTO;
import at.fhv.ae.shared.dto.release.RecordingRemoteDTO;
import at.fhv.ae.shared.dto.release.ReleaseSearchResultDTO;
import at.fhv.ae.shared.rmi.ReleaseSearchService;
import at.fhv.ae.shared.rmi.RemoteSellService;
import javafx.beans.property.SimpleObjectProperty;
import at.fhv.ae.shared.rmi.RemoteBasketService;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

    private final ReleaseSearchService searchService;

    private final RemoteBasketService basketService;

    private final RemoteSellService sellService;

    @FXML
    private TextField searchTitle;

    @FXML
    private TextField searchArtist;

    @FXML
    private TextField searchGenre;

    @FXML
    private StackPane searchStackPane; // contains result table and detailed result view to switch between

    @FXML
    private TableView<ReleaseSearchResultDTO> searchResultsView;
    @FXML
    private TableColumn<ReleaseSearchResultDTO, Double> searchColPrice;
    @FXML
    private TableColumn<ReleaseSearchResultDTO, String> searchColAddToBasket;

    @FXML
    private Label detailTitle;

    @FXML
    private TableView<Pair<String, String>> detailView;

    @FXML
    private TableView<RecordingRemoteDTO> detailRecordings;

    @FXML
    private TableColumn<RecordingRemoteDTO, String> detailArtists;

    @FXML
    private TableColumn<RecordingRemoteDTO, String> detailGenres;

    @FXML
    private TableView<BasketItemRemoteDTO> basketView;
    @FXML
    private TableColumn<BasketItemRemoteDTO, QuantityColumnInfo> basketColQuantity;
    @FXML
    private TableColumn<BasketItemRemoteDTO, Double> basketColPrice;

    public MusicShopController() throws NotBoundException, MalformedURLException, RemoteException {

        searchService = (ReleaseSearchService) Naming.lookup("rmi://localhost/release-search-service");
        basketService = (RemoteBasketService) Naming.lookup("rmi://localhost/basket-service");
        sellService = (RemoteSellService) Naming.lookup("rmi://localhost/sell-service");
    }

    private <S, T> Callback<TableColumn<S,T>, TableCell<S,T>> currencyCellFactory() {
        return column -> new TableCell<>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);

                if(item == null || empty)
                    setText(null);
                else
                    setText(DecimalFormat.getCurrencyInstance(Locale.GERMANY).format(item));
            }
        };
    }

    @FXML
    public void initialize() throws RemoteException {

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

        detailArtists.setCellValueFactory(data -> new ReadOnlyStringWrapper(String.join(", ", data.getValue().getArtists())));

        detailGenres.setCellValueFactory(data -> new ReadOnlyStringWrapper(String.join(", ", data.getValue().getGenres())));

        // search result table - add to basket button column
        searchColAddToBasket.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getId()));

        searchColAddToBasket.setCellFactory(column -> {
            var cell = new TableCell<ReleaseSearchResultDTO, String>();
            var button = new Button("Add");

            button.setOnAction(e -> {
                try {
                    addToBasket(cell.getItem());
                } catch (RemoteException remoteException) {
                    throw new RuntimeException(remoteException);
                }
            });

            cell.setGraphic(button);
            return cell;
        });

        // basket table - quantity column
        basketColQuantity.setCellValueFactory(data -> new SimpleObjectProperty<>(
                new QuantityColumnInfo(data.getValue().getReleaseId(), () -> 1, () -> data.getValue().getStock(), data.getValue().getQuantity())
        ));

        basketColQuantity.setCellFactory(column -> new TableCell<>() {
            @Override
            public void updateItem(QuantityColumnInfo item, boolean empty) {
                super.updateItem(item, empty);

                if(item == null || empty)
                    return;

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

        basketView.getItems().setAll(basketService.itemsInBasket());
    }

    public void search() throws RemoteException {
         searchResultsView.getItems().setAll(
                 searchService.query(
                         searchTitle.getText(),
                         searchArtist.getText(),
                         searchGenre.getText()));
    }

    public void reset() {
        searchTitle.clear();
        searchArtist.clear();
        searchGenre.clear();
        searchResultsView.getItems().clear();
    }

    public void showDetailsOf(ReleaseSearchResultDTO result) throws RemoteException {

        DetailedReleaseRemoteDTO details = searchService.getDetails(UUID.fromString(result.getId()));

        detailTitle.setText(details.getTitle());

        detailView.getItems().setAll(List.of(
                new Pair<>("Price", DecimalFormat.getCurrencyInstance(Locale.GERMANY).format(details.getPrice())),
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

    public void addToBasket(ActionEvent event) throws RemoteException {
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
