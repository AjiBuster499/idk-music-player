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

  public void playMusic() {
    player.play();
  }

  public void pauseMusic () {
    player.pause();
  }
  
}
