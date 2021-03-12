package com.ajibuster.app.view;

import java.util.ArrayList;

import com.ajibuster.app.OpenFileWindow;
import com.ajibuster.app.eventbus.Event;
import com.ajibuster.app.eventbus.EventBus;
import com.ajibuster.app.eventbus.events.OpenMediaEvent;
import com.ajibuster.app.eventbus.events.OpenMultipleMediaEvent;
import com.ajibuster.app.eventbus.events.OpenPlaylistEvent;
import com.ajibuster.app.eventbus.events.SaveToPlaylistEvent;
import com.ajibuster.app.model.media.MediaItem;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

public class TopMenu extends MenuBar {

  private Menu menuFile;
  private EventBus eventBus;
  private ArrayList<MediaItem> itemList;

  public TopMenu (EventBus eventBus) {
    this.menuFile = new Menu("File");

    this.eventBus = eventBus;
    
    MenuItem openMedia = new MenuItem("Open File...");
    MenuItem openPlaylist = new MenuItem("Open Playlist...");
    MenuItem openMediaMulti = new MenuItem("Open Multiple Files...");
    MenuItem saveToPlaylist = new MenuItem("Save to Playlist...");

    openMedia.setOnAction(e -> handle(new OpenMediaEvent(), "Open a Media..."));
    openPlaylist.setOnAction(e -> handle(new OpenPlaylistEvent(), "Open a Playlist..."));
    openMediaMulti.setOnAction(e -> handle(new OpenMultipleMediaEvent(), "Open Multiple Media..."));
    saveToPlaylist.setOnAction(e -> eventBus.emit(new SaveToPlaylistEvent()));

    this.menuFile.getItems().addAll(openMedia, openPlaylist, openMediaMulti, new SeparatorMenuItem(), saveToPlaylist);

    this.getMenus().addAll(this.menuFile);
    
  }

  private void handle (Event event, String title) {
    OpenFileWindow fw = new OpenFileWindow(title);
    this.itemList = fw.openMedia();
    this.eventBus.emit(event);
  }

  public ArrayList<MediaItem> getItemList () {
    return this.itemList;
  }
  
}
