package com.ajibuster.app;

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

  public String openMusic () {
    display("Open a music file...");
    return this.file.getAbsolutePath().replaceAll(Pattern.quote("\s"), "%20");
  }
}
