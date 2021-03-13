package com.ajibuster.app.model.media;

import java.util.ArrayList;

import javafx.scene.media.Media;

public class Playlist {
  private int index = 0;

  private boolean endOfPlaylist = false;

  private ArrayList<Media> mediaList;
  private ArrayList<MediaItem> itemList;
  
  public Playlist (ArrayList<MediaItem> itemList) {
    this.mediaList = createMedia(itemList);
    this.itemList = itemList;
  }

  private ArrayList<Media> createMedia (ArrayList<MediaItem> itemList) {
    this.mediaList = new ArrayList<>();
    for (MediaItem item : itemList) {
      this.mediaList.add(new Media(item.getPath()));
    }

    return this.mediaList;
  }

  public Media next() {
    // skip to next song
    if (this.index == this.mediaList.size() - 1) {
      this.endOfPlaylist = true;
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

  public ArrayList<Media> queue (ArrayList<MediaItem> newItems) {
    this.mediaList.addAll(createMedia(newItems));
    return this.mediaList;
  }

  public Media getCurrentMedia () {
    return this.mediaList.get(index);
  }

  public ArrayList<Media> getMediaList () {
    return this.mediaList;
  }

  public ArrayList<MediaItem> getItemList () {
    return this.itemList;
  }

  public boolean isEndOfPlaylist () {
    return this.endOfPlaylist;
  }
}
