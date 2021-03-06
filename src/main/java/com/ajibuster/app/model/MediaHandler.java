package com.ajibuster.app.model;

import java.util.ArrayList;

import com.ajibuster.app.eventbus.EventBus;
import com.ajibuster.app.eventbus.EventListener;
import com.ajibuster.app.eventbus.events.*;
import com.ajibuster.app.model.media.*;

import javafx.concurrent.Task;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;

public class MediaHandler {
  private MediaPlayer player;
  private Playlist playlist;

  private Thread timeThread;

  private EventBus eventBus;
  
  private RepeatStatus repeatStatus = RepeatStatus.REPEAT_OFF;

  public MediaHandler(EventBus eventBus) {
    this.eventBus = eventBus;

    // TO WHOEVER READS THIS MESS:
    // It's just establishing listeners. Carry On.
    //#region
    eventBus.listen(PlayMediaEvent.class, new PlayMediaEventListener());
    eventBus.listen(PauseMediaEvent.class, new PauseMediaEventListener());
    eventBus.listen(StopMediaEvent.class, new StopMediaEventListener());
    eventBus.listen(SeekTimeEvent.class, new SeekTimeEventListener());
    eventBus.listen(ForwardEvent.class, new ForwardEventListener());
    eventBus.listen(RewindEvent.class, new RewindEventListener());
    eventBus.listen(VolumeChangedEvent.class, new VolumeChangedEventListener());
    eventBus.listen(RepeatStatusChangeEvent.class, new RepeatStatusChangeEventListener());
    //#endregion
  }

  public void addMedia (ArrayList<MediaItem> items) {
    // Add media to the playlist
    this.playlist.queue(items);
  }

  // Generates new MediaPlayers for a set of songs
  public void createNewPlayer(ArrayList<MediaItem> itemList) {
    // eliminate the old player
    if (this.player != null) {
      this.player.dispose();
    }
    // Check the Playlist
    if (this.playlist == null) {
      // Generate a new playlist
      this.playlist = new Playlist(itemList, this.eventBus);
    }

    // Establish a player for the first song and play it.
    this.player = new MediaPlayer(this.playlist.getCurrentMedia());
    initPlay(); // recursive method
  }

  private void initPlay () {
    // CLEANUP: remove recursive
    this.player.setOnEndOfMedia(() -> {
      switch (repeatStatus) {
        case REPEAT_OFF: {
          // do not repeat playlist on end
          player.dispose();
          player = new MediaPlayer(playlist.next());
          initPlay();
          if (playlist.isEndOfPlaylist()) {
            player.seek(Duration.ZERO);
            player.stop();
            playlist.setEndOfPlaylist(false);
          }
          break;
        }
        case REPEAT_ON: {
          // repeat playlist on end
          player.dispose();
          player = new MediaPlayer(playlist.next());
          initPlay();
          break;
        }
        case REPEAT_ONE: {
          // repeat this media
          player.seek(Duration.ZERO);
          break;
        }
        case REPEAT_DEFAULT: {
          // will never be here
          break;
        }
      }
    });
    this.player.setOnReady(() -> {
      eventBus.emit(new LoadArtEvent(getAlbumArt()));
    });
    this.player.setAutoPlay(true);
    this.player.setOnPlaying(() -> {
      // Start Tracking Time
      startTime();
    });
  }

  private void startTime() {
    // Task to track the time on a separate thread.
    Task<Void> task = new Task<Void>() {
      @Override
      protected Void call() throws Exception {
        while (player.getCurrentTime().lessThanOrEqualTo(player.getStopTime()) && player.getStatus() != Status.PAUSED) {
          if (player.getStatus() == Status.STOPPED) {
            eventBus.emit(new CurrentTimeEvent(0, Duration.ZERO));
            cancel();
          }
          // Convert the current time to a percentage
          double timePercentage = player.getCurrentTime().toSeconds() / player.getStopTime().toSeconds();
          // Get the current time as a Duration
          Duration timeDuration = player.getCurrentTime();
          eventBus.emit(new CurrentTimeEvent(timePercentage, timeDuration));
          try {
            Thread.sleep(1000);
          } catch (InterruptedException e) {
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

  private ImageView getAlbumArt () {
    // Fetches Album Cover from metadata.
    ImageView iv = new ImageView((Image) this.playlist.getCurrentMedia().getMetadata().get("image"));
    // Future implementations:
    // * store in cache for faster loading
    // * be sure to give an option to empty cache
    return iv;
  }

  public Playlist getPlaylist () {
    return this.playlist;
  }

  public boolean isPlayerAlive () {
    return this.player != null ? true: false;
  }

  // The World's Supply of Event Listeners
  //#region
  private class PlayMediaEventListener implements EventListener<PlayMediaEvent> {
    @Override
    public void handle(PlayMediaEvent event) {
      if (player.getStatus() == Status.STOPPED) {
        player.setOnPlaying(() -> {
          startTime();
        });
      }
      player.play();
    }
  }
  private class PauseMediaEventListener implements EventListener<PauseMediaEvent> {
    @Override
    public void handle(PauseMediaEvent event) {
      player.pause();
    }
  }
  private class StopMediaEventListener implements EventListener<StopMediaEvent> {
    @Override
    public void handle(StopMediaEvent event) {
      player.stop();
      timeThread.interrupt();
    }
  }
  private class SeekTimeEventListener implements EventListener<SeekTimeEvent> {
    @Override
    public void handle(SeekTimeEvent event) {
      player.pause();
      double rawTime = (event.getTimePercentage() * player.getStopTime().toSeconds()); // In Seconds
      Duration seekTime = new Duration(rawTime * 1000); // Convert rawTime to Millis
      if (rawTime == 0) {
        return;
      }
      player.seek(seekTime);
      player.play();
    }
  }
  private class RewindEventListener implements EventListener<RewindEvent> {
    @Override
    public void handle(RewindEvent event) {
      // Duration rewindTime = player.getCurrentTime().subtract(new Duration(5000));
      // player.seek(rewindTime);
      if (isPlayerAlive()) {
        player.dispose();
      }
      playlist.prev();
      player = new MediaPlayer(playlist.getCurrentMedia());
      initPlay();
    }
  }
  private class ForwardEventListener implements EventListener<ForwardEvent> {
    @Override
    public void handle(ForwardEvent event) {
      // Duration forwardTime = player.getCurrentTime().add(new Duration(5000));
      // player.seek(forwardTime);
      if (isPlayerAlive()) {
        player.dispose();
      }
      playlist.next();
      player = new MediaPlayer(playlist.getCurrentMedia());
      initPlay();
    }
  }
  private class VolumeChangedEventListener implements EventListener<VolumeChangedEvent> {
    @Override
    public void handle(VolumeChangedEvent event) {
      player.setVolume(event.getVol());
    }
  }
  private class RepeatStatusChangeEventListener implements EventListener<RepeatStatusChangeEvent> {
    @Override
    public void handle(RepeatStatusChangeEvent event) {
      // Need to define cases for each status
      // Possible cleanup: Create a pattern of sorts for these switches
      switch (event.getStatus()) {
        case REPEAT_OFF:{
          repeatStatus = RepeatStatus.REPEAT_OFF;
          player.setCycleCount(1);
          break;
        }
        case REPEAT_ON:{
          repeatStatus = RepeatStatus.REPEAT_ON;
          player.setCycleCount(1);
          break;
        }
        case REPEAT_ONE:{
          // Return when playlists are implemented
          repeatStatus = RepeatStatus.REPEAT_ONE;
          player.setCycleCount(MediaPlayer.INDEFINITE);
          break;
        }
        case REPEAT_DEFAULT:{
          break;
        }
      } 
    }
  }
  //#endregion
  // END LISTENERS
}

