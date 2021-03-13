package com.ajibuster.app;

import com.ajibuster.app.view.*;
import com.ajibuster.app.view.files.FileWindow;

import java.util.ArrayList;

import com.ajibuster.app.eventbus.EventBus;
import com.ajibuster.app.eventbus.EventListener;
import com.ajibuster.app.eventbus.events.*;
import com.ajibuster.app.model.*;
import com.ajibuster.app.model.media.MediaItem;

import javafx.scene.layout.BorderPane;

public class MusicPlayer extends BorderPane {
  private ArrayList<MediaItem> itemList;

  private TopMenu topMenu;
  private CenterPane centerPane;
  private BottomPane bottomPane;
  private LeftPane leftPane;

  private MediaHandler mediaHandler;

  public MusicPlayer(MediaHandler mediaHandler, EventBus eventBus) {
    this.topMenu = new TopMenu(eventBus);
    this.bottomPane = new BottomPane(eventBus);
    this.leftPane = new LeftPane();
    this.centerPane = new CenterPane(eventBus);
    this.mediaHandler = mediaHandler;

    leftPane.setStyle("-fx-border-style: hidden solid hidden hidden; -fx-border-color: black");
    // CLEANUP: Create a .css and put style in there.

    this.setCenter(this.centerPane);
    this.setLeft(this.leftPane);
    this.setTop(this.topMenu);
    this.setBottom(this.bottomPane);

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
      FileWindow fw = new FileWindow("Open Single Media", event.getValue());
      fw.display();
      itemList = fw.getItemList();
      if (!mediaHandler.isPlayerAlive()) {
        // no player
        mediaHandler.createNewPlayer(itemList);
      } else {
        // just add it to the queue
        mediaHandler.addMedia(itemList);
      }
    }
  }

  private class OpenPlaylistEventListener implements EventListener<OpenPlaylistEvent> {
    @Override
    public void handle(OpenPlaylistEvent event) {
      FileWindow fw = new FileWindow("Open Playlist", event.getValue());
      fw.display();
      itemList = fw.getItemList();
      // Create a new player, regardless of whether one exists.
      mediaHandler.createNewPlayer(itemList);
    }
  }

  private class OpenMultipleMediaEventListener implements EventListener<OpenMultipleMediaEvent> {
    @Override
    public void handle(OpenMultipleMediaEvent event) {
      // Same as OpenMediaEventListener for now.
      // Check for a player
      FileWindow fw = new FileWindow("Open Multiple Media", event.getValue());
      fw.display();
      itemList = fw.getItemList();
      if (!mediaHandler.isPlayerAlive()) {
        // no player
        mediaHandler.createNewPlayer(itemList);
      } else {
        // just add it to the queue
        mediaHandler.addMedia(itemList);
      }
    }
  }

  private class SaveToPlaylistEventListener implements EventListener<SaveToPlaylistEvent> {

    @Override
    public void handle(SaveToPlaylistEvent event) {
      FileWindow fw = new FileWindow("Save to Playlist...", event.getValue());
      fw.saveToFile(mediaHandler.getPlaylist());    
    }

  }
  //#endregion
}
