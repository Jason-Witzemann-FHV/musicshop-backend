package at.fhv.ae.javafxfrontend;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Objects;

public class MusicShopApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        //Parent root = FXMLLoader.load(getClass().getResource("__dashboard.fxml"));

        URL fxmlUrl = this.getClass().getResource("MusicShop.fxml");
        Objects.requireNonNull(fxmlUrl);
        Parent root = FXMLLoader.load(fxmlUrl);
        Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
        Scene scene = new Scene(root, screenSize.getWidth(), screenSize.getHeight() - 35);
        stage.setTitle("Soundklaut");
        stage.setScene(scene);
        stage.show();
    }


}