module com.poscinema.pos_cinema {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.poscinema.pos_cinema to javafx.fxml;
    exports com.poscinema.pos_cinema;
}