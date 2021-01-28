package com.ajibuster.app.model;

import com.ajibuster.app.eventbus.EventListener;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;

public class MediaHandler {
  private MediaPlayer player;
  private Media media;
  public double duration;

  public MediaHandler() {
  }

  private MediaHandler(String filePath) {
    this.media = new Media(filePath);
    this.player = new MediaPlayer(media);
    this.duration = this.media.getDuration().toSeconds();
    this.player.setAutoPlay(true);
    // Tell EventBus listen for play event
    // eventbus.listen(PlayEvent, PlayEventListener);
  }

  // private class PlayEventListener implements EventListener<PlayEvent>
  //  handle(PlayEvent) {
  //    player.play();
  //  }

  private class PlayEventListener implements EventListener<PlayEvent> {

    @Override
    public void handle(PlayEvent event) {
      // TODO Auto-generated method stub

    }

  }

  public void playMusic() {
    // Listener for Play
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

  public MediaPlayer getPlayer () {
    return this.player;
  }

  public Media getMedia () {
    return this.media;
    
  }

  public boolean isPlaying () {
    return player.getStatus() == Status.PLAYING ? true : false;
  }

}
