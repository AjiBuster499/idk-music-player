package com.ajibuster.app;

import com.ajibuster.app.view.*;
import com.ajibuster.app.eventbus.EventBus;
import com.ajibuster.app.eventbus.EventListener;
import com.ajibuster.app.eventbus.events.OpenMediaEvent;
import com.ajibuster.app.eventbus.events.OpenMultipleMediaEvent;
import com.ajibuster.app.eventbus.events.OpenPlaylistEvent;
import com.ajibuster.app.eventbus.events.SaveToPlaylistEvent;
import com.ajibuster.app.model.*;

import javafx.scene.layout.BorderPane;

public class MusicPlayer extends BorderPane {

  private TopMenu topMenu;
  private CenterPane centerPane;

  private MediaHandler mediaHandler;

  public MusicPlayer(MediaHandler mediaHandler, EventBus eventBus) {
    this.topMenu = new TopMenu(eventBus);
    BottomPane bottomPane = new BottomPane(eventBus);
    LeftPane leftPane = new LeftPane();
    this.centerPane = new CenterPane(eventBus);
    this.mediaHandler = mediaHandler;

    leftPane.setStyle("-fx-border-style: hidden solid hidden hidden; -fx-border-color: black");
    // CLEANUP: Create a .css and put style in there.

    this.setCenter(this.centerPane);
    this.setLeft(leftPane);
    this.setTop(this.topMenu);
    this.setBottom(bottomPane);

    eventBus.listen(OpenMediaEvent.class, new OpenMediaEventListener());
    eventBus.listen(OpenPlaylistEvent.class, new OpenPlaylistEventListener());
    eventBus.listen(OpenMultipleMediaEvent.class, new OpenMultipleMediaEventListener());
    eventBus.listen(SaveToPlaylistEvent.class, new SaveToPlaylistEventListener());
  }

  //#region
  private class OpenMediaEventListener implements EventListener<OpenMediaEvent> {
    @Override
    public void handle(OpenMediaEvent event) {
      // Check for a player
      if (!mediaHandler.isPlayerAlive()) {
        // no player
        mediaHandler.createNewPlayer(topMenu.getItemList());
      } else {
        // just add it to the queue
        mediaHandler.addMedia(topMenu.getItemList());
      }
    }
  }

  private class OpenPlaylistEventListener implements EventListener<OpenPlaylistEvent> {
    @Override
    public void handle(OpenPlaylistEvent event) {
      // Create a new player, regardless of whether one exists.
      mediaHandler.createNewPlayer(topMenu.getItemList());
    }
  }

  private class OpenMultipleMediaEventListener implements EventListener<OpenMultipleMediaEvent> {
    @Override
    public void handle(OpenMultipleMediaEvent event) {
      // Same as OpenMediaEventListener for now.
      // Check for a player
      if (!mediaHandler.isPlayerAlive()) {
        // no player
        mediaHandler.createNewPlayer(topMenu.getItemList());
      } else {
        // just add it to the queue
        mediaHandler.addMedia(topMenu.getItemList());
      }
    }
  }

  private class SaveToPlaylistEventListener implements EventListener<SaveToPlaylistEvent> {

    @Override
    public void handle(SaveToPlaylistEvent event) {
      SaveFileWindow fw = new SaveFileWindow("Save to Playlist...");
      fw.saveToFile(mediaHandler.getPlaylist().getMediaPaths());
      
    }

  }
  //#endregion
}
