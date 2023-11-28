package edu.sjsu.cs.mediaplayer;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
    private Button subtitleFileButton;

    @FXML
    private Label subtitleFileLabel;

    @FXML
    private Button watchVideoButton;

    //Changed from private to protected static, for mediaPlayerController to access
    protected static String mediaFilePath;
    protected static String srtFilePath;
    private Parent root;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mediaFileLabel.setVisible(false);
        subtitleFileButton.setVisible(false);
        subtitleFileLabel.setVisible(false);
        watchVideoButton.setVisible(false);
        onSelectMediaFile();
        onSubtitleFile();
        onWatchVideo();
    }

    private void onSelectMediaFile() {
        mediaFileButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            // restrict the file type to .mp4
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Select a file (*.mp4)", "*.mp4"));
            File selectedFile = fileChooser.showOpenDialog(((Node) event.getTarget()).getScene().getWindow());
            // show the mp4 file to the user
            if (selectedFile != null) {
                mediaFilePath = selectedFile.toURI().toString();
                mediaFileLabel.setText(selectedFile.getName() + " has been selected.");
                mediaFileLabel.setVisible(true);
                subtitleFileButton.setVisible(true);
                watchVideoButton.setVisible(true);
            }
            else {
                mediaFileLabel.setText("Please select a file");
                mediaFileLabel.setVisible(true);
            }
        });
    }

    private void onSubtitleFile() { // set the functionality for the choose subtitle file button
        subtitleFileButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            // restrict the file type to .srt
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Select a file (*.srt)", "*.srt"));
            File selectedFile = fileChooser.showOpenDialog(((Node) event.getTarget()).getScene().getWindow());
            // show the srt file to the user
            if (selectedFile != null) {
                srtFilePath = selectedFile.toString();
                subtitleFileLabel.setText(selectedFile.getName() + " has been selected.");
                subtitleFileLabel.setVisible(true);
            }
            else {
                subtitleFileLabel.setText("No *srt file selected.");
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
            //MediaPlayerController mediaPlayerController = fxmlLoader.getController();

            if (mediaFilePath != null && srtFilePath != null) {
                //mediaPlayerController.setupMediaAndSubtitles(mediaFilePath, srtFilePath);
                // get the parent stage of the current scene
                Stage stage = (Stage) watchVideoButton.getScene().getWindow();
                // set the scene of the parent stage to the media player
                stage.setScene(new Scene(root, 800, 800));
            }
            else if (mediaFilePath != null && srtFilePath == null) {
                //mediaPlayerController.setupMediaAndSubtitles(mediaFilePath, null);
                // get the parent stage of the current scene
                Stage stage = (Stage) watchVideoButton.getScene().getWindow();
                // set the scene of the parent stage to the media player
                stage.setScene(new Scene(root, 800, 800));
            }
            else {
                mediaFileLabel.setText("Select a media file");
            }
        });
    }
}
