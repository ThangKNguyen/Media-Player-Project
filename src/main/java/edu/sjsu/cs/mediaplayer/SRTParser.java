package edu.sjsu.cs.mediaplayer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SRTParser {

    public static class Subtitle {
        int number;
        String startTime;
        String endTime;
        List<String> subtitleLines;

        public Subtitle() {
            subtitleLines = new ArrayList<>();
        }
    }

    public List<Subtitle> parseSRT(String filePath) throws IOException {
        List<Subtitle> subtitles = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            Subtitle subtitle = null;
            while ((line = br.readLine()) != null) {
                if (subtitle == null) {
                    subtitle = new Subtitle();
                    subtitle.number = Integer.parseInt(line);
                } else if (subtitle.startTime == null) {
                    String[] times = line.split(" --> ");
                    subtitle.startTime = times[0];
                    subtitle.endTime = times[1];
                } else if (!line.isEmpty()) {
                    subtitle.subtitleLines.add(line);
                } else {
                    subtitles.add(subtitle);
                    subtitle = null;
                }
            }
            // Add the last subtitle if the file doesn't end with a new line
            if (subtitle != null && !subtitle.subtitleLines.isEmpty()) {
                subtitles.add(subtitle);
            }
        }
        return subtitles;
    }
}
