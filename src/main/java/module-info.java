module com.poscinema.pos_cinema {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires de.mkammerer.argon2.nolibs;

    opens com.poscinema.pos_cinema to javafx.fxml;
    exports com.poscinema.pos_cinema;
    exports com.poscinema.pos_cinema.controllers;
    opens com.poscinema.pos_cinema.controllers to javafx.fxml;
}