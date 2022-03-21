package at.fhv.ae.javafxfrontend;

import at.fhv.ae.shared.dto.release.ReleaseSearchResultDTO;
import at.fhv.ae.shared.rmi.ReleaseSearchService;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class MusicShopController {

    private final ReleaseSearchService searchService;

    @FXML
    private TextField searchTitle;

    @FXML
    private TextField searchArtist;

    @FXML
    private TextField searchGenre;

    @FXML
    private TableView<ReleaseSearchResultDTO> searchResultsView;
    @FXML private TableColumn<ReleaseSearchResultDTO, Double> colPrice;


    public MusicShopController() throws NotBoundException, MalformedURLException, RemoteException {

        searchService = (ReleaseSearchService) Naming.lookup("rmi://localhost/release-search-service");
    }

    @FXML
    public void initialize() {

        // bind each column to a DTO property named by the userData FXML attribute
        for(TableColumn<ReleaseSearchResultDTO, ?> col: searchResultsView.getColumns()) {
            col.setCellValueFactory(new PropertyValueFactory<>(col.getUserData().toString()));
        }

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
    }

    public void search() throws RemoteException {
         searchResultsView.getItems().setAll(
                 searchService.query(
                         searchTitle.getText(),
                         searchArtist.getText(),
                         searchGenre.getText()));
    }

}
