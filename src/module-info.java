module ContactsProjectJavaFX {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires java.sql;
    opens sample;
    opens sample.DataModel;
}