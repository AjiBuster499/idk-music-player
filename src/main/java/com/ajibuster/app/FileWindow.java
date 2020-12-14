package com.ajibuster.app;

import java.io.File;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FileWindow {
  private static Stage window = new Stage();
  private static FileChooser fc = new FileChooser();
  public static  MediaHandler mh;

  public FileWindow(MediaHandler mh) {
    FileWindow.mh = mh;
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
    mh.pauseMusic();
    mh = MediaHandler.changeSong("file://" + file.getAbsolutePath().toString());
    mh.playMusic();

    return mh;
  }
}
