package edu.sjsu.cs.mediaplayer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.File;

//test comments
public class MediaPlayerApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {


        FXMLLoader fxmlLoader = new FXMLLoader(MediaPlayerApp.class.getResource("FileSelect.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 800);
        stage.setTitle("Media Player");

        stage.setScene(scene);
        stage.getIcons().add(new Image(new File("src/main/resources/images/mediaPlay.png").toURI().toString()));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
