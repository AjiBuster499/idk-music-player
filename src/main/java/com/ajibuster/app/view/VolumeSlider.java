package com.ajibuster.app.view;

import com.ajibuster.app.eventbus.EventBus;
import com.ajibuster.app.eventbus.events.VolumeChangedEvent;

import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;

public class VolumeSlider extends Slider {
  private EventBus eventBus;

  public VolumeSlider (EventBus eventBus) {
    this.eventBus = eventBus;
    setWidth(100);
  
    this.setOnMouseClicked(this::changeVolume);
    this.setOnMouseDragged(this::changeVolume);
  }

  private void changeVolume(MouseEvent mEvent) {
    this.eventBus.emit(new VolumeChangedEvent(getValue()));
  }

  
}
