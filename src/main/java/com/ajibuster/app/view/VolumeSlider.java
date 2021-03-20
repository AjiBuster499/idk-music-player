package com.ajibuster.app.view;

import com.ajibuster.app.eventbus.EventBus;
import com.ajibuster.app.eventbus.events.VolumeChangedEvent;

import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;

public class VolumeSlider extends Slider {
  private EventBus eventBus;

  public VolumeSlider (EventBus eventBus) {
    this.eventBus = eventBus;
    setId("volumeSlider");
  
    setOnMouseClicked(this::changeVolume);
    setOnMouseDragged(this::changeVolume);

    setWidth(100);
    setMax(1);
    setValue(1);
  }

  private void changeVolume(MouseEvent mEvent) {
    this.eventBus.emit(new VolumeChangedEvent(getValue()));
  }
  
}
