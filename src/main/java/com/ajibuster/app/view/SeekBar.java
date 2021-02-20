package com.ajibuster.app.view;

import com.ajibuster.app.eventbus.EventBus;
import com.ajibuster.app.eventbus.EventListener;
import com.ajibuster.app.eventbus.events.*;

import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;

public class SeekBar extends Slider {
  private EventBus eventBus;


  public SeekBar (EventBus eventBus) {
    this.eventBus = eventBus;
    setMinWidth(500);

    eventBus.listen(CurrentTimeEvent.class, new CurrentTimeEventListener());

    setOnMouseClicked(this::seekTime);
    setOnMouseDragReleased(this::seekTime);

    // Note to Self: Default Max is 100
    // This is Necessary LOL
    setMax(1);
  }
  
  private class CurrentTimeEventListener implements EventListener<CurrentTimeEvent> {

    @Override
    public void handle(CurrentTimeEvent event) {
      setValue(event.getTime());
    }
    
  }

  private void seekTime (MouseEvent event) {
    this.eventBus.emit(new SeekTimeEvent(getValue()));
  }
}
