module com.heshanthenura.textureandlight {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.heshanthenura.textureandlight to javafx.fxml;
    exports com.heshanthenura.textureandlight;
}