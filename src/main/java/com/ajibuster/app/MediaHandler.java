package com.ajibuster.app;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MediaHandler {
  public static void playMusic () {
    Media m = new Media("file:///media/sf_Dev/Java/PersonalProjects/JavaFX/musicplayer/Tactics.mp3");
    MediaPlayer player = new MediaPlayer(m);

    player.play();

  }
  
}
