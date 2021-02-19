package com.ajibuster.app.view;

import com.ajibuster.app.eventbus.Event;
import com.ajibuster.app.eventbus.EventBus;
import com.ajibuster.app.eventbus.EventListener;
import com.ajibuster.app.eventbus.events.*;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BottomPane extends VBox {
  
  private Label volume;
  private EventBus eventBus;

  public BottomPane (EventBus eventBus) {
    HBox bottomButtons = new HBox();
    HBox volumeControls = new HBox();
    HBox timeControls = new HBox();
    this.eventBus = eventBus;

    Button play = new Button("Play");
    Button pause = new Button("Pause");
    Button stop = new Button("Stop");
    Button forward = new Button("Forward");
    Button rewind = new Button("Rewind");

    VolumeSlider volSlider = new VolumeSlider(eventBus);
    SeekBar seekBar = new SeekBar(this.eventBus);

    this.volume = new Label("Vol: 0%");
    Label time = new Label("TI:ME");

    play.setOnAction(e -> handle(new PlayEvent()));
    pause.setOnAction(e -> handle(new PauseEvent()));
    stop.setOnAction(e -> handle(new StopEvent()));
    forward.setOnAction(e -> handle(new ForwardEvent()));
    rewind.setOnAction(e -> handle(new RewindEvent()));

    // Hopefully this doesn't grow to the nightmare in MediaHandler
    // NotLikeAji

    eventBus.listen(VolumeChangedEvent.class, new VolumeChangedEventListener());

    timeControls.getChildren().addAll(time, seekBar);
    volumeControls.getChildren().addAll(volume, volSlider);
    bottomButtons.getChildren().addAll(play, pause, stop, forward, rewind);
    this.getChildren().addAll(bottomButtons, timeControls, volumeControls);

  }

  private class VolumeChangedEventListener implements EventListener<VolumeChangedEvent> {
    @Override
    public void handle(VolumeChangedEvent event) {
      String volumeFormat = "Vol: %.0f%%";
      volume.setText(String.format(volumeFormat, event.getVol()));
    }
  }

  private void handle (Event event) {
    this.eventBus.emit(event);
  }
}
