package com.ajibuster.app.view.files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ajibuster.app.model.media.MediaItem;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FileWindow {
  private Stage window = new Stage();
  private FileChooser fc = new FileChooser();

  private List<File> fileList;
  private ArrayList<MediaItem> itemList;

  private String extM3UTemplate = "#EXTM3U";
  private String extInfTemplate = "#EXTINF%d, %s - %s";
  private String osName = System.getProperty("os.name");
  private String uriAddOn = osName.contains("Windows") ? "file:///" : "file://";

  public FileWindow(String title, String id) {
    // TODO: Make different ExtensionFilters for each type of window
    this.fileList = new ArrayList<File>();
    window.setTitle(title);
    switch (id) {
      case "OpenMultipleMedia":
        // multiple media, same as single
      case "OpenMedia":
        // single Media
        fc.getExtensionFilters().addAll(
          new FileChooser.ExtensionFilter("mp3 Files", "*.mp3"),
          new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        break;
      case "SaveToPlaylist":
        // save playlist, same as open
      case "OpenPlaylist":
        // open Playlist
        fc.getExtensionFilters().addAll(
          new FileChooser.ExtensionFilter("m3u Files", "*.m3u"),
          new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        break;
      default:
        fc.getExtensionFilters().addAll(
          new FileChooser.ExtensionFilter("mp3 Files", "*.mp3"),
          new FileChooser.ExtensionFilter("m3u Files", "*.m3u"),
          new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        break;
    }
    
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
        String filePath = this.uriAddOn + file.getAbsolutePath().replace("\\", "/").replace("\s", "%20");
        itemList.add(new MediaItem(filePath));
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
        String path = this.uriAddOn + line.replace("\\", "/").replace("\s", "%20");
        filePaths.add(new MediaItem(path));
      }
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return filePaths;
  }

  private File generateM3UFile (ArrayList<String> pathsList, File m3uFile) {
    try {
      FileWriter writer = new FileWriter (m3uFile);
      writer.write(extM3UTemplate);
      // Needs to know the paths from Playlist.
      for (String path : pathsList) {
        writer.write("\n");
        // Trim out the file://
        path = path.replace(uriAddOn, "");
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
