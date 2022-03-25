package at.fhv.ae.javafxfrontend;

import at.fhv.ae.shared.dto.release.ReleaseSearchResultDTO;
import at.fhv.ae.shared.rmi.ReleaseSearchService;
import at.fhv.ae.shared.rmi.RemoteBasketService;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.StageStyle;
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

    @FXML
    private TextField searchTitle;

    @FXML
    private TextField searchArtist;

    @FXML
    private TextField searchGenre;

    @FXML
    private StackPane searchStackPane;

    @FXML
    private TableView<ReleaseSearchResultDTO> searchResultsView;

    @FXML
    private TableColumn<ReleaseSearchResultDTO, Double> colPrice;

    @FXML
    private TableColumn<ReleaseSearchResultDTO, String> basket;

    @FXML
    private TableView<Pair<String, String>> detailView;

    public MusicShopController() throws NotBoundException, MalformedURLException, RemoteException {

        searchService = (ReleaseSearchService) Naming.lookup("rmi://localhost/release-search-service");
        basketService = (RemoteBasketService) Naming.lookup("rmi://localhost/basket-service");
    }

    @FXML
    public void initialize() {
        Runnable userActionOnSearchResults = () -> {
            ReleaseSearchResultDTO selectedResult = searchResultsView.getSelectionModel().getSelectedItem();
            if(selectedResult != null)
                searchDetailsOf(selectedResult);
        };

        searchResultsView.setOnKeyReleased(event -> {
            if(event.getCode().equals(KeyCode.ENTER))
                userActionOnSearchResults.run();
        });
        searchResultsView.setOnMouseClicked(event -> {
            if(event.getClickCount() >= 2)
                userActionOnSearchResults.run();
        });

        // format price column as currency
        colPrice.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);

                if(item == null || empty)
                    setText(null);
                else
                    setText(DecimalFormat.getCurrencyInstance(Locale.GERMANY).format(item));
            }
        });

        basket.setCellFactory(column -> {
            var cell = new TableCell<ReleaseSearchResultDTO, String>();
            var button = new Button("add");

            EventHandler<ActionEvent> handler = e -> {
                try {
                    addToBasket(cell.getItem());
                } catch (RemoteException remoteException) {
                    throw new RuntimeException(remoteException);
                }
            };

            button.setOnAction(handler);
            cell.setGraphic(button);
            return cell;
        });

        basket.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getId()));
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

    public void searchDetailsOf(ReleaseSearchResultDTO result) {

        detailView.getItems().setAll(List.of(

                new Pair<>("Price", DecimalFormat.getCurrencyInstance(Locale.GERMANY).format(result.getPrice())),
                new Pair<>("Medium", result.getMedium()),
                new Pair<>("Stock", Integer.toString(result.getStock()))));

        switchSearchView();
    }

    public void switchSearchView() {
        searchStackPane.getChildren().add(1, searchStackPane.getChildren().remove(0));
    }

    public void addToBasket(String id) throws RemoteException {
        basketService.addItemToBasket(UUID.fromString(id),1);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Recording added");
            alert.setContentText("Recording added");
            alert.showAndWait();
    }

    public void addToBasket(ActionEvent event) throws RemoteException {
        addToBasket(searchResultsView.getSelectionModel().getSelectedItem().getId());
    }


}
