package at.fhv.ae.javafxfrontend;

import at.fhv.ae.shared.services.BeanSession;
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
import java.util.Properties;

public class LoginController {

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

    public void login(ActionEvent event) {
        try {

            String ip = local.isSelected() ? "localhost" : ownServer.getText();

            Properties props = new Properties();
            props.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
            props.put(Context.PROVIDER_URL, "http-remoting://" + ip + ":8080");
            Context ctx = new InitialContext(props);
            BeanSession session = (BeanSession) ctx.lookup("ejb:/backend-1.0-SNAPSHOT/BeanSessionImpl!at.fhv.ae.shared.services.BeanSession?stateful");
            session.authenticate(userName.getText(), password.getText());


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
