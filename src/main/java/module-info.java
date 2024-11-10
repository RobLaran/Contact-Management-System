module org.cms.contactmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;
    requires mysql.connector.j;

    opens org.cms.contactmanagementsystem to javafx.fxml;
    exports org.cms.contactmanagementsystem;
}