module edu.sjsu.cs.mediaplayer.cs151mediaplayer {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    //requires revai.java.sdk;


    opens edu.sjsu.cs.mediaplayer to javafx.fxml;
    exports edu.sjsu.cs.mediaplayer;
}
