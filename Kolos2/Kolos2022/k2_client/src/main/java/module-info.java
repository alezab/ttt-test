/**
 * This module-info.java file is part of the org.example.k2_client module.
 * It specifies the dependencies and the packages that are opened and exported by this module.
 */
module org.example.k2_client {
    // This module requires javafx.controls module for JavaFX controls like buttons, text fields, etc.
    requires javafx.controls;
    // This module requires javafx.fxml module for working with FXML to create user interfaces.
    requires javafx.fxml;

    // This module requires org.controlsfx.controls module for additional JavaFX controls.
    requires org.controlsfx.controls;

    // This module opens org.example.k2_client package to javafx.fxml.
    // This means that the classes in this package can be accessed by javafx.fxml.
    opens org.example.k2_client to javafx.fxml;
    // This module exports org.example.k2_client package.
    // This means that the public classes in this package can be accessed by other modules.
    exports org.example.k2_client;
}