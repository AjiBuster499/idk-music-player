package com.ajibuster.app;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;

public class MediaHandler {
  private MediaPlayer player;
  private Media media;

  public MediaHandler() {
  }

  private MediaHandler(String filePath) {
    this.media = new Media(filePath);
    this.player = new MediaPlayer(media);
    // this.player.setAutoPlay(true);
  }

  public void playMusic() {
    player.play();
  }

  public void pauseMusic () {
    player.pause();
  }

  public void stopMusic () {
    player.stop();
  }

  public static MediaHandler changeSong(String fileName) {
    MediaHandler mh = new MediaHandler(fileName);
    return mh;
  }
  
  public void getAlbumCover (ImageView imageView) {
    player.setOnReady(new Runnable () {
      @Override
      public void run () {
        Image albumCover = (Image) player.getMedia().getMetadata().get("image");
        imageView.setImage(albumCover);
      }
    });
  }

  public MediaPlayer getPlayer () {
    return player;
  }

  public boolean isPlaying() {
    if (player.getStatus() == Status.PLAYING) {
      return true;
    } else {
      return false;
    }
  }
  
}
