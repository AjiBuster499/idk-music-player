package com.ajibuster.app.model;

import com.ajibuster.app.eventbus.EventBus;
import com.ajibuster.app.eventbus.EventListener;
import com.ajibuster.app.eventbus.events.PlayEvent;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;

public class MediaHandler {
  private MediaPlayer player;
  private Media media;
  public double duration;

  public MediaHandler (EventBus eventBus) {
    eventBus.listen(PlayEvent.class, new PlayEventListener());
  }

  private class PlayEventListener implements EventListener<PlayEvent> {

    @Override
    public void handle(PlayEvent event) {
      player.play();
    }

  }

  public void createNewPlayer(String filePath) {
    if (this.player != null) {
      this.player.dispose();
    }
    this.media = new Media(filePath);
    this.player = new MediaPlayer(this.media);
  }

  public void pauseMusic () {
    player.pause();
  }

  public void stopMusic () {
    player.stop();
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
