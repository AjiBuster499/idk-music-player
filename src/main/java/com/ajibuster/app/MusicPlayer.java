package com.ajibuster.app;

import com.ajibuster.app.view.*;
import com.ajibuster.app.eventbus.EventBus;
import com.ajibuster.app.eventbus.EventListener;
import com.ajibuster.app.eventbus.events.OpenEvent;
import com.ajibuster.app.model.*;

import javafx.scene.layout.BorderPane;

public class MusicPlayer extends BorderPane {

  private TopMenu topMenu;
  private BottomPane bottomPane;
  private LeftPane leftPane;

  private MediaHandler mediaHandler;

  public MusicPlayer(MediaHandler mediaHandler, EventBus eventBus) {
    this.topMenu = new TopMenu(eventBus);
    this.bottomPane = new BottomPane(eventBus);
    this.leftPane = new LeftPane();
    this.mediaHandler = mediaHandler;

    this.setTop(this.topMenu);
    this.setLeft(this.leftPane);
    this.setBottom(this.bottomPane);

    eventBus.listen(OpenEvent.class, new OpenEventListener());
  }

  private class OpenEventListener implements EventListener<OpenEvent> {

    @Override
    public void handle(OpenEvent event) {
      // Generate new Media
      mediaHandler.createNewPlayer(topMenu.getMusicPath());
    }
    
  }
  
}
