package com.ajibuster.app.view;

import com.ajibuster.app.eventbus.EventBus;
import com.ajibuster.app.eventbus.EventListener;
import com.ajibuster.app.eventbus.events.*;

import javafx.application.Platform;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;

public class SeekBar extends Slider {
  private EventBus eventBus;


  public SeekBar (EventBus eventBus) {
    this.eventBus = eventBus;
    setMinWidth(500);

    eventBus.listen(CurrentTimeEvent.class, new CurrentTimeEventListener());

    setOnMousePressed(this::seekTime);
    setOnMouseDragged(this::seekTime);

    // Note to Self: Default Max is 100
    // This is Necessary LOL
    setMax(1);
  }
  
  private class CurrentTimeEventListener implements EventListener<CurrentTimeEvent> {

    @Override
    public void handle(CurrentTimeEvent event) {
      // This is on the timeThread from MediaHandler
      Platform.runLater(new Runnable () {
        // On FX Application Thread
        @Override
        public void run() {
          setValue(event.getTimePercentage());
        }
      });
    }
    
  }

  private void seekTime (MouseEvent event) {
    this.eventBus.emit(new SeekTimeEvent(getValue()));
  }
}
