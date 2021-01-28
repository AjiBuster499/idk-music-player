package com.ajibuster.app;

import com.ajibuster.app.model.*;

import java.io.File;
import java.util.regex.Pattern;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FileWindow {
  private Stage window = new Stage();
  private FileChooser fc = new FileChooser();
  private File file;

  public FileWindow() {
    fc.getExtensionFilters().addAll(
      new FileChooser.ExtensionFilter("mp3 Files", "*.mp3"),
      new FileChooser.ExtensionFilter("All Files", "*.*")
    );
  }

  private void display (String title) {
    this.file = fc.showOpenDialog(window);
  }

  public MediaHandler openMusic () {
    display("Open a music file...");
    String filePath = this.file.getAbsolutePath().replaceAll(Pattern.quote("\s"), "%20");
    // TODO: Fix This
    // Pass around a MediaHandler?
    MediaHandler mh = new MediaHandler();
    mh = MediaHandler.changeSong("file://" + filePath); // TODO: Improve this
    return mh;
  }
}
