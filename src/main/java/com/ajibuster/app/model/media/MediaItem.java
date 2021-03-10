package com.ajibuster.app.model.media;

public class MediaItem {
  private String mediaPath, albumArtist, title;

  public MediaItem(String path) {
    this.mediaPath = path;
  }

  public String getPath() {
    return this.mediaPath;
  }

  public String getArtist () {
    return this.albumArtist;
  }

  public String title () {
    return this.title;
  }
  
}
