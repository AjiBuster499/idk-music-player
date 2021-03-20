package com.ajibuster.app.view;

import com.ajibuster.app.eventbus.EventBus;
import com.ajibuster.app.eventbus.EventListener;
import com.ajibuster.app.eventbus.events.LoadArtEvent;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

// Sometimes I wonder about some of my comments.
public class CenterPane extends StackPane {
  // Center Pane holds album art.

  public CenterPane (EventBus eventBus) {
    // Blank Pane for first opening
    // Maybe create a default image to display
    setId("centerPane");

    eventBus.listen(LoadArtEvent.class, new LoadArtEventListener());
  }

  private void addImageView (ImageView iv) {
    // Avoid painting over other album covers.
    iv.setFitHeight(this.getScene().getHeight() * 0.8);
    iv.setFitWidth(this.getScene().getWidth() * 0.8);
    // Smoothe
    iv.setSmooth(true);
    this.getChildren().add(iv);
  }
  
  // EventListeners
  //#region
  private class LoadArtEventListener implements EventListener<LoadArtEvent> {

    @Override
    public void handle(LoadArtEvent event) {
      getChildren().clear();
      addImageView(event.getImageView());
    }

  }
}
