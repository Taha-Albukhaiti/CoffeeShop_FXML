module com.example.coffeeshop_fxml {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.coffeeshop_fxml to javafx.fxml;
    exports com.example.coffeeshop_fxml;
}