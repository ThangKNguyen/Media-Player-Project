package edu.sjsu.cs.mediaplayer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.media.Media;
import javafx.stage.FileChooser;

import java.io.File;
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
}
