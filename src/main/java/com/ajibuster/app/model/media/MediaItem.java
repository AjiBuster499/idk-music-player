package com.ajibuster.app.model.media;

public class MediaItem {
  private String mediaPath, albumArtist, title;
  private int seconds = 0;

  public MediaItem () {
  }

  public String getArtist () {
    return this.albumArtist;
  }

  public void setArtist (String artist) {
    this.albumArtist = artist;
  }

  public String getPath () {
    return this.mediaPath;
  }

  public void setPath (String path) {
    this.mediaPath = path;
  }

  public int getSeconds () {
    return this.seconds;
  }

  public void setSeconds (int seconds) {
    this.seconds = seconds;
  }

  public String getTitle () {
    return this.title;
  }

  public void setTitle (String title) {
    this.title = title;
  }
  
}
