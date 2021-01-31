package com.ajibuster.app.model;

import com.ajibuster.app.eventbus.EventBus;
import com.ajibuster.app.eventbus.EventListener;
import com.ajibuster.app.eventbus.events.*;

import javafx.concurrent.Task;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;

public class MediaHandler {
  private MediaPlayer player;
  private Media media;

  private Thread timeThread;

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
      if (player.getStatus() == Status.STOPPED) {
        player.setOnPlaying(() -> {
          startTime();
        });
      }
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
      try {
        timeThread.join(1000);
      } catch (InterruptedException e) {
        System.out.println("Interrupted: ");
        e.printStackTrace();
      }
    }
  }

  public void createNewPlayer(String filePath) {
    if (this.player != null) {
      this.player.dispose();
    }
    this.media = new Media(filePath);
    this.player = new MediaPlayer(this.media);
    this.player.setAutoPlay(true);
    this.player.setOnPlaying(() -> {
      startTime();
    });
  }

  private void startTime() {
    Task<Void> task = new Task<Void>() {
      @Override
      protected Void call() throws Exception {
        while (player.getCurrentTime().toSeconds() < player.getStopTime().toSeconds()
        && player.getStatus() == Status.PLAYING) {
          double time = player.getCurrentTime().toSeconds() / player.getStopTime().toSeconds();
          eventBus.emit(new CurrentTimeEvent(time));
          try {
            Thread.sleep(500);
          } catch (InterruptedException ie) {
            System.out.println("Interrupted.");
            ie.printStackTrace();
            cancel();
            break;
          }
        }
        return null;
      }
    };

    timeThread = new Thread(task);
    timeThread.setDaemon(true);
    timeThread.start();
  }
}
