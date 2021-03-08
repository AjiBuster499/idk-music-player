package com.ajibuster.app.view;

import java.util.ArrayList;

import com.ajibuster.app.FileWindow;
import com.ajibuster.app.eventbus.EventBus;
import com.ajibuster.app.eventbus.events.OpenMediaEvent;
import com.ajibuster.app.model.MediaItem;

import javafx.event.ActionEvent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class TopMenu extends MenuBar {

  private Menu menuFile;
  private MenuItem openMedia, openPlaylist;
  private EventBus eventBus;
  private ArrayList<MediaItem> itemList;

  public TopMenu (EventBus eventBus) {
    this.menuFile = new Menu("File");

    this.eventBus = eventBus;
    
    this.openMedia = new MenuItem("Open File...");
    this.openPlaylist = new MenuItem("Open Playlist...");

    this.openMedia.setOnAction(this::handleOpenMedia);
    this.openPlaylist.setOnAction(this::handleOpenMedia);

    this.menuFile.getItems().addAll(this.openMedia, this.openPlaylist);

    this.getMenus().addAll(this.menuFile);
    
  }

  private void handleOpenMedia (ActionEvent aEvent) {
    FileWindow fw = new FileWindow();
    this.itemList = fw.openMedia();
    this.eventBus.emit(new OpenMediaEvent());
  }

  public ArrayList<MediaItem> getItemList () {
    return this.itemList;
  }
  
}
