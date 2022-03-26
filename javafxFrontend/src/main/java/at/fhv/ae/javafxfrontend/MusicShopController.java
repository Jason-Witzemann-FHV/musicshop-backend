package at.fhv.ae.javafxfrontend;

import at.fhv.ae.shared.dto.release.ReleaseSearchResultDTO;
import at.fhv.ae.shared.rmi.ReleaseSearchService;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.util.Pair;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.util.List;

public class MusicShopController {

    private final ReleaseSearchService searchService;

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
    private TableView<Pair<String, String>> detailView;

    @FXML
    private TableView<ReleaseSearchResultDTO> basketTable;
    @FXML
    TableColumn<ReleaseSearchResultDTO, BoundedInteger> colQuantity;

    public MusicShopController() throws NotBoundException, MalformedURLException, RemoteException {

        searchService = (ReleaseSearchService) Naming.lookup("rmi://localhost/release-search-service");
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
                    setText(DecimalFormat.getCurrencyInstance().format(item));
            }
        });

        colQuantity.setCellValueFactory(data -> new SimpleObjectProperty<>(
                new BoundedInteger(() -> 1, () -> data.getValue().getStock(), 1)
        ));

        colQuantity.setCellFactory(column -> new TableCell<>() {
            @Override
            public void updateItem(BoundedInteger item, boolean empty) {
                if(item == null || empty)
                    return;
                var spinner = new Spinner<Integer>(item.min(), item.max(), item.value());
                this.setGraphic(spinner);
                spinner.valueProperty().addListener((observable, oldVal, newVal) -> item.setValue(newVal));
            }
        });


        // basketTable.getItems().add(new ReleaseSearchResultDTO("Never gonna give you up", "MC", 3, 4.40));
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
                new Pair<>("Price", DecimalFormat.getCurrencyInstance().format(result.getPrice())),
                new Pair<>("Medium", result.getMedium()),
                new Pair<>("Stock", Integer.toString(result.getStock()))));

        switchSearchView();
    }

    public void switchSearchView() {
        searchStackPane.getChildren().add(1, searchStackPane.getChildren().remove(0));
    }
}
