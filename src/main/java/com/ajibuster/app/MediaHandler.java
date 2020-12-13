package com.ajibuster.app;

import java.nio.file.Paths;

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

  public static MediaHandler switchSong(String fileName) {
    MediaHandler mh = new MediaHandler(Paths.get(fileName).toUri().toString());
    return mh;
  }
  
}
