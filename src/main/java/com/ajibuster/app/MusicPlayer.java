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
  private EventBus eventBus;

  private MediaHandler mh;

  public MusicPlayer() {
    this.eventBus = new EventBus();
    this.topMenu = new TopMenu(this.eventBus);
    this.bottomPane = new BottomPane(eventBus);
    this.leftPane = new LeftPane();

    this.setTop(this.topMenu);
    this.setLeft(this.leftPane);
    this.setBottom(this.bottomPane);

    this.eventBus.listen(OpenEvent.class, new OpenEventListener());
  }

  public MediaHandler getMediaHandler() {
    return this.mh;
  }

  public void setMediaHandler(MediaHandler mh) {
    this.mh = mh;
  }

  private class OpenEventListener implements EventListener<OpenEvent> {

    @Override
    public void handle(OpenEvent event) {
      // Generate new Media
      // if there is a player, dispose of it.
      // If not, then create a new mediaHandler
      if (mh.getPlayer() != null) {
        mh.getPlayer().dispose();
      }
      mh = new MediaHandler(topMenu.getMusicPath(), eventBus);

    }
    
  }
  
}
