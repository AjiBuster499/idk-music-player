package com.ajibuster.app;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MediaHandler {
  MediaPlayer player;
  Media media;

  public MediaHandler() {
    this.media = new Media("file:///media/sf_Dev/Java/PersonalProjects/JavaFX/musicplayer/Tactics.mp3");
    this.player = new MediaPlayer(media);
  }

  public void playMusic() {
    player.play();
  }

  public void pauseMusic () {
    player.pause();
  }
  
}
