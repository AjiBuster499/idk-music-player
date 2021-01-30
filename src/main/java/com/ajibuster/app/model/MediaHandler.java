package com.ajibuster.app.model;

import com.ajibuster.app.eventbus.EventBus;
import com.ajibuster.app.eventbus.EventListener;
import com.ajibuster.app.eventbus.events.*;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;

public class MediaHandler {
  private MediaPlayer player;
  private Media media;

  private EventBus eventBus;

  public MediaHandler(EventBus eventBus) {
    this.eventBus = eventBus;

    eventBus.listen(PlayEvent.class, new PlayEventListener());
    eventBus.listen(PauseEvent.class, new PauseEventListener());
    eventBus.listen(StopEvent.class, new StopEventListener());
  }

  private class PlayEventListener implements EventListener<PlayEvent> {
    @Override
    public void handle(PlayEvent event) {
      player.play();
    }
  }

  private class PauseEventListener implements EventListener<PauseEvent> {
    @Override
    public void handle(PauseEvent event) {
      player.pause();
    }
  }

  private class StopEventListener implements EventListener<StopEvent> {
    @Override
    public void handle(StopEvent event) {
      player.stop();
    }
  }

  public void createNewPlayer(String filePath) {
    if (this.player != null) {
      this.player.dispose();
    }
    this.media = new Media(filePath);
    this.player = new MediaPlayer(this.media);
    this.player.setOnPlaying(() -> {
      startTime();
    });
  }

  public void startTime() {
    while (player.getCurrentTime().toMillis() < player.getStopTime().toMillis()
        && player.getStatus() == Status.PLAYING) {
      double time = player.getCurrentTime().toSeconds() / player.getStopTime().toSeconds();
      eventBus.emit(new CurrentTimeEvent(time));
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
  }
}
