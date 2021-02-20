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
  
  private Label volume, time;
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

    this.volume = new Label("Vol: 100%");
    this.time = new Label("00:00");

    play.setOnAction(e -> handle(new PlayEvent()));
    pause.setOnAction(e -> handle(new PauseEvent()));
    stop.setOnAction(e -> handle(new StopEvent()));
    forward.setOnAction(e -> handle(new ForwardEvent()));
    rewind.setOnAction(e -> handle(new RewindEvent()));

    // Hopefully this doesn't grow to the nightmare in MediaHandler
    // NotLikeAji

    eventBus.listen(VolumeChangedEvent.class, new VolumeChangedEventListener());
    // TODO: Implement Time Tracking.
    // Due to the use of threads, it is difficult to transfer data across threads.
    // tl;dr Apparently the handler is on another thread and can't interact with the label?

    timeControls.getChildren().addAll(seekBar, time);
    volumeControls.getChildren().addAll(volSlider, volume);
    bottomButtons.getChildren().addAll(play, pause, stop, forward, rewind);
    this.getChildren().addAll(bottomButtons, timeControls, volumeControls);

  }

  private class VolumeChangedEventListener implements EventListener<VolumeChangedEvent> {
    @Override
    public void handle(VolumeChangedEvent event) {
      String volumeFormat = "Vol: %.0f%%";
      volume.setText(String.format(volumeFormat, event.getVol() * 100));
    }
  }

  private void handle (Event event) {
    this.eventBus.emit(event);
  }
}
