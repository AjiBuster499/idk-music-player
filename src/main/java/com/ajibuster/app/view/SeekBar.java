package com.ajibuster.app.view;

import com.ajibuster.app.eventbus.EventBus;
import com.ajibuster.app.eventbus.EventListener;
import com.ajibuster.app.eventbus.events.CurrentTimeEvent;

import javafx.scene.control.Slider;

public class SeekBar extends Slider {

  public SeekBar (EventBus eventBus) {
    eventBus.listen(CurrentTimeEvent.class, new CurrentTimeEventListener());
  }

  private class CurrentTimeEventListener implements EventListener<CurrentTimeEvent> {

    @Override
    public void handle(CurrentTimeEvent event) {
      
    }
    
  }
}
