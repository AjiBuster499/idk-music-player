package com.ajibuster.app.view.files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ajibuster.app.model.media.MediaItem;
import com.ajibuster.app.model.media.Playlist;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FileWindow {
  private Stage window = new Stage();
  private FileChooser fc = new FileChooser();

  private List<File> fileList = new ArrayList<File>();
  private ArrayList<MediaItem> itemList;

  private String extM3UTemplate = "#EXTM3U";
  private String extInfTemplate = "#EXTINF:%d, %s - %s";
  private String osName = System.getProperty("os.name");
  private String uriAddOn = osName.contains("Windows") ? "file:///" : "file://";

  public FileWindow(String title, String id) {
    window.setTitle(title);
    window.setOnCloseRequest(h -> {
      window.hide();
    });
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
        // all the filters
        fc.getExtensionFilters().addAll(
          new FileChooser.ExtensionFilter("mp3 Files", "*.mp3"),
          new FileChooser.ExtensionFilter("m3u Files", "*.m3u"),
          new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        break;
    }
    
  }

  public void display() {
    this.fileList.addAll(this.fc.showOpenMultipleDialog(window));
    if (this.fileList == null) {
      return;
    }
    openMedia();
  }

  private void openMedia() {
    this.itemList = new ArrayList<MediaItem>();
    if (this.fileList.get(0).getAbsolutePath().endsWith(".m3u")) {
      // playlist file
      itemList = parseM3U(this.fileList.get(0));
    } else {
      // individual media files
      for (File file : fileList) {
        MediaItem item = new MediaItem();
        String filePath = this.uriAddOn + file.getAbsolutePath().replace("\\", "/").replace("\s", "%20");
        item.setPath(filePath);
        itemList.add(item);
      }
    }
  }

  private ArrayList<MediaItem> parseM3U(File m3u) {
    ArrayList<MediaItem> itemList = new ArrayList<MediaItem>();
    try {
      BufferedReader reader = new BufferedReader(new FileReader(m3u));

      // Start reading the file
      String line;
      MediaItem item = new MediaItem();
      while ((line = reader.readLine()) != null) {
        if (line.startsWith(extM3UTemplate) || line.isBlank()) {
          continue;
        } else {
          // skip over "#EXTINF", "%d,", " - "
          // remainder is, in order, Artist and Title
          int artistIndex = line.indexOf(", "), titleIndex = line.indexOf(" - ");
          item.setArtist(line.substring(artistIndex + 2, titleIndex));
          item.setTitle(line.substring(titleIndex + 3, line.length()));

          // read next line. will be the filePath.
          line = reader.readLine();
          item.setPath(this.uriAddOn + line.replace("\\", "/").replace("\s", "%20"));
          itemList.add(item);
          item = new MediaItem();
        }
      }
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return itemList;
  }

  public void saveToFile (Playlist playlist) {
    File m3uFile = this.fc.showSaveDialog(window);
    
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(m3uFile));
      writer.write(extM3UTemplate);
      // grab all the metadata from the medias
      // and put it into the mediaItems
      for (int i = 0; i < playlist.getItemList().size(); i++) {
        if (playlist.getItemList().get(i).getDuration() == 0) {
          // cannot retrive duration if song has not played yet. wow.
          int seconds = (int) playlist.getMediaList().get(i).getDuration().toSeconds();
          System.out.println(seconds);
        }
        if (playlist.getItemList().get(i).getArtist() == null) {
          String artist = playlist.getMediaList().get(i).getMetadata().get("artist").toString();
          // for some unknown reason, nulls are included.
          if (artist.endsWith("\0")) {
            artist = artist.replace("\0", "");
          }
          playlist.getItemList().get(i).setArtist(artist);
        }
        if (playlist.getItemList().get(i).getTitle() == null) {
          String title = playlist.getMediaList().get(i).getMetadata().get("title").toString();
          // for some unknown reason, nulls are included.
          if (title.endsWith("\0")) {
            title = title.replace("\0", "");
          }
          playlist.getItemList().get(i).setTitle(title);
        }
      }
      for (MediaItem item : playlist.getItemList()) {
        writer.write("\n\n");
        // Trim out the file://
        String path = item.getPath().replace(uriAddOn, "");
        // add in the data
        writer.write(String.format(extInfTemplate, item.getDuration(), item.getArtist(), item.getTitle()));
        writer.write("\n");
        writer.write(path);
      }
      writer.write("\n");
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public ArrayList<MediaItem> getItemList() {
    return this.itemList;
  }
}
