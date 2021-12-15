module com.example.labyrtinthe_algomvte {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.example.labyrtinthe_algomvte to javafx.fxml;
    exports com.example.labyrtinthe_algomvte;
}