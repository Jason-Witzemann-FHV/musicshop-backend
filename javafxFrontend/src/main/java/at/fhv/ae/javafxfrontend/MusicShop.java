package at.fhv.ae.javafxfrontend;

import at.fhv.ae.shared.dto.release.ReleaseSearchResultDTO;
import at.fhv.ae.shared.rmi.ReleaseSearchService;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class MusicShop {

    private final ReleaseSearchService searchService;

    @FXML
    private TextField searchTitle;

    @FXML
    private TextField searchArtist;

    @FXML
    private TextField searchGenre;

    @FXML
    private ListView<ReleaseSearchResultDTO> searchResultsView;
    private final ObservableList<ReleaseSearchResultDTO> searchResults;

    public MusicShop() throws NotBoundException, MalformedURLException, RemoteException {

        searchService = (ReleaseSearchService) Naming.lookup("rmi://localhost/release-search-service");

        searchResults = new ObservableListWrapper<>(new ArrayList<>());
    }

    @FXML
    public void initialize() {
        searchResultsView.setItems(searchResults);
    }

    public void search() throws RemoteException {
         searchResults.setAll(
                 searchService.query(
                         searchTitle.getText(),
                         searchArtist.getText(),
                         searchGenre.getText()));
    }

}
