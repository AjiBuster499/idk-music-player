package com.ajibuster.app;

import javafx.scene.control.Slider;

public class SliderRunnable implements Runnable {
  // SliderRunnable implements Runnable
  // This creates a Runnable that can be passed
  // to a Thread to deal with all Slider actions
  // that need multithreading.
  // Dev Note: I'm super unsure of what I'm doing.

  private boolean stopRequested = false;
  private Slider slider;
  private double duration;

  public SliderRunnable (Slider s, double d) {
    // We need a method to handle the slider tracking play time.
    // To that end, we need to know the Slider and the Duration
    this.slider = s;
    this.duration = d;
  }

  public synchronized void requestStop () {
    this.stopRequested = true;
  }

  public synchronized boolean isStopRequested () {
    return this.stopRequested;
  }

  @Override
  public void run () {
    while (!stopRequested) {
      // In here do your business logic
      trackProgress();
      sleep(1000);
    }

  }

  private void trackProgress () {
    // In here we update the slider with the song
    // play time.
    double progress = 0;
    while (progress <= duration) {
      slider.setValue(progress);
      progress++;
    }
  }
  
  private void sleep (long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }  
}
