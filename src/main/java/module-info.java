module edu.sjsu.cs.mediaplayer.cs151mediaplayer {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens edu.sjsu.cs.mediaplayer to javafx.fxml;
    exports edu.sjsu.cs.mediaplayer;
}