package edu.sjsu.cs.mediaplayer;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    private Label subtitlesLabel;

    @FXML
    private Button yesSubtitlesButton;

    @FXML
    private Button noSubtitlesButton;

    @FXML
    private Label subtitleCheckLabel;

    @FXML
    private Button subtitleFileButton;

    @FXML
    private Label subtitleFileLabel;

    @FXML
    private Button watchVideoButton;

    private String mediaFilePath;
    private String srtFilePath;
    private boolean subtitlesOn = false;
    private Parent root;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mediaFileLabel.setVisible(false);
        subtitlesLabel.setVisible(false);
        yesSubtitlesButton.setVisible(false);
        noSubtitlesButton.setVisible(false);
        subtitleCheckLabel.setVisible(false);
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
            // restrict the file type to .srt
            FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select a file (*.mp4)", "*.mp4");
            fileChooser.getExtensionFilters().add(filter);
            File file = fileChooser.showOpenDialog(null);
            mediaFilePath = file.toURI().toString();
            // show the mp4 file to the user
            if (mediaFilePath != null) {
                mediaFileLabel.setText(mediaFilePath);
                mediaFileLabel.setVisible(true);
                subtitlesLabel.setVisible(true);
                yesSubtitlesButton.setVisible(true);
                noSubtitlesButton.setVisible(true);
                watchVideoButton.setVisible(true);
            }
        });
    }

    private void onYesSubtitles() {
        yesSubtitlesButton.setOnAction(event -> {
            subtitlesOn = true;
            subtitleFileButton.setVisible(true);
            subtitleCheckLabel.setVisible(true);
            subtitleCheckLabel.setText("You chose to have subtitles on");
        });
    }

    private void onNoSubtitles() {
        noSubtitlesButton.setOnAction(event -> {
            subtitlesOn = false;
            subtitleFileButton.setVisible(false);
            subtitleCheckLabel.setVisible(true);
            subtitleCheckLabel.setText("You chose to have no subtitles");
        });
    }

    private void onSubtitleFile() { // set the functionality for the choose subtitle file button
        subtitleFileButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            // restrict the file type to .srt
            FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select a file (*.srt)", "*.srt");
            fileChooser.getExtensionFilters().add(filter);
            File file = fileChooser.showOpenDialog(null);
            srtFilePath = file.toURI().toString();
            // show the srt file to the user
            if (srtFilePath != null) {
                subtitleFileLabel.setText(srtFilePath);
                subtitleFileLabel.setVisible(true);
            }
        });
    }


    private void onWatchVideo() { // what happens when the user clicks the watch video button
        watchVideoButton.setOnAction(event -> {
            // load the FXML file of the Media Player
            FXMLLoader fxmlLoader = new FXMLLoader(MediaPlayerApp.class.getResource("MediaPlayer.fxml"));
            try {
                root = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // create instance of MediaPlayerController so you can access the methods of the class
            MediaPlayerController mediaPlayerController = fxmlLoader.getController();

            if (subtitlesOn && srtFilePath == null) { // user chooses subtiles on, but provides no srt file
                subtitleFileLabel.setText("Select a .srt file");
                subtitleFileLabel.setVisible(true);
            }
            else if (!subtitlesOn && mediaFilePath!= null) {
                mediaPlayerController.setupMedia(mediaFilePath);
                // get the parent stage of the current scene
                Stage stage = (Stage) watchVideoButton.getScene().getWindow();
                // set the scene of the parent stage to the media player
                stage.setScene(new Scene(root, 600, 600));
            }
            else if (subtitlesOn && mediaFilePath!= null && srtFilePath != null) {
                mediaPlayerController.setupMediaAndSubtitles(mediaFilePath, srtFilePath);
                // get the parent stage of the current scene
                Stage stage = (Stage) watchVideoButton.getScene().getWindow();
                // set the scene of the parent stage to the media player
                stage.setScene(new Scene(root, 600, 600));
            }
        });
    }
}
