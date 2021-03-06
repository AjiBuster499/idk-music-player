package com.ajibuster.app.model.media;

import java.util.ArrayList;

import javafx.concurrent.Task;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MetadataGenerator extends Task<ArrayList<MediaItem>> {
  private ArrayList<MediaItem> itemList;
  private ArrayList<Media> mediaList;

  private Object obj = new Object();

  private int i;

  public MetadataGenerator (Playlist playlist) {
    this.itemList = playlist.getItemList();
    this.mediaList = playlist.getListToSave();
  }

  @Override
  protected ArrayList<MediaItem> call() throws Exception {
    try {
      for (i = 0; i < this.mediaList.size(); ++i) {
        if (isCancelled()) {
          break;
        }
        generateData(this.mediaList.get(i), i);
        synchronized (obj) {
          obj.wait(100);
        }
      }
    } catch (InterruptedException ie) {
      ie.printStackTrace();
      updateMessage("Interrupted. Cancelling...");
      cancel();
    }
    return this.itemList;
  }

  private void generateData(Media media, int i) {
    final MediaPlayer player = new MediaPlayer(media);
    player.setOnReady( () -> {
      if (this.itemList.get(i).getDuration() == 0) {
        // get the duration of the media
        // set it to itemList's duration
        final int duration = (int) media.getDuration().toSeconds();
        this.itemList.get(i).setDuration(duration);
      }
      if (this.itemList.get(i).getArtist() == null) {
        // get the artist from the metadata
        // set it to the itemList's artist
        String artist = media.getMetadata().get("artist").toString();
        artist = artist.replace("\0", "");
        this.itemList.get(i).setArtist(artist);
      }
      if (this.itemList.get(i).getTitle() == null) {
        // get the title from the metadata
        // set it to the itemList's title
        String title = media.getMetadata().get("title").toString();
        title = title.replace("\0", "");
        this.itemList.get(i).setTitle(title);
      }
      synchronized (obj) {
        obj.notify();
      }
    });
  }
  
}
