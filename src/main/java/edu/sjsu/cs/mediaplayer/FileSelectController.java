package edu.sjsu.cs.mediaplayer;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.media.Media;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FileSelectController implements Initializable {
    @FXML
    private Button mediaFileButton;

    @FXML
    private Label mediaFileLabel;

    @FXML
    private MenuButton subtitleButton;

    @FXML
    private MenuItem yesSubtitles;

    @FXML
    private MenuItem noSubtitles;

    @FXML
    private Button subtitleFileButton;

    @FXML
    private Label subtitleFileLabel;

    @FXML
    private Button watchVideoButton;

    private String filePath;
    private Media media;
    private String srtFile;
    private Scene mediaPlayerScene;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mediaFileLabel.setVisible(false);
        subtitleButton.setVisible(false);
        subtitleFileButton.setVisible(false);
        subtitleFileLabel.setVisible(false);
        watchVideoButton.setVisible(false);
        onSelectMediaFile();
        onYesSubtitles();
        onNoSubtitles();
        onSubtitleFile();
        onWatchVideo();
    }

    private void onSelectMediaFile() {
        mediaFileButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select a file (*.mp4)", "*.mp4");
            fileChooser.getExtensionFilters().add(filter);
            File file = fileChooser.showOpenDialog(null);
            filePath = file.toURI().toString();
            if (filePath != null) {
                mediaFileLabel.setText(filePath);
                mediaFileLabel.setVisible(true);
                subtitleButton.setVisible(true);
                media = new Media(filePath);
                watchVideoButton.setVisible(true);
            }
        });
    }

    private void onYesSubtitles() {
        yesSubtitles.setOnAction(event -> {
            subtitleFileButton.setVisible(true);
        });
    }

    private void onNoSubtitles() {
        noSubtitles.setOnAction(event -> {
            subtitleFileButton.setVisible(false);
            subtitleFileLabel.setVisible(false);
        });
    }

    private void onSubtitleFile() {
        subtitleFileButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select a file (*.srt)", "*.srt");
            fileChooser.getExtensionFilters().add(filter);
            File file = fileChooser.showOpenDialog(null);
            srtFile = file.toURI().toString();
        });
    }

    private void onWatchVideo() { // what happens when the user clicks the watch video button
        watchVideoButton.setOnAction(event -> {
            // load the FXML file of the Media Player
            FXMLLoader fxmlLoader = new FXMLLoader(MediaPlayerApp.class.getResource("MediaPlayer.fxml"));
            // get the parent stage of the current scene
            Stage stage = (Stage) watchVideoButton.getScene().getWindow();
            try {
                // set the scene of the parent stage to the media player
                stage.setScene(new Scene(fxmlLoader.load(), 600, 600));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
