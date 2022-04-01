package at.fhv.ae.javafxfrontend;

import at.fhv.ae.shared.rmi.RemoteReleaseSearchService;
import at.fhv.ae.shared.rmi.RemoteSession;
import at.fhv.ae.shared.rmi.RemoteSessionFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.awt.event.InputEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

public class LoginController {

    private final RemoteSessionFactory sessionFactory = null;

    @FXML
    TextField userName;
    @FXML
    TextField password;

    @FXML
    Button loginButton;

    public LoginController() throws NotBoundException, MalformedURLException, RemoteException {
        //sessionFactory = (RemoteSessionFactory) Naming.lookup("rmi://localhost/music-shop");
    }

    public void login(MouseEvent mouseEvent) throws IOException {
        System.out.println(userName.getText());
       // Optional<RemoteSession> session = sessionFactory.logIn(userName.getText(),password.getText());

       if(true){
           final Node source = (Node) mouseEvent.getSource();
           final Stage currentStage = (Stage) source.getScene().getWindow();
            currentStage.close();

           FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MusicShop.fxml"));
           Parent root = (Parent)fxmlLoader.load();
           MusicShopController controller = fxmlLoader.<MusicShopController>getController();
           //controller.setSession(session.get());
           Scene scene = new Scene(root);
           Stage stage = new Stage();
           stage.setScene(scene);
           stage.setTitle("Soundkraut");
           stage.show();




       } else {

           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Could not log in");
           alert.setContentText(alert.getTitle());
           alert.show();
       }
    }

}
