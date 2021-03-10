package com.ajibuster.app.model.media;

import java.util.ArrayList;

import com.ajibuster.app.eventbus.EventBus;
import com.ajibuster.app.eventbus.events.SkipMediaEvent;

import javafx.scene.media.Media;

public class Playlist {
  private int index = 0;

  private boolean repeating = false;
  private boolean endOfPlaylist = false;

  private EventBus eventBus;

  private ArrayList<Media> mediaList;
  
  public Playlist (ArrayList<MediaItem> itemList, EventBus eventBus) {
    this.mediaList = createMedia(itemList);
    this.eventBus = eventBus;
    
    // Use itemlist to set every media's title and artist.
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
      if (this.repeating) {
        this.index = 0;
      }
    } else {
      this.index++;
    }
    this.eventBus.emit(new SkipMediaEvent());
    return this.mediaList.get(index);
  }

  public Media prev() {
    // return to previous song
    if (this.index == 0) {
      this.index = this.mediaList.size();
    } else {
      this.index--;
    }
    this.eventBus.emit(new SkipMediaEvent());
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

  public void setRepeating (boolean newRepeating) {
    this.repeating = newRepeating;
  }

  public boolean isEndOfPlaylist () {
    return this.endOfPlaylist;
  }
}
