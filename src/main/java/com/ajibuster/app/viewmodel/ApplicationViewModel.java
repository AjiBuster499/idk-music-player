package com.ajibuster.app.viewmodel;

import java.util.LinkedList;
import java.util.List;

public class ApplicationViewModel {

  private List<ClickListener> clickListeners;

  public ApplicationViewModel () {
    clickListeners = new LinkedList<>();
  }

  // Listen for Clicks

  // When Clicks happen, notify all listeners.
  
}
