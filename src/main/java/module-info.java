module com.brightlightshop.client4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires okhttp3;
    requires org.json;
    requires org.joda.time;
    requires org.apache.commons.lang3;

    opens com.brightlightshop.client4 to javafx.fxml;
    exports com.brightlightshop.client4;
    opens com.brightlightshop.client4.controllers.pages to javafx.fxml;
    exports com.brightlightshop.client4.controllers.pages;
    opens com.brightlightshop.client4.controllers.components to javafx.fxml;
    exports com.brightlightshop.client4.controllers.components;
    opens com.brightlightshop.client4.models to javafx.fxml;
    exports com.brightlightshop.client4.models;

    exports com.brightlightshop.client4.types;
}