package com.ajibuster.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.ajibuster.app.model.media.MediaItem;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

// TODO: Probably rename to OpenFileWindow
public class FileWindow {
  private Stage window = new Stage();
  private FileChooser fc = new FileChooser();
  private List<File> fileList;
  private ArrayList<MediaItem> itemList;

  public FileWindow(String title) {
    // TODO: Make different ExtensionFilters for each type of window
    this.fileList = new ArrayList<File>();
    window.setTitle(title);
    fc.getExtensionFilters().addAll(
      new FileChooser.ExtensionFilter("mp3 Files", "*.mp3"),
      new FileChooser.ExtensionFilter("m3u Files", "*.m3u"),
      new FileChooser.ExtensionFilter("All Files", "*.*")
    );
  }

  private void display() {
    this.fileList.clear();
    List<File> list = fc.showOpenMultipleDialog(window);
    if (list == null) {
      // do literally nothing
      return;
    } else {
      this.fileList.addAll(list);
    }
  }

  public ArrayList<MediaItem> openMedia() {
    this.itemList = new ArrayList<MediaItem>();
    display();
    if (this.fileList.get(0).getAbsolutePath().endsWith(".m3u")) {
      // Parse .m3u
      itemList = parseM3U(this.fileList.get(0));
    } else {
      for (File file : fileList) {
        itemList.add(new MediaItem("file://" + file.getAbsolutePath().replaceAll(Pattern.quote("\s"), "%20")));
      }
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
        // line starts with # or is blank
        if (line.startsWith("#") || line.isBlank()) {
          // Don't want it
          // Need to add these lines in and parse them into the right data.
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
