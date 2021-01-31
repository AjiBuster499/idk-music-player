package com.ajibuster.app.view;

import com.ajibuster.app.eventbus.EventBus;
import com.ajibuster.app.eventbus.EventListener;
import com.ajibuster.app.eventbus.events.CurrentTimeEvent;
import com.ajibuster.app.eventbus.events.SeekTimeEvent;

import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;

public class SeekBar extends Slider {
  private EventBus eventBus;


  public SeekBar (EventBus eventBus) {
    this.eventBus = eventBus;

    eventBus.listen(CurrentTimeEvent.class, new CurrentTimeEventListener());

    this.setOnMouseClicked(this::seekTime);
    this.setOnMouseDragReleased(this::seekTime);

    this.setMax(1);
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
