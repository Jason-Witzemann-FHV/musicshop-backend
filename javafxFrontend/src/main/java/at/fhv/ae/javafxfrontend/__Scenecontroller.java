package at.fhv.ae.javafxfrontend;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class __Scenecontroller {
    private Stage stage;
    private Scene scene;
    private Parent root;
    Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();

    @FXML
    private Text employeeName;

    @FXML
    private void initialize() {
        employeeName.setText("I'm a Label.");
    }

    public void switchToDashboard(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("__dashboard.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, screenSize.getWidth(), screenSize.getHeight() - 35);

        stage.setScene(scene);
        stage.show();
    }

    public void switchToMusicSearch(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("__music_search.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, screenSize.getWidth(), screenSize.getHeight() - 35);
        stage.setScene(scene);
        stage.show();
    }
}