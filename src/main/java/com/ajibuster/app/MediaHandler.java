package com.ajibuster.app;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MediaHandler {
  MediaPlayer player;
  Media media;

  public MediaHandler(String filePath) {
    this.media = new Media(filePath);
    this.player = new MediaPlayer(media);
  }

  public MediaHandler() {
  }

  public void playMusic() {
    player.play();
  }

  public void pauseMusic () {
    player.pause();
  }

  public static MediaHandler changeSong(String fileName) {
    MediaHandler mh = new MediaHandler(fileName);
    return mh;
  }
  
}
