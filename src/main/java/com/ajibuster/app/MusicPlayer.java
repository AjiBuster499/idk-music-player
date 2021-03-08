package com.ajibuster.app;

import com.ajibuster.app.view.*;
import com.ajibuster.app.eventbus.EventBus;
import com.ajibuster.app.eventbus.EventListener;
import com.ajibuster.app.eventbus.events.OpenMediaEvent;
import com.ajibuster.app.model.*;

import javafx.scene.layout.BorderPane;

public class MusicPlayer extends BorderPane {

  private TopMenu topMenu;
  private BottomPane bottomPane;
  private LeftPane leftPane;
  private CenterPane centerPane;

  private MediaHandler mediaHandler;

  public MusicPlayer(MediaHandler mediaHandler, EventBus eventBus) {
    this.topMenu = new TopMenu(eventBus);
    this.bottomPane = new BottomPane(eventBus);
    this.leftPane = new LeftPane();
    this.centerPane = new CenterPane();
    this.mediaHandler = mediaHandler;

    this.leftPane.setStyle("-fx-border-style: hidden solid hidden hidden; -fx-border-color: black");
    // CLEANUP: Create a .css and put style in there.

    this.setCenter(this.centerPane);
    this.setLeft(this.leftPane);
    this.setTop(this.topMenu);
    this.setBottom(this.bottomPane);

    eventBus.listen(OpenMediaEvent.class, new OpenMediaEventListener());
  }

  private class OpenMediaEventListener implements EventListener<OpenMediaEvent> {

    @Override
    public void handle(OpenMediaEvent event) {
      // Generate new Media
      mediaHandler.createNewPlayer(topMenu.getItemList());
      // mediaHandler.getPlayer().setOnReady(() -> {
      //   centerPane.addImageView(mediaHandler.getAlbumArt());
      // });
    }
    
  }
}
