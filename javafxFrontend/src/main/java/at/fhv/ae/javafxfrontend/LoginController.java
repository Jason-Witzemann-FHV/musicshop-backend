package at.fhv.ae.javafxfrontend;

import at.fhv.ae.shared.rmi.RemoteSession;
import at.fhv.ae.shared.rmi.RemoteSessionFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Properties;

public class LoginController {

    private RemoteSessionFactory sessionFactory;

    @FXML
    TextField userName;
    @FXML
    TextField password;

    @FXML
    Button loginButton;

    @FXML
    RadioButton local;

    @FXML
    TextField ownServer;

    public LoginController() {
    }

    public void hide() {
        ownServer.setVisible(false);
    }

    public void show() {
        ownServer.setVisible(true);
    }

    public void connectToServer(String server) throws IllegalArgumentException, MalformedURLException, NotBoundException, RemoteException, NamingException {
        System.out.println("Connect to: '" + server + "'");



        Properties props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
        props.put(Context.PROVIDER_URL, "http-remoting://localhost:8080"); // todo choose server
        Context ctx = new InitialContext(props);

        //ejb:/[DeployedName]/Implementierungsname![packages + Interface of Bean]
        sessionFactory= (RemoteSessionFactory) ctx.lookup("ejb:/backend-1.0-SNAPSHOT/RemoteSessionFactoryImpl!at.fhv.ae.shared.rmi.RemoteSessionFactory");


       // sessionFactory = (RemoteSessionFactory) Naming.lookup("rmi://" + server + "/music-shop");
    }

    public void login(ActionEvent event) {
        try {
            if (local.isSelected()) {
                connectToServer("localhost");
            } else {
                connectToServer(ownServer.getText());
            }

            RemoteSession session = sessionFactory.logIn(userName.getText(),password.getText());

            final Node source = (Node) event.getSource();
            final Stage currentStage = (Stage) source.getScene().getWindow();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MusicShop.fxml"));
            Parent root = fxmlLoader.load();

            MusicShopController controller = fxmlLoader.getController();
            controller.setSession(session);

            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setScene(scene);

            stage.setTitle("SoundKraut");
            stage.show();

            currentStage.close();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.getDialogPane().setPrefWidth(200);
            alert.setTitle("Could not log in");
            alert.setContentText(alert.getTitle());
            alert.show();
            e.printStackTrace();
        }
    }
}
