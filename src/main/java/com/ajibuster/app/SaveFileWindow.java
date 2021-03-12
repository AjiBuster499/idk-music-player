package com.ajibuster.app;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SaveFileWindow {
  private Stage window = new Stage();
  private FileChooser fc = new FileChooser();
  private String extM3UTemplate = "#EXTM3U";
  private String extInfTemplate = "#EXTINF%d, %s - %s";

  public SaveFileWindow (String title) {
    window.setTitle(title);
  }

  private File generateM3UFile (ArrayList<String> pathsList, File m3uFile) {
    try {
      FileWriter writer = new FileWriter (m3uFile);
      writer.write(extM3UTemplate);
      // Needs to know the paths from Playlist.
      for (String path : pathsList) {
        writer.write("\n");
        // Trim out the file://
        path = path.replace("file://", "");
        // stand-in parts for right now.
        writer.write(String.format(extInfTemplate, 10, "Testing", "Testers"));
        writer.write("\n");
        writer.write(path);
      }
      writer.write("\n");
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return m3uFile;
  }

  public File saveToFile (ArrayList<String> pathsList) {
    return generateM3UFile(pathsList, this.fc.showSaveDialog(window));
  }
  
}
