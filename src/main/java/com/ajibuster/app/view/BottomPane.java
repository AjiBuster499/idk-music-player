package com.ajibuster.app.view;

import com.ajibuster.app.eventbus.Event;
import com.ajibuster.app.eventbus.EventBus;
import com.ajibuster.app.eventbus.EventListener;
import com.ajibuster.app.eventbus.events.*;
import com.ajibuster.app.model.media.RepeatStatus;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BottomPane extends VBox {
  
  private Label volume, time;
  private Button repeat;

  private EventBus eventBus;
  private RepeatStatus repeatState = RepeatStatus.REPEAT_OFF;

  public BottomPane (EventBus eventBus) {
    HBox bottomButtons = new HBox();
    HBox volumeControls = new HBox();
    HBox timeControls = new HBox();
    
    this.eventBus = eventBus;

    // Take this whole thing and make it it's own class
    Button play = new Button("Play");
    Button pause = new Button("Pause");
    Button stop = new Button("Stop");
    Button forward = new Button("Forward");
    Button rewind = new Button("Rewind");
    this.repeat = new Button("Repeat: Off");

    VolumeSlider volSlider = new VolumeSlider(eventBus);
    SeekBar seekBar = new SeekBar(this.eventBus);

    this.volume = new Label("Vol: 100%");
    this.time = new Label("00:00");

    play.setOnAction(e -> handle(new PlayMediaEvent()));
    pause.setOnAction(e -> handle(new PauseMediaEvent()));
    stop.setOnAction(e -> handle(new StopMediaEvent()));
    forward.setOnAction(e -> handle(new ForwardEvent()));
    rewind.setOnAction(e -> handle(new RewindEvent()));
    this.repeat.setOnAction(e -> handleRepeat());

    // Hopefully this doesn't grow to the nightmare in MediaHandler
    // NotLikeAji
    eventBus.listen(VolumeChangedEvent.class, new VolumeChangedEventListener());
    eventBus.listen(CurrentTimeEvent.class, new CurrentTimeEventListener());

    timeControls.getChildren().addAll(seekBar, time);
    volumeControls.getChildren().addAll(volSlider, volume);
    bottomButtons.getChildren().addAll(play, pause, stop, rewind, forward, repeat);
    this.getChildren().addAll(bottomButtons, timeControls, volumeControls);

  }

  private class VolumeChangedEventListener implements EventListener<VolumeChangedEvent> {
    @Override
    public void handle(VolumeChangedEvent event) {
      String volumeFormat = "Vol: %.0f%%";
      volume.setText(String.format(volumeFormat, event.getVol() * 100));
    }
  }

  private class CurrentTimeEventListener implements EventListener<CurrentTimeEvent> {
    @Override
    public void handle(CurrentTimeEvent event) {
      // This is on timeThread from MediaHandler
      String timeFormat = "%02d:%02d";
      Platform.runLater(new Runnable() {
        // This is on the FX Application Thread
        @Override
        public void run() {
          time.setText(String.format(timeFormat, (int) event.getTimeDuration().toMinutes(), (int) event.getTimeDuration().toSeconds() % 60));
        }
      });
    }
  }

  private void handle (Event event) {
    this.eventBus.emit(event);
  }

  private void handleRepeat () {
    switch (repeatState) {
      case REPEAT_OFF: {
        handle(new RepeatStatusChangeEvent(RepeatStatus.REPEAT_OFF));
        this.repeat.setText("Repeat: Off");
        repeatState = RepeatStatus.REPEAT_ON;
        break;
      }
      case REPEAT_ON: {
        handle(new RepeatStatusChangeEvent(RepeatStatus.REPEAT_ON));
        this.repeat.setText("Repeat: On");
        repeatState = RepeatStatus.REPEAT_ONE;
        break;
      }
      case REPEAT_ONE: {
        handle(new RepeatStatusChangeEvent(RepeatStatus.REPEAT_ONE));
        this.repeat.setText("Repeat: One");
        repeatState = RepeatStatus.REPEAT_OFF;
        break;
      }
    }
  }
}
