package com.ajibuster.app;

import java.io.File;
import java.util.regex.Pattern;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FileWindow {
  private Stage window = new Stage();
  private FileChooser fc = new FileChooser();
  private  MediaHandler mh;

  public FileWindow(MediaHandler mh) {
    this.mh = mh;
    fc.getExtensionFilters().addAll(
      new FileChooser.ExtensionFilter("mp3 Files", "*.mp3"),
      new FileChooser.ExtensionFilter("All Files", "*.*")
      // new FileChooser.ExtensionFilter("Video Files", "*.mp4"); Future Update for Video?
    );
  }

  // TODO: Rework Display and openMusic they're ugly
  public MediaHandler display (String title) {
    File file = fc.showOpenDialog(window);
    if (file != null) {
      mh = openMusic(file);
      return mh;
    }
    return null;
  }

  private MediaHandler openMusic (File file) {
    String filePath = file.getAbsolutePath().replaceAll(Pattern.quote("\s"), "%20");
    if (mh != null) {
      if (mh.getPlayer() != null) {
        mh.getPlayer().dispose();
      }
    }
    mh = MediaHandler.changeSong("file://" + filePath);
    return mh;
  }
}
