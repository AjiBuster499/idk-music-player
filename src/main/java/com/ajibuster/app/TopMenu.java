package com.ajibuster.app;

import javafx.event.ActionEvent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

public class TopMenu extends MenuBar {

  private Menu menuFile;
  private MenuItem open, exit;

  public TopMenu () {
    this.menuFile = new Menu("File");
    
    this.open = new MenuItem("Open File...");
    this.exit = new MenuItem("Exit");

    this.exit.setOnAction(this::handleExit);
    this.open.setOnAction(this::handleOpen);

    this.menuFile.getItems().addAll(this.open, new SeparatorMenuItem(), this.exit);

    this.getMenus().addAll(this.menuFile);
    
  }

  private void handleExit (ActionEvent aEvent) {

  }

  private void handleOpen (ActionEvent aEvent) {

  }
  
}
