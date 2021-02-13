package com.ajibuster.app.model;

import com.ajibuster.app.eventbus.EventBus;
import com.ajibuster.app.eventbus.EventListener;
import com.ajibuster.app.eventbus.events.*;

import javafx.concurrent.Task;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    // TODO CLEANUP: simplify this.
    // TO WHOEVER READS THIS MESS:
    // It's just establishing listeners. Carry On.
    eventBus.listen(PlayEvent.class, new PlayEventListener());
    eventBus.listen(PauseEvent.class, new PauseEventListener());
    eventBus.listen(StopEvent.class, new StopEventListener());
    eventBus.listen(SeekTimeEvent.class, new SeekTimeEventListener());
    eventBus.listen(ForwardEvent.class, new ForwardEventListener());
    eventBus.listen(RewindEvent.class, new RewindEventListener());
    eventBus.listen(VolumeUpEvent.class, new VolumeUpEventListener());
    eventBus.listen(VolumeDownEvent.class, new VolumeDownEventListener());
  }

  // The World's Supply of Event Listeners
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
      timeThread.interrupt();
    }
  }
  private class SeekTimeEventListener implements EventListener<SeekTimeEvent> {

    @Override
    public void handle(SeekTimeEvent event) {
      double rawTime = (event.getTimePercentage() * player.getStopTime().toSeconds()); // In Seconds
      Duration seekTime = new Duration(rawTime * 1000); // Convert rawTime to Millis
      player.seek(seekTime);
    }

  }
  private class RewindEventListener implements EventListener<RewindEvent> {

    @Override
    public void handle(RewindEvent event) {
      Duration rewindTime = player.getCurrentTime().subtract(new Duration(5000));
      player.seek(rewindTime);
    }

  }
  private class ForwardEventListener implements EventListener<ForwardEvent> {

    @Override
    public void handle(ForwardEvent event) {
      Duration forwardTime = player.getCurrentTime().add(new Duration(5000));
      player.seek(forwardTime);

    }

  }
  private class VolumeUpEventListener implements EventListener<VolumeUpEvent> {

    @Override
    public void handle(VolumeUpEvent event) {
      player.setVolume(player.getVolume() + 0.05);
      // TODO CLEANUP: Fix this crap
      // Then Do the Same Below
      if (player.getVolume() <= 1) {
        eventBus.emit(new VolumeChangedEvent(player.getVolume()));
      }
      if (player.getVolume() > 1) {
        return;
      }
      

    }

  }
  private class VolumeDownEventListener implements EventListener<VolumeDownEvent> {

    @Override
    public void handle(VolumeDownEvent event) {
      player.setVolume(player.getVolume() - 0.05);
      eventBus.emit(new VolumeChangedEvent(player.getVolume()));

    }

  }

  // Generates a new Media Player
  public void createNewPlayer(String filePath) {
    // If there's a player, dispose of it.
    if (this.player != null) {
      this.player.dispose();
    }

    // Generation
    this.media = new Media(filePath);
    this.player = new MediaPlayer(this.media);
    this.player.setAutoPlay(true);
    this.player.setOnPlaying(() -> {
      // Start Tracking Time
      startTime();
    });
    this.player.setOnEndOfMedia(() -> {
      // Reset Time. Stop Media.
      player.seek(Duration.valueOf("0.0ms"));
      player.stop();
    });
  }

  private void startTime() {
    // Task to track the time on a separate thread.
    Task<Void> task = new Task<Void>() {
      @Override
      protected Void call() throws Exception {
        while (player.getCurrentTime().lessThanOrEqualTo(player.getStopTime()) && player.getStatus() == Status.PLAYING) {
          double timePercentage = player.getCurrentTime().toSeconds() / player.getStopTime().toSeconds();
          eventBus.emit(new CurrentTimeEvent(timePercentage));
          try {
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            System.out.println("Thread " + Thread.currentThread().getName() + " interrupted.");
            cancel();
          }
        }
        return null;
      }
    };

    timeThread = new Thread(task);
    timeThread.setDaemon(true);
    timeThread.start();
  }

  public ImageView getAlbumArt () {
    // Fetches Album Cover from metadata.
    ImageView iv = new ImageView((Image) this.media.getMetadata().get("image"));
    return iv;
  }

  public MediaPlayer getPlayer () {
    return this.player;
  }
}
