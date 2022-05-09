package at.fhv.ae.javafxfrontend;

import at.fhv.ae.shared.AuthorizationException;
import at.fhv.ae.shared.dto.basket.BasketItemRemoteDTO;
import at.fhv.ae.shared.dto.basket.CustomerSearchResponseDTO;
import at.fhv.ae.shared.dto.news.NewsRemoteDTO;
import at.fhv.ae.shared.dto.release.DetailedReleaseRemoteDTO;
import at.fhv.ae.shared.dto.release.RecordingRemoteDTO;
import at.fhv.ae.shared.dto.release.ReleaseSearchResultDTO;
import at.fhv.ae.shared.dto.sale.ItemRemoteDTO;
import at.fhv.ae.shared.dto.sale.SaleItemsRemoteDTO;
import at.fhv.ae.shared.services.*;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Pair;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.*;

public class MusicShopController {

    private BeanSession session;
    private RemoteReleaseSearchService releaseSearchService;
    private RemoteBasketService basketService;
    private RemoteSellService sellService;
    private RemoteCustomerSearchService customerSearchService;
    private RemoteBroadcastService broadcastService;
    private RemoteNewsPollingService newsPublisherService;
    private RemoteReturnReleaseService returnReleaseService;

    private static final double TAX_RATE = 0.2;

    static ObservableList<NewsRemoteDTO> newsList;

    @FXML TableView<NewsRemoteDTO> newsView;
    @FXML TableColumn<NewsRemoteDTO, LocalDateTime> newsDateColumn;
    // search fields
    @FXML TextField searchTitle;
    @FXML TextField searchArtist;
    @FXML ComboBox<String> searchGenre;

    // container for search table & details
    @FXML StackPane searchStackPane;

    // search table
    @FXML TableView<ReleaseSearchResultDTO> searchReleaseResultsView;
    @FXML TableColumn<ReleaseSearchResultDTO, Double> searchColPrice;
    @FXML TableColumn<ReleaseSearchResultDTO, String> searchColAddToBasket;

    // search details
    @FXML Label detailTitle;
    @FXML TableView<Pair<String, String>> detailView;
    @FXML TableView<RecordingRemoteDTO> detailRecordings;
    @FXML TableColumn<RecordingRemoteDTO, String> detailRecordingsColArtists;
    @FXML TableColumn<RecordingRemoteDTO, String> detailRecordingsColGenres;

    // container for sale table & details
    @FXML StackPane saleStackPane;

    // sales table
    @FXML TableView<SaleItemsRemoteDTO> saleResultsView;
    @FXML TableColumn<SaleItemsRemoteDTO, Double> saleColPrice;
    @FXML TextField searchSalesNo;

    // sale detail
    @FXML Label saleNumber;
    @FXML TableView <Pair<String, String>> saleGeneralInfo;
    @FXML TableView<ItemRemoteDTO> saleItems;
    @FXML TableColumn<ItemRemoteDTO, Double> itemColPrice;
    @FXML TableColumn<String, String> returnedAmount;


    // basket
    @FXML TableView<BasketItemRemoteDTO> basketView;

    @FXML TableColumn<BasketItemRemoteDTO, QuantityColumnInfo> basketColQuantity;
    @FXML TableColumn<BasketItemRemoteDTO, Double> basketColPrice;
    @FXML TableColumn<BasketItemRemoteDTO, UUID> basketColRemove;
    @FXML TextField customerSearchFirstName;
    @FXML TextField customerSearchSurname;
    @FXML TableView<CustomerSearchResponseDTO> customerSearchView;
    @FXML Label netPrice;
    @FXML Label taxPrice;
    @FXML Label grossPrice;


    // buttons and tabs - authorization
    @FXML TabPane tabPane;
    @FXML Tab searchTab;
    @FXML Tab basketTab;
    @FXML Tab salesHistoryTab;
    @FXML Tab broadcastTab;
    @FXML Button toBasketInDetails;
    @FXML Button clearBasketButton;
    @FXML Button sellBasketButton;


    // fields and button - broadcast
    @FXML ComboBox<String> topicCombobox;
    @FXML DatePicker dateOfEvent;
    @FXML TextField messageTitle;
    @FXML TextArea message;
    @FXML Tab newsTab;



    public void setSession(BeanSession session) throws RemoteException {
        this.session = session;

        try {
            releaseSearchService = session.remoteReleaseService();

            // populate combo box selection, add an empty element to remove selection from drop-down
            var genres = releaseSearchService.knownGenres();
            genres.add(0, "");
            searchGenre.getItems().setAll(genres);

        } catch (AuthorizationException ignored) {
            // Hide unauthorized
            tabPane.getTabs().remove(searchTab);
        }

        try {
            basketService = session.remoteBasketService();

            fetchBasket();
        } catch (AuthorizationException ignored) {
            // Hide unauthorized
            toBasketInDetails.setVisible(false);
            searchColAddToBasket.setVisible(false);
            tabPane.getTabs().remove(basketTab);
        }

        try {
            sellService = session.remoteSellService();
            returnReleaseService = session.remoteReturnReleaseService();
            saleSearch();

        } catch (AuthorizationException ignored) {
            // Hide unauthorized
            clearBasketButton.setVisible(false);
            sellBasketButton.setVisible(false);
            tabPane.getTabs().remove(salesHistoryTab);
        }

        try {
            customerSearchService = session.remoteCustomerSearchService();

        } catch (AuthorizationException ignored) {

        }

        try {
            broadcastService = session.remoteBroadcastService();
            topicCombobox.getItems().setAll(List.of("SystemTopic", "RockTopic", "PopTopic"));
        } catch (AuthorizationException ignored) {
            tabPane.getTabs().remove(broadcastTab);
        }

        try {
            newsPublisherService = session.remoteNewsPublisherService();

            var startNews = newsPublisherService.pollForNewNews(Long.MIN_VALUE);
            newsView.getItems().setAll(startNews);

            new Timer().scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    var pollTime = newsView.getItems()
                            .stream()
                            .mapToLong(NewsRemoteDTO::getPublishedTimeStamp)
                            .max()
                            .orElse(Long.MIN_VALUE);
                    var newNews = newsPublisherService.pollForNewNews(pollTime);

                    if (!newNews.isEmpty()) {
                        newNews.forEach(n -> newsView.getItems().add(0, n));
                        newsTab.setStyle("-fx-background-color: #FA7878");
                    }
                }
            }, 5000, 5000);

        } catch (AuthorizationException e) {
            tabPane.getTabs().remove(newsTab);
        }

    }

    public void logout(ActionEvent event) throws IOException {
        // Remove Session
        this.session = null;

        // Current stage
        final Node source = (Node) event.getSource();
        final Stage currentStage = (Stage) source.getScene().getWindow();

        // Load Login Stage
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();

        // Close Soundkraut stage
        currentStage.close();
    }

    private <T> String formatCurrency(T amount) {
        return DecimalFormat.getCurrencyInstance(Locale.GERMANY).format(amount);
    }

    private <S, T> Callback<TableColumn<S, T>, TableCell<S, T>> currencyCellFactory() {
        return column -> new TableCell<>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty)
                    setText(null);
                else
                    setText(formatCurrency(item));
            }
        };
    }

    @FXML
    public void initialize() {
        newsView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        basketView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        customerSearchView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        searchReleaseResultsView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        saleResultsView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        saleGeneralInfo.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        saleItems.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        detailView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        detailRecordings.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // double click / hit enter on a search result for details
        Runnable userActionOnSearchResults = () -> {
            ReleaseSearchResultDTO selectedResult = searchReleaseResultsView.getSelectionModel().getSelectedItem();
            if (selectedResult != null) {
                try {
                    loadDetailsOf(selectedResult);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        // double click / hit enter on a sale result for details
        Runnable userActionOnSaleResults = () -> {
            SaleItemsRemoteDTO selectedResult = saleResultsView.getSelectionModel().getSelectedItem();
            if (selectedResult != null) {
                try {
                    loadDetailsOf(selectedResult);
                    switchSaleView();
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        searchReleaseResultsView.setOnKeyReleased(event -> {
            if (event.getCode().equals(KeyCode.ENTER))
                userActionOnSearchResults.run();
        });
        searchReleaseResultsView.setOnMouseClicked(event -> {
            if (event.getClickCount() >= 2)
                userActionOnSearchResults.run();
        });

        saleResultsView.setOnKeyReleased(event -> {
            if (event.getCode().equals(KeyCode.ENTER))
                userActionOnSaleResults.run();
        });
        saleResultsView.setOnMouseClicked(event -> {
            if (event.getClickCount() >= 2)
                userActionOnSaleResults.run();
        });

        // format price columns as currency
        searchColPrice.setCellFactory(currencyCellFactory());
        saleColPrice.setCellFactory(currencyCellFactory());
        itemColPrice.setCellFactory(currencyCellFactory());
        basketColPrice.setCellFactory(currencyCellFactory());

        // search result table - add to basket button column
        searchColAddToBasket.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    this.setGraphic(null);
                    return;
                }

                var button = new Button("+");
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
                new QuantityColumnInfo(data.getValue().getReleaseId(), 1, data.getValue().getStock(), data.getValue().getQuantity())
        ));

        basketColQuantity.setCellFactory(column -> new TableCell<>() {
            @Override
            public void updateItem(QuantityColumnInfo item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    this.setGraphic(null);
                    return;
                }


                var spinner = new Spinner<Integer>(item.min(), Math.max(item.stock(), item.value()), item.value());
                var spinnerValueFactory = (SpinnerValueFactory.IntegerSpinnerValueFactory) spinner.getValueFactory();

                spinner.valueProperty().addListener((observable, oldVal, newVal) -> {
                    // reduce max so you can't go back up
                    if (item.stock() < spinnerValueFactory.getMax() && newVal < spinnerValueFactory.getMax()) {
                        spinnerValueFactory.setMax(newVal);
                    }

                    item.setValue(newVal);
                    try {
                        basketService.changeQuantityOfItem(item.productId(), newVal);
                        calcPrice();
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

                if (item == null || empty) {
                    this.setGraphic(null);
                    return;
                }

                var button = new Button("-");
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

        // newsTab opened - change color to default color
        newsTab.setOnSelectionChanged(event -> newsTab.setStyle(null));


        saleItems.setOnMouseClicked(event -> {
            if (event.getClickCount() >= 2)

                returnDialog(Integer.parseInt(saleNumber.getText()),saleItems.getSelectionModel().getSelectedItem().getItemId());
                //showSelectedSale();
        });
    }

    public void returnDialog(int saleNumber,  UUID itemId){
        TextInputDialog dialog = new TextInputDialog();

        dialog.setTitle("Return");
        dialog.setHeaderText(saleItems.getSelectionModel().getSelectedItem().getTitle());
        dialog.setContentText("Amount:");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(amount -> {
            if(Integer.parseInt(amount) > 0) {

                try {
                    //Here
                    returnReleaseService.returnRelease(saleNumber,itemId, Integer.parseInt(amount));
                    var updatedSale = sellService.searchSale(saleNumber);
                    loadDetailsOf(updatedSale);

                }catch (Exception e){
                    dialog.close();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Amount");
                    alert.setContentText("Returning due to amount not possible");
                    alert.showAndWait();
            }
        }});
    }

    public void saleSearch() {
        var sales = sellService.allSales();
        saleResultsView.getItems().setAll(sales);
    }

    public void showSelectedSale() {
        SaleItemsRemoteDTO selectedResult = saleResultsView.getSelectionModel().getSelectedItem();
        if (selectedResult != null) {
            try {
                loadDetailsOf(selectedResult);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void releaseSearch() {
        searchReleaseResultsView.getItems().setAll(
                releaseSearchService.query(
                        searchTitle.getText(),
                        searchArtist.getText(),
                        searchGenre.getValue()));
    }

    public void releaseReset() {
        searchTitle.clear();
        searchArtist.clear();
        searchGenre.setValue("");
        searchReleaseResultsView.getItems().clear();
    }

    public void loadDetailsOf(ReleaseSearchResultDTO result) throws RemoteException {

        DetailedReleaseRemoteDTO details = releaseSearchService.getDetails(UUID.fromString(result.getId()));

        detailTitle.setText(details.getTitle());
        detailView.getItems().setAll(List.of(
                new Pair<>("Price", formatCurrency(details.getPrice())),
                new Pair<>("Medium", details.getMedium()),
                new Pair<>("Stock", Integer.toString(details.getStock()))));
        detailRecordings.getItems().setAll(details.getRecordings());
        switchSearchView();
    }

    // overloading Methodname
    public void loadDetailsOf(SaleItemsRemoteDTO details) throws RemoteException {

        // textfield can only be string, therefore cast int to String
        saleNumber.setText(Integer.toString(details.getSaleNumber()));
        saleGeneralInfo.getItems().setAll(List.of(
                // is Pair<String, String>, therefore cast int to String
                new Pair<>("Sale number",Integer.toString(details.getSaleNumber())),
                new Pair<>("Date of Sale", details.getDateOfSale()),
                new Pair<>("Customer", details.getCustomerId()),
                new Pair<>("Total price", formatCurrency(details.getTotalPrice()))));
        saleItems.getItems().setAll(details.getItems());
    }

    public void switchSearchView() {
        searchStackPane.getChildren().add(1, searchStackPane.getChildren().remove(0));
    }

    public void switchSaleView() {
        saleStackPane.getChildren().add(1,saleStackPane.getChildren().remove(0));
    }

    private void fetchBasket() throws RemoteException {
        basketView.getItems().setAll(basketService.itemsInBasket());
        calcPrice();
    }

    private void calcPrice() throws RemoteException {
        List<BasketItemRemoteDTO> basketItems = basketService.itemsInBasket();

        double dnetPrice = basketItems.stream().mapToDouble(b -> b.getPrice() * b.getQuantity()).sum();
        double dtaxPrice = dnetPrice * TAX_RATE;
        double dgrossPrice = dnetPrice + dtaxPrice;
        netPrice.setText(formatCurrency(dnetPrice) + " Net");
        taxPrice.setText("+ " + formatCurrency(dtaxPrice) + " Tax");
        grossPrice.setText(formatCurrency(dgrossPrice) + " Total");
    }

    public void addToBasket(String id) throws RemoteException {
        basketService.addItemToBasket(UUID.fromString(id), 1);
        fetchBasket();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Release added");
        alert.setContentText("Release added");
        alert.showAndWait();
    }

    public void addToBasket() throws RemoteException {
        addToBasket(searchReleaseResultsView.getSelectionModel().getSelectedItem().getId());
    }

    public void clearBasket() throws RemoteException {
        basketService.clearBasket();
        fetchBasket();
    }

    public void sell() throws RemoteException {

        ObjectId customerId = Optional.ofNullable(customerSearchView.getSelectionModel().getSelectedItem())
                .map(CustomerSearchResponseDTO::getId)
                .orElse(null);

        boolean success = false;
        try {
            success = sellService.sellItemsInBasket(customerId); // todo assign customer
        } catch (Exception e) {
            e.printStackTrace();
        }

        Alert alert = new Alert(success ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
        alert.setTitle(success ? "Items sold" : "Error confirming Sale");
        alert.setContentText(alert.getTitle());
        alert.showAndWait();

        customerSearchView.getItems().clear();
        fetchBasket();
        saleSearch();
    }

    public void searchCustomer() {

        List<CustomerSearchResponseDTO> customers = customerSearchService.findCustomerByName(customerSearchFirstName.getText(), customerSearchSurname.getText());
        customerSearchView.getItems().setAll(customers);
    }


    public void searchSaleNumber() {
        try {
            int saleNum = Integer.parseInt(searchSalesNo.getText());
            var sale = sellService.searchSale(saleNum);
            if (sale == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Could not find sale.");
                alert.setContentText(alert.getTitle());
                alert.showAndWait();
            } else {
                saleResultsView.getItems().setAll(sale);
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Search for a number!");
            alert.setContentText(alert.getTitle());
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Could not find sale.");
            alert.setContentText(alert.getTitle());
            alert.showAndWait();
        }
    }

    public void searchSaleReset() {
        searchSalesNo.clear();
        saleSearch();
    }

    public void sendMessage() {
        boolean success = false;

        try {
            if( topicCombobox.getValue() == null || dateOfEvent.getValue() == null ||
                topicCombobox.getValue().equals("") || messageTitle.getText().isEmpty() ||
                    message.getText().isEmpty()) {

                throw new IllegalArgumentException("Not all message fields are filled in");

            } else {
                broadcastService.broadcast(topicCombobox.getValue(),
                        messageTitle.getText(),
                        message.getText(),
                        dateOfEvent.getValue().atStartOfDay());

                topicCombobox.setValue("");
                messageTitle.clear();
                message.clear();
                dateOfEvent.getEditor().clear();

                success = true;
            }

        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        Alert alert = new Alert(success ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
        alert.setTitle(success ? "Message sent" : "Error by sending message");
        alert.setContentText(alert.getTitle());
        alert.showAndWait();
    }
}
