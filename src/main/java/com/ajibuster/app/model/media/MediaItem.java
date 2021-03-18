package com.ajibuster.app.model.media;

public class MediaItem {
  private String mediaPath, albumArtist, title;
  private int duration = 0;

  public MediaItem () {
  }

  public String getArtist () {
    return this.albumArtist;
  }

  public void setArtist (String artist) {
    this.albumArtist = artist;
  }

  public int getDuration () {
    return this.duration;
  }

  public void setDuration (int duration) {
    this.duration = duration;
  }

  public String getPath () {
    return this.mediaPath;
  }

  public void setPath (String path) {
    this.mediaPath = path;
  }

  public String getTitle () {
    return this.title;
  }

  public void setTitle (String title) {
    this.title = title;
  }
  
}
