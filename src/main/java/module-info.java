module mx.unison {
    requires javafx.controls;
    requires javafx.fxml;
    requires ormlite.jdbc;
    requires java.sql;

    // Cambia la línea de slf4j por esta:
    requires org.slf4j;

    opens mx.unison.views to javafx.fxml;
    opens mx.unison.controllers to javafx.fxml;
    opens mx.unison.models to ormlite.jdbc, javafx.base;

    exports mx.unison;
}