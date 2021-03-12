package com.ajibuster.app.view;

import com.ajibuster.app.eventbus.EventBus;
import com.ajibuster.app.eventbus.events.OpenMediaEvent;
import com.ajibuster.app.eventbus.events.OpenMultipleMediaEvent;
import com.ajibuster.app.eventbus.events.OpenPlaylistEvent;
import com.ajibuster.app.eventbus.events.SaveToPlaylistEvent;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

public class TopMenu extends MenuBar {

  private Menu menuFile;

  public TopMenu (EventBus eventBus) {
    this.menuFile = new Menu("File");
    
    MenuItem openMedia = new MenuItem("Open File...");
    MenuItem openPlaylist = new MenuItem("Open Playlist...");
    MenuItem openMediaMulti = new MenuItem("Open Multiple Files...");
    MenuItem saveToPlaylist = new MenuItem("Save to Playlist...");

    openMedia.setOnAction(e -> eventBus.emit(new OpenMediaEvent()));
    openPlaylist.setOnAction(e -> eventBus.emit(new OpenPlaylistEvent()));
    openMediaMulti.setOnAction(e -> eventBus.emit(new OpenMultipleMediaEvent()));
    saveToPlaylist.setOnAction(e -> eventBus.emit(new SaveToPlaylistEvent()));

    this.menuFile.getItems().addAll(openMedia, openPlaylist, openMediaMulti, new SeparatorMenuItem(), saveToPlaylist);

    this.getMenus().addAll(this.menuFile);
    
  }
}
