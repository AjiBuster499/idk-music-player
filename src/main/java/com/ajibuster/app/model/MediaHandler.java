package com.ajibuster.app.model;

import com.ajibuster.app.eventbus.EventBus;
import com.ajibuster.app.eventbus.EventListener;
import com.ajibuster.app.eventbus.events.*;

import javafx.concurrent.Task;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;

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
    eventBus.listen(SeekTimeEvent.class, new SeekTimeEventListener());
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
        timeThread.join(650);
      } catch (InterruptedException e) {
        System.out.println("Interrupted: ");
        e.printStackTrace();
      }
    }
  }

  private class SeekTimeEventListener implements EventListener<SeekTimeEvent> {

    @Override
    public void handle(SeekTimeEvent event) {
      double rawTime = (event.getTimePercentage() * player.getStopTime().toSeconds()); // In Seconds
      Duration seekTime = new Duration(rawTime * 1000); // Convert rawTime to Millis
      System.out.println(seekTime);
      player.seek(seekTime);
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
            Thread.sleep(650);
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
