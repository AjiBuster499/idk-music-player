package com.ajibuster.app.view;

import com.ajibuster.app.FileWindow;
import com.ajibuster.app.eventbus.EventBus;
import com.ajibuster.app.eventbus.events.OpenEvent;

import javafx.event.ActionEvent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class TopMenu extends MenuBar {

  private Menu menuFile;
  private MenuItem open;
  private EventBus eventBus;
  private String musicPath;

  public TopMenu (EventBus eventBus) {
    this.menuFile = new Menu("File");

    this.eventBus = eventBus;
    
    this.open = new MenuItem("Open File...");

    this.open.setOnAction(this::handleOpen);

    this.menuFile.getItems().addAll(this.open);

    this.getMenus().addAll(this.menuFile);
    
  }

  private void handleOpen (ActionEvent aEvent) {
    FileWindow fw = new FileWindow();
    this.musicPath = fw.openMusic();
    this.eventBus.emit(new OpenEvent());
  }

  public String getMusicPath () {
    return this.musicPath;
  }
  
}
