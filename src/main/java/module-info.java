module com.ajibuster.app {
  requires transitive javafx.controls;
  requires transitive javafx.media;
  requires javafx.graphics;

  exports com.ajibuster.app;
  exports com.ajibuster.app.model;
  exports com.ajibuster.app.view;
  exports com.ajibuster.app.eventbus;
}
