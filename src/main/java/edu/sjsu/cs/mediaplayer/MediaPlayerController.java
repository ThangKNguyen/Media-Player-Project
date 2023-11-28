package edu.sjsu.cs.mediaplayer;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import java.util.List;


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
    private Label volumeLabel;

    @FXML
    private Slider volumeSlider;

    @FXML
    private HBox volumeHBox;

    @FXML
    private Label currentTimeLabel;

    @FXML
    private Label totalLengthLabel;

    @FXML
    private Button subtitleButton;
    private List<SRTParser.Subtitle> subtitlesText;
    @FXML
    private Label subtitleLabel;

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

    private MediaPlayer mediaPlayer;
    private String srtFilePath;
    private boolean endOfVideo;
    private boolean isPlaying;
    private double lastVolumeLevel;

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
        setupMediaAndSubtitles(FileSelectController.mediaFilePath, FileSelectController.srtFilePath);

        // put the ImageViews to the respective buttons
        setImages();
        playPauseReplayButton.setGraphic(pause);
        skipForwardButton.setGraphic(skipForward);
        skipBackwardsButton.setGraphic(skipBackward);
        volumeLabel.setGraphic(volume);
        playbackSpeedMenuButton.setGraphic(playbackSpeed);
        fullscreenButton.setGraphic(fullscreen);
        subtitleButton.setGraphic(subtitles);
        exitButton.setGraphic(exit);

        onFullscreen();
        setPlaybackSpeeds();
        onVolumeLabel();
        playPauseReplayButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Button buttonPlay = (Button) actionEvent.getSource();
                if (endOfVideo) {
                    timeSlider.setValue(0);
                    endOfVideo = false;
                    isPlaying = false;
                }
                if (isPlaying) {
                    buttonPlay.setGraphic(play);
                    mediaPlayer.pause();
                    isPlaying = false;
                } else {
                    buttonPlay.setGraphic(pause);
                    mediaPlayer.play();
                    isPlaying = true;
                }
            }
        });

        //skips forward 10 secs when pressed

        skipForwardButton.setOnAction(event -> {
            Duration currentTime = mediaPlayer.getCurrentTime();
            mediaPlayer.seek(currentTime.add(Duration.seconds(10)));
        });

        //skips backward 10 secs when pressed
        skipBackwardsButton.setOnAction(event -> {
            Duration currentTime = mediaPlayer.getCurrentTime();
            mediaPlayer.seek(currentTime.subtract(Duration.seconds(10)));
        });

        //sets the default volume to 100
        volumeSlider.setValue(mediaPlayer.getVolume()*100);

        //allows users to adjust volume with volume slider
        //notes: should change icon to a muted volume image when volume == 0
        volumeSlider.valueProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable observable) {
                mediaPlayer.setVolume(volumeSlider.getValue()/100);

                if(mediaPlayer.getVolume() !=0.0){
                    volumeLabel.setGraphic(volume);
                    mediaPlayer.setMute(false);
                } else{
                    volumeLabel.setGraphic(muted);
                    mediaPlayer.setMute(true);
                }

            }
        });

        //Added Slider functionality
        timeSlider.valueChangingProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean wasChanging, Boolean isChanging) {
                if (!isChanging) {
                    mediaPlayer.seek(Duration.seconds(timeSlider.getValue()));
                }
            }
        });
        timeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number prevValue, Number newValue) {
                double currentTime = mediaPlayer.getCurrentTime().toSeconds();
                if (Math.abs(currentTime - newValue.doubleValue()) > 0.5) {
                    mediaPlayer.seek(Duration.seconds(newValue.doubleValue()));
                }
                // check if at the end of video
                matchEndOfVideo(currentTimeLabel.getText(), totalLengthLabel.getText());
            }
        });


        // bind the height of media view to height of the scene
        parentVBox.sceneProperty().addListener(new ChangeListener<Scene>() {
            @Override
            public void changed(ObservableValue<? extends Scene> observableValue, Scene oldScene, Scene newScene) {
                if (oldScene == null && newScene != null)
                    // the height of the media view needs to be the height of the scene minus the height of the control hbox
                    mediaView.fitHeightProperty().bind(newScene.heightProperty().subtract(controlsHBox.heightProperty().add(50)));
            }
        });

        // when the mouse enters the volume label's space, show the volume slider
        volumeLabel.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (volumeHBox.lookup("#volumeSlider") == null) {
                    volumeHBox.getChildren().add(volumeSlider);
                }
            }
        });

        // when the mouse leaves the volume HBox space, hide the volume slider
        volumeHBox.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                volumeHBox.getChildren().remove(volumeSlider);
            }
        });

        mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observableValue, Duration oldTime, Duration newTime) {
                // check if the slider is not changing
                if (!timeSlider.isValueChanging())
                    timeSlider.setValue(newTime.toSeconds());
                matchEndOfVideo(currentTimeLabel.getText(), totalLengthLabel.getText());
            }
        });

        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                // at the end of the video, so need to show the replay button
                playPauseReplayButton.setGraphic(replay);
                endOfVideo = true;
            }
        });

        subtitleButton.setOnAction(event -> {
            if (subtitleLabel.isVisible())
                subtitleLabel.setVisible(false);
            else subtitleLabel.setVisible(true);
            mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                @Override
                public void changed(ObservableValue<? extends Duration> observableValue, Duration oldTime, Duration newTime) {
                    updateSubtitleText(newTime.toMillis());
                }
            });
        });

        mediaView.setOnMouseClicked(event -> {
            if (endOfVideo) {
                timeSlider.setValue(0);
                endOfVideo = false;
                isPlaying = false;
            }
            if (isPlaying) {
                playPauseReplayButton.setGraphic(play);
                mediaPlayer.pause();
                isPlaying = false;
            } else {
                playPauseReplayButton.setGraphic(pause);
                mediaPlayer.play();
                isPlaying = true;
            }
        });

        onExit();
    }

    private void updateSubtitleText(double currentTime) {
        for (SRTParser.Subtitle subtitle : subtitlesText) {
            Duration start = parseTime(subtitle.startTime);
            Duration end = parseTime(subtitle.endTime);
            if (!start.isUnknown() && !end.isUnknown() && currentTime >= start.toMillis() && currentTime < end.toMillis()) {
                String text = String.join("\n", subtitle.subtitleLines);
                subtitleLabel.setText(text);
                break;
            } else if (currentTime >= end.toMillis()) {
                subtitleLabel.setText("");
            }
        }
    }

    private Duration parseTime(String timeString) {
        try {
            String[] hms = timeString.split(":");
            String[] secMs = hms[2].split(",");
            int hours = Integer.parseInt(hms[0]);
            int minutes = Integer.parseInt(hms[1]);
            int seconds = Integer.parseInt(secMs[0]);
            int millis = Integer.parseInt(secMs[1]);
            return Duration.hours(hours).add(Duration.minutes(minutes)).add(Duration.seconds(seconds)).add(Duration.millis(millis));
        } catch (Exception e) {
            e.printStackTrace();
            // Handle parsing error
            return Duration.UNKNOWN;
        }
    }

    private void setImages() { // use the images and put them on to their respective ImageView
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

    private void onExit() { // what happens when the user clicks the exit button
        exitButton.setOnAction(event -> {
            mediaPlayer.stop();
            // load the FXML file of the select file page
            FXMLLoader fxmlLoader = new FXMLLoader(MediaPlayerApp.class.getResource("FileSelect.fxml"));
            // get the parent stage of the current scene
            Stage stage = (Stage) exit.getScene().getWindow();
            try {
                // set the scene of the parent stage to the select file page
                stage.setScene(new Scene(fxmlLoader.load(), 800, 800));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void setupMedia(String mediaFilePath) { // use the video's file path and display it
        Media media = new Media(mediaFilePath);
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.setAutoPlay(true);
        subtitleLabel.setVisible(false);
        isPlaying = true;
        bindTimeSliderAndCurrentTimeLabel();
        mediaPlayer.totalDurationProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observableValue, Duration oldDuration, Duration newDuration) {
                timeSlider.setMax(newDuration.toSeconds());
                totalLengthLabel.setText(formatTime(newDuration));
            }
        });

        mediaPlayer.currentTimeProperty().addListener(((observableValue, oldValue, newValue) -> {
            timeSlider.setValue(newValue.toSeconds());
        }));
    }

    public void setupMediaAndSubtitles(String mediaFilePath, String srtFilePath) {
        setupMedia(mediaFilePath);
        if (srtFilePath == null) {
            subtitleButton.setVisible(false);
        }
        // will setup subtitles later
        else {
            subtitleButton.setVisible(true);
            SRTParser parser = new SRTParser();
            try {
                this.subtitlesText = parser.parseSRT(srtFilePath);
            } catch (IOException e) {
                e.printStackTrace();
                // Handle error (e.g., show an alert to the user)
            }


        }
    }

    private void onFullscreen() {
        fullscreenButton.setOnAction(event -> {
            // get the parent stage
            Stage parentStage = (Stage) fullscreenButton.getScene().getWindow();
            // if the stage is already fullscreen, exit fullscreen
            if (parentStage.isFullScreen()) {
                parentStage.setFullScreen(false);
                mediaView.setPreserveRatio(true);
            }
            // otherwise enter fullscreen
            else {
                parentStage.setFullScreen(true);
                mediaView.setPreserveRatio(false);
            }
        });
    }

    private void setPlaybackSpeeds() {
        onOneFourthSpeed();
        onHalfSpeed();
        onThreeFourthSpeed();
        onOneSpeed();
        onOne25Speed();
        onOne50Speed();
        onOne75Speed();
        onDoubleSpeed();
    }

    private void onOneFourthSpeed() {
        oneFourthSpeed.setOnAction(event -> {
            setMediaPlayerRate(0.25);
        });
    }
    private void onHalfSpeed() {
        halfSpeed.setOnAction(event -> {
            setMediaPlayerRate(0.50);
        });
    }
    private void onThreeFourthSpeed() {
        threeFourthSpeed.setOnAction(event -> {
            setMediaPlayerRate(0.75);
        });
    }
    private void onOneSpeed() {
        oneSpeed.setOnAction(event -> {
            setMediaPlayerRate(1);
        });
    }
    private void onOne25Speed() {
        one25Speed.setOnAction(event -> {
            setMediaPlayerRate(1.25);
        });
    }
    private void onOne50Speed() {
        one50Speed.setOnAction(event -> {
            setMediaPlayerRate(1.50);
        });
    }
    private void onOne75Speed() {
        one75Speed.setOnAction(event -> {
            setMediaPlayerRate(1.75);
        });
    }
    private void onDoubleSpeed() {
        doubleSpeed.setOnAction(event -> {
            setMediaPlayerRate(2);
        });
    }

    private void setMediaPlayerRate(double rate) {
        mediaPlayer.setRate(rate);
        playbackSpeedMenuButton.setText(rate + "x");
    }

    private void onVolumeLabel() {
        volumeLabel.setOnMouseClicked(event -> {
            if (mediaPlayer.isMute()) {
                mediaPlayer.setMute(false);
                volumeLabel.setGraphic(volume);
                volumeSlider.setValue(lastVolumeLevel);
            }
            else {
                mediaPlayer.setMute(true);
                volumeLabel.setGraphic(muted);
                lastVolumeLevel = volumeSlider.getValue();
                volumeSlider.setValue(0);
            }
        });
    }

    private void bindTimeSliderAndCurrentTimeLabel() {
        currentTimeLabel.textProperty().bind(Bindings.createStringBinding(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return formatTime(mediaPlayer.getCurrentTime()) + " / ";
            }
        }, mediaPlayer.currentTimeProperty()));
    }

    private String formatTime(Duration time) {
        int hrs = (int) time.toHours();
        int mins = (int) time.toMinutes();
        int secs = (int) time.toSeconds();

        // show only values less than 60 for seconds
        if (secs > 59) {
            secs %=  60;
        }
        if (mins > 59) {
            mins %= 60;
        }
        if (hrs > 59) {
            hrs %= 60;
        }

        // hours:mins:secs
        if (hrs > 0)
            return String.format("%d:%02d:%02d", hrs, mins, secs);
        // mins:secs
        else if (mins >= 10)
            return String.format("%02d:%02d", mins, secs);
        else
            return String.format("%2d:%02d", mins, secs);
    }

    // method to check if at the end of video
    // at end of video when each character of the current time label is the same as the total length label
    public void matchEndOfVideo(String currTime, String totalTime) {
        for (int i = 0; i < totalTime.length(); i++) {
            // if the characters do not match, we are not at the end of the video
            // so break out of the loop
            if (currTime.charAt(i) != totalTime.charAt(i)) {
                endOfVideo = false;
                if (isPlaying)
                    playPauseReplayButton.setGraphic(pause);
                else
                    playPauseReplayButton.setGraphic(play);
                break;
            }
            else {
                endOfVideo = true;
                playPauseReplayButton.setGraphic(replay);
            }
        }
    }
}
