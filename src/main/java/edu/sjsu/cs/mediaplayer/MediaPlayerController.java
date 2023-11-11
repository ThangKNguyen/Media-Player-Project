package edu.sjsu.cs.mediaplayer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MediaPlayerController implements Initializable {
    @FXML
    private VBox parentVBox;

    @FXML
    private MediaView mediaView;

    @FXML
    private Slider timeSlider;

    @FXML
    private HBox controlsHBox;

    @FXML
    private Button playPauseReplayButton;

    @FXML
    private Button skipForwardButton;

    @FXML
    private Button skipBackwardsButton;

    @FXML
    private HBox volumeHBox;

    @FXML
    private Label volumeLabel;

    @FXML
    private Slider volumeSlider;

    @FXML
    private Label currentTimeLabel;

    @FXML
    private Label totalLengthLabel;

    @FXML
    private Button subtitleButton;

    @FXML
    private MenuButton playbackSpeedMenuButton;

    @FXML
    private MenuItem oneFourthSpeed;

    @FXML
    private MenuItem halfSpeed;

    @FXML
    private MenuItem threeFourthSpeed;

    @FXML
    private MenuItem oneSpeed;

    @FXML
    private MenuItem one25Speed;

    @FXML
    private MenuItem one50Speed;

    @FXML
    private MenuItem one75Speed;

    @FXML
    private MenuItem doubleSpeed;

    @FXML
    private Button fullscreenButton;

    @FXML
    private Button exitButton;

    private Scene fileSelectScene;
    private MediaPlayer mediaPlayer;
    private Media media;
    private String mediaPath;
    private boolean endOfVideo;
    private boolean isPlaying;
    private boolean isMuted;

    private ImageView play;
    private ImageView pause;
    private ImageView replay;
    private ImageView skipForward;
    private ImageView skipBackward;
    private ImageView volume;
    private ImageView muted;
    private ImageView subtitles;
    private ImageView playbackSpeed;
    private ImageView fullscreen;
    private ImageView exit;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setImages();
        playPauseReplayButton.setGraphic(pause);
        skipForwardButton.setGraphic(skipForward);
        skipBackwardsButton.setGraphic(skipBackward);
        volumeLabel.setGraphic(volume);
        playbackSpeedMenuButton.setGraphic(playbackSpeed);
        fullscreenButton.setGraphic(fullscreen);
        subtitleButton.setGraphic(subtitles);
        exitButton.setGraphic(exit);
    }

    private void setImages() {
        final int IMAGE_SIZE = 25;

        Image playImage = new Image(new File("src/main/resources/images/play.png").toURI().toString());
        play = new ImageView(playImage);
        play.setFitHeight(IMAGE_SIZE);
        play.setFitWidth(IMAGE_SIZE);

        Image pauseImage = new Image(new File("src/main/resources/images/pause.png").toURI().toString());
        pause = new ImageView(pauseImage);
        pause.setFitHeight(IMAGE_SIZE);
        pause.setFitWidth(IMAGE_SIZE);

        Image replayImage = new Image(new File("src/main/resources/images/replay.png").toURI().toString());
        replay = new ImageView(replayImage);
        replay.setFitHeight(IMAGE_SIZE);
        replay.setFitWidth(IMAGE_SIZE);

        Image forwardImage = new Image(new File("src/main/resources/images/skipForward.png").toURI().toString());
        skipForward = new ImageView(forwardImage);
        skipForward.setFitHeight(IMAGE_SIZE);
        skipForward.setFitWidth(IMAGE_SIZE);

        Image backwardImage = new Image(new File("src/main/resources/images/skipBackward.png").toURI().toString());
        skipBackward = new ImageView(backwardImage);
        skipBackward.setFitHeight(IMAGE_SIZE);
        skipBackward.setFitWidth(IMAGE_SIZE);

        Image volumeImage = new Image(new File("src/main/resources/images/volume.png").toURI().toString());
        volume = new ImageView(volumeImage);
        volume.setFitHeight(IMAGE_SIZE);
        volume.setFitWidth(IMAGE_SIZE);

        Image mutedImage = new Image(new File("src/main/resources/images/muted.png").toURI().toString());
        muted = new ImageView(mutedImage);
        muted.setFitHeight(IMAGE_SIZE);
        muted.setFitWidth(IMAGE_SIZE);

        Image subtitleImage = new Image(new File("src/main/resources/images/subtitles.png").toURI().toString());
        subtitles = new ImageView(subtitleImage);
        subtitles.setFitHeight(IMAGE_SIZE);
        subtitles.setFitWidth(IMAGE_SIZE);

        Image playbackImage = new Image(new File("src/main/resources/images/playbackSpeed.png").toURI().toString());
        playbackSpeed = new ImageView(playbackImage);
        playbackSpeed.setFitHeight(IMAGE_SIZE);
        playbackSpeed.setFitWidth(IMAGE_SIZE);

        Image fullscreenImage = new Image(new File("src/main/resources/images/fullscreen.png").toURI().toString());
        fullscreen = new ImageView(fullscreenImage);
        fullscreen.setFitHeight(IMAGE_SIZE);
        fullscreen.setFitWidth(IMAGE_SIZE);

        Image exitImage = new Image(new File("src/main/resources/images/exit.png").toURI().toString());
        exit = new ImageView(exitImage);
        exit.setFitHeight(IMAGE_SIZE);
        exit.setFitWidth(IMAGE_SIZE);
    }
}
