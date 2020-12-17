package com.ajibuster.app;

import javafx.scene.control.Slider;
import javafx.scene.media.Media;

public class SliderRunnable implements Runnable {
  // SliderRunnable implements Runnable
  // This creates a Runnable that can be passed
  // to a Thread to deal with all Slider actions
  // that need multithreading.
  // Dev Note: I'm super unsure of what I'm doing.

  private boolean stopRequested = false;
  private Slider slider;
  private Media media;

  public SliderRunnable (Slider s, Media m) {
    // We need a method to handle the slider tracking play time.
    // To that end, we need to know the Slider and the Media.
    // Insert bad joke about S and M.
    this.slider = s;
    this.media = m;
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
      System.out.println("Hi!");
      sleep(1000);
    }

  }

  private void trackProgress () {
    // In here we update the slider with the song
    // play time.
  }
  
  private void sleep (long millis) {
    try {
      System.out.println("Sleeping");
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }  
}
