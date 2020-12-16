package com.ajibuster.app;

import java.io.File;
import java.util.regex.Pattern;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FileWindow {
  private static Stage window = new Stage();
  private static FileChooser fc = new FileChooser();
  private static  MediaHandler mh;

  public FileWindow(MediaHandler mh) {
    FileWindow.mh = mh;
    fc.getExtensionFilters().addAll(
      new FileChooser.ExtensionFilter("mp3 Files", "*.mp3"),
      new FileChooser.ExtensionFilter("All Files", "*.*")
      // new FileChooser.ExtensionFilter("Video Files", "*.mp4"); Future Update for Video?
    );
  }

  public MediaHandler display (String title) {
    File file = fc.showOpenDialog(window);
    if (file != null) {
      mh = openMusic(file);
      return mh;
    }
    return null;
  }

  private static MediaHandler openMusic (File file) {
    // TODO: Allow Spaces in File
    String filePath = file.getAbsolutePath().replaceAll(Pattern.quote("\s"), "%20");
    if (FileWindow.mh.getPlayer() != null) {
      FileWindow.mh.getPlayer().dispose();
    }
    mh = MediaHandler.changeSong("file://" + filePath);
    return mh;
  }
}
