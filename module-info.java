module typetrainer {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.media;

    opens typetrainer to javafx.fxml;
    exports typetrainer;
}
