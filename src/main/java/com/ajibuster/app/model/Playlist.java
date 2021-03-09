package com.ajibuster.app.model;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.media.Media;

public class Playlist {
  private int index = 0;

  private List<MediaItem> itemList;
  private List<Media> mediaList;
  
  public Playlist (ArrayList<Media> mediaList, ArrayList<MediaItem> itemList) {
    this.itemList = itemList;
    this.mediaList = mediaList;
    
    // Use itemlist to set every media's title and artist.
    
  }

  public Media next() {
    // skip to next song
    if (this.index == this.mediaList.size()) {
      this.index = 0;
    } else {
      this.index++;
    }
    return this.mediaList.get(index);
  }

  public Media prev() {
    // return to previous song
    if (this.index == 0) {
      this.index = this.mediaList.size();
    } else {
      this.index--;
    }
    return this.mediaList.get(index);
  }

  public void shuffle() {
    // shuffle playlist
    // return value TBD
  }

  public Media getCurrentMedia () {
    return this.mediaList.get(index);
  }
}
