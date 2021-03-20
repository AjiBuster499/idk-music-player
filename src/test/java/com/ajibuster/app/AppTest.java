package com.ajibuster.app;

import org.junit.After;
import org.junit.Before;
import org.testfx.framework.junit.ApplicationTest;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class AppTest extends ApplicationTest {

  @Override
  public void start (Stage stage) throws Exception {
    Parent root = new Pane();
    stage.setScene(new Scene(root));
    stage.show();
    stage.toFront();
  }

  @Before
  public void setUp () {

  }

  @After
  public void tearDown ()  {
    
  }
  
}
