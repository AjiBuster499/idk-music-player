package com.ajibuster.app;

import javafx.collections.ObservableMap;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MediaHandler {
  private MediaPlayer player;
  private Media media;

  public MediaHandler(String filePath) {
    this.media = new Media(filePath);
    this.player = new MediaPlayer(media);
    this.player.setAutoPlay(true);
  }

  public MediaHandler() {
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

  public void showMetadata () {
    player.setOnReady(new Runnable(){

      @Override
      public void run() {
        ObservableMap<String, Object> metadata = media.getMetadata();

        for (String key : metadata.keySet()) {
          System.out.println(key + " = " + metadata.get(key));
        }

      }
      
    });
  }

  public void showAlbumCover (ImageView imageView) {
    player.setOnReady(new Runnable () {
      @Override
      public void run () {
        Image albumCover = (Image) player.getMedia().getMetadata().get("image");
        imageView.setImage(albumCover);
      }
    });
  }

  public static MediaHandler changeSong(String fileName) {
    MediaHandler mh = new MediaHandler(fileName);
    return mh;
  }
  
}
