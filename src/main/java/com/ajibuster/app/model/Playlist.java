package com.ajibuster.app.model;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.media.Media;

public class Playlist {

  private List<MediaItem> itemList;
  private List<Media> mediaList;
  private MediaHandler mediaHandler;
  
  public Playlist (MediaHandler mediaHandler, ArrayList<MediaItem> itemList) {
    this.itemList = itemList;
    this.mediaHandler = mediaHandler;
    this.mediaList = mediaHandler.getMediaList();
  }

  public void next() {
    // skip to next song
  }

  public void prev() {
    // return to previous song
  }

  public void shuffle() {
    // shuffle playlist
    // return value TBD
  }
}
