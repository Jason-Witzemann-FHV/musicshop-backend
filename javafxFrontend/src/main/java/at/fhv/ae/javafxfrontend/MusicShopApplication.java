package at.fhv.ae.javafxfrontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Objects;

public class MusicShopApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        URL fxmlUrl = this.getClass().getResource("MusicShop.fxml");
        Objects.requireNonNull(fxmlUrl);
        Locale.setDefault(new Locale("en"));
        Parent root = FXMLLoader.load(fxmlUrl);
        Scene scene = new Scene(root);
        stage.setTitle("SoundKraut");
        stage.setScene(scene);
        stage.show();
    }
}