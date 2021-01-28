package com.ajibuster.app.view;

import com.ajibuster.app.FileWindow;
import com.ajibuster.app.eventbus.EventBus;
import com.ajibuster.app.eventbus.events.OpenEvent;

import javafx.event.ActionEvent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

public class TopMenu extends MenuBar {

  private Menu menuFile;
  private MenuItem open, exit;
  private EventBus eventBus;
  private String musicPath;

  public TopMenu (EventBus eventBus) {
    this.menuFile = new Menu("File");

    this.eventBus = eventBus;
    
    this.open = new MenuItem("Open File...");
    this.exit = new MenuItem("Exit");

    this.exit.setOnAction(this::handleExit);
    this.open.setOnAction(this::handleOpen);

    this.menuFile.getItems().addAll(this.open, new SeparatorMenuItem(), this.exit);

    this.getMenus().addAll(this.menuFile);
    
  }

  private void handleExit (ActionEvent aEvent) {
    // TODO: ????
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
