package com.ajibuster.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import com.ajibuster.app.model.MediaItem;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FileWindow {
  private Stage window = new Stage();
  private FileChooser fc = new FileChooser();
  private File file;

  public FileWindow() {
    fc.getExtensionFilters().addAll(
      new FileChooser.ExtensionFilter("mp3 Files", "*.mp3"),
      new FileChooser.ExtensionFilter("m3u Files", "*.m3u"),
      new FileChooser.ExtensionFilter("All Files", "*.*")
    );
  }

  private void display(String title) {
    this.file = fc.showOpenDialog(window);
  }

  public ArrayList<MediaItem> openMedia() {
    ArrayList<MediaItem> itemList = new ArrayList<MediaItem>();
    display("Open a music file...");
    String filePath = this.file.getAbsolutePath().replaceAll(Pattern.quote("\s"), "%20");
    // Need to parse .m3u in here
    if (filePath.endsWith(".m3u")) {
      itemList = parseM3U(this.file);
    } else {
      itemList.add(new MediaItem("file://" + filePath));
    }
    return itemList;
  }

  private ArrayList<MediaItem> parseM3U(File m3u) {
    ArrayList<MediaItem> filePaths = new ArrayList<MediaItem>();
    try {
      BufferedReader reader = new BufferedReader(new FileReader(m3u));

      // Start reading the file
      String line;
      while ((line = reader.readLine()) != null) {
        // line starts with #?
        if (line.startsWith("#") || line.isBlank()) {
          continue;
        }
        String path = "file://" + line.replaceAll(Pattern.quote("\s"), "%20");
        filePaths.add(new MediaItem(path));
      }

      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return filePaths;
  }
}
