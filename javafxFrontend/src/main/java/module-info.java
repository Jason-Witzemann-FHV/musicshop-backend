module at.fhv.ae.javafxfrontend {
    requires javafx.controls;
    requires javafx.fxml;


    opens at.fhv.ae.javafxfrontend to javafx.fxml;
    exports at.fhv.ae.javafxfrontend;
}