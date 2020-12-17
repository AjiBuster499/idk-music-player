package com.ajibuster.app;

public class SliderThread implements Runnable {
  
  private boolean stopRequested = false;
  Thread sliderThread;

  public SliderThread () {

  }

  public synchronized void requestStop () {
    this.stopRequested = true;
  }

  public synchronized boolean isStopRequested () {
    return this.stopRequested;
  }

  @Override
  public void run() {
    // TODO Auto-generated method stub

  }

  /* updateSlider()
   * Return Value: TBD
   * Runs a task to constantly update
   * Slider (the time of the file) 
   */
  // private Thread updateSlider () {
  //     return new Thread(new Runnable() {
  //     @Override
  //     public void run() {
  //       while (!exitThread) {
  //         double progress = 0;
  //         final double duration = timeBar.getMax();
  //         for(int i = 0; i <= duration; i++){
  //           try {
  //             Thread.sleep(1000);
  //           } catch (InterruptedException e) {
  //             e.printStackTrace();
  //           }
  //           progress++;
  //
  //           // Convert progress to percentage of duration
  //           final double timePlayed = progress;
  //           Platform.runLater(new Runnable() {
  //             @Override
  //             public void run() {
  //               // Update ProgressBar Here
  //               timeBar.setValue(timePlayed);
  //             }
  //           });
  //         }
  //       }
  //     }
  //   });
  // }
  
}
