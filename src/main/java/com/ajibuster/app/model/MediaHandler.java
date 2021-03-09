package com.ajibuster.app.model;

import java.util.ArrayList;

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
  private ArrayList<Media> mediaList;
  private Playlist playlist;

  private Thread timeThread;

  private EventBus eventBus;
  
  private RepeatStatus repeatStatus = RepeatStatus.REPEAT_OFF;

  public MediaHandler(EventBus eventBus) {
    this.eventBus = eventBus;

    this.mediaList = new ArrayList<Media>();

    // TO WHOEVER READS THIS MESS:
    // It's just establishing listeners. Carry On.
    //#region
    eventBus.listen(PlayEvent.class, new PlayEventListener());
    eventBus.listen(PauseEvent.class, new PauseEventListener());
    eventBus.listen(StopEvent.class, new StopEventListener());
    eventBus.listen(SeekTimeEvent.class, new SeekTimeEventListener());
    eventBus.listen(ForwardEvent.class, new ForwardEventListener());
    eventBus.listen(RewindEvent.class, new RewindEventListener());
    eventBus.listen(VolumeChangedEvent.class, new VolumeChangedEventListener());
    eventBus.listen(RepeatStatusChangeEvent.class, new RepeatStatusChangeEventListener());
    //#endregion
  }

  // Generates new MediaPlayers for a set of songs
  public void createNewPlayer(ArrayList<MediaItem> itemList) {
    // If there's a player, dispose of it.
    if (this.player != null) {
      this.player.dispose();
    }

    // Empty the mediaList
    if (!this.mediaList.isEmpty()) {
      this.mediaList.clear();
    }

    // Generate new mediaList
    for (int i = 0; i < itemList.size(); i++) {
      // Creates a new Media for each path
      this.mediaList.add(new Media(itemList.get(i).getPath()));
    }
    // wHaT iS mY pUrPoSe?
    this.playlist = new Playlist(this.mediaList, itemList);
    do {
      // Establish a player for the first song and play it.
      this.player = new MediaPlayer(this.playlist.getCurrentMedia());
      initPlay();
    } while (repeatStatus == RepeatStatus.REPEAT_ON);
  }

  private void initPlay () {
    this.player.setOnEndOfMedia(() -> {
      switch (repeatStatus) {
      case REPEAT_OFF:
        // do not repeat on end
      case REPEAT_ON:
        // repeat on end
        player.dispose();
        player = new MediaPlayer(playlist.next());
        initPlay();
        break;
      case REPEAT_ONE:
        // repeat this media
        player.seek(Duration.ZERO);
        break;
      }
      System.out.println("end of media: " + playlist.getCurrentMedia());
    });
    // this.player.setAutoPlay(true);
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
            // TODO: Figure out if this is extraneous
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

  public ImageView getAlbumArt () {
    // Fetches Album Cover from metadata.
    ImageView iv = new ImageView((Image) this.playlist.getCurrentMedia().getMetadata().get("image"));
    return iv;
  }

  public MediaPlayer getPlayer () {
    return this.player;
  }

  public ArrayList<Media> getMediaList () {
    return this.mediaList;
  }

  // The World's Supply of Event Listeners
  //#region
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
        case REPEAT_OFF:
          repeatStatus = RepeatStatus.REPEAT_OFF;
          player.setCycleCount(1);
          break;
        case REPEAT_ON:
          repeatStatus = RepeatStatus.REPEAT_ON;
          player.setCycleCount(1);
          break;
        case REPEAT_ONE:
          // Return when playlists are implemented
          repeatStatus = RepeatStatus.REPEAT_ONE;
          player.setCycleCount(MediaPlayer.INDEFINITE);
          break;
      } 
    }
  }
  //#endregion
  // END LISTENERS
}

