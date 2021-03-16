package com.ajibuster.app.model.media;

import java.util.ArrayList;
import java.util.Collections;

import com.ajibuster.app.eventbus.EventBus;
import com.ajibuster.app.eventbus.EventListener;
import com.ajibuster.app.eventbus.events.ShufflePlaylistEvent;

import javafx.scene.media.Media;

/**
 * The Playlist class contains all the Medias to be played, 
 * as well as methods to traverse and manipulate the Medias.
 * @author ajibuster499
 */
public class Playlist {
  /**
   * Used to traverse the playlist.
   */
  private int index = 0;

  /**
   * Used to check for the last song.
   */
  private boolean endOfPlaylist = false;

  /**
   * Used to check if the current playlist has been shuffled.
   */
  private boolean shuffled = false;
  /** 
   * The list of media that is used by the MediaHandler
   * to actually play media.
   */
  private ArrayList<Media> mediaList;
  /**
   * A duplicate of mediaList that has been randomized.
   */
  private ArrayList<Media> shuffledMedia;
  /**
   * A list of MediaItems to be converted to Medias. 
   * Additionally, they need to be given any missing data
   */
  private ArrayList<MediaItem> itemList;
  
  public Playlist (ArrayList<MediaItem> itemList, EventBus eventBus) {
    this.mediaList = createMedia(itemList);
    this.itemList = itemList;
    generateMetadata();

    eventBus.listen(ShufflePlaylistEvent.class, new ShufflePlaylistEventListener());
  }

  /**
   * Creates a playlist given a list of MediaItems.
   * @param itemList the list of MediaItems to generate Medias for
   * @return the list of Medias generated
   */
  private ArrayList<Media> createMedia (ArrayList<MediaItem> itemList) {
    this.mediaList = new ArrayList<>();
    for (MediaItem item : itemList) {
      this.mediaList.add(new Media(item.getPath()));
    }
    return this.mediaList;
  }

  /**
   * Gets the next Media in the playlist by manipulating the index.
   * If the current Media is the last Media, it will get the first item in 
   * the playlist.
   * @return The previous Media in the playlist
   */
  public Media next() {
    // skip to next media
    if (this.index == this.mediaList.size() - 1) { // check if this is working.
      this.endOfPlaylist = true;
      this.index = 0;
    } else {
      this.index++;
    }
    return this.mediaList.get(index);
  }

  /**
   * Gets the previous Media in the playlist by manipulating the index.
   * If the current Media is the first Media, it will get the last item in 
   * the playlist.
   * @return The previous Media in the playlist
   */
  public Media prev() {
    // return to previous media
    if (this.index == 0) {
      this.index = this.mediaList.size() - 1;
    } else {
      this.index--;
    }
    return this.mediaList.get(index);
  }

  /**
   * Shuffle the mediaList. For the purpose 
   * of saving the playlist in it's original order, 
   * we use a different ArrayList, shuffledMedia, 
   * to shuffle and then play.
   */
  private void shuffle() {
    this.shuffledMedia = new ArrayList<>(this.mediaList);
    Collections.shuffle(this.shuffledMedia);
  }

  /**
   * Generates new Medias and adds them to the playlist.
   * If the playlist has been shuffled, then it shuffles up the new additions 
   * before adding them in.
   * @param newItems The list of items to be added.
   * @return An updated mediaList
   * @implNote This method needs to shuffle the entire playlist when new 
   * medias are added, sans what has been already played.
   */
  public ArrayList<Media> queue (ArrayList<MediaItem> newItems) {
    ArrayList<Media> newMedias = createMedia(newItems);
    if (this.shuffled) {
      Collections.shuffle(newMedias);
    }
    this.mediaList.addAll(newMedias);
    return this.mediaList;
  }

  /**
   * Gets the current Media from the playlist.
   * @return the current Media
   */
  public Media getCurrentMedia () {
    return this.mediaList.get(index);
  }

  /**
   * Gets the current mediaList.
   * @return the current mediaList
   */
  public ArrayList<Media> getMediaList () {
    return this.mediaList;
  }

  /**
   * Gets the current itemList.
   * @return the current itemlist
   */
  public ArrayList<MediaItem> getItemList () {
    return this.itemList;
  }

  /**
   * Checks if the current media is the last song in the playlist.
   * @return true if current media is last song, false otherwise.
   */
  public boolean isEndOfPlaylist () {
    return this.endOfPlaylist;
  }

  /**
   * Generates the full set of data for a MediaItem.
   * Since individual Media files do not contain 
   * easily accessible data for saving a .m3u, such as 
   * title, time (in seconds), and artist, it is necessary 
   * to retrieve that data from the metadata of the Media
   * to have a complete .m3u.
   * This operation runs on a separate thread and uses
   * a JavaFX Task to do the work.
   */
  private void generateMetadata () {
    // adds metadata to mediaItem on separate thread
    MetadataGenerator mdGenerator = new MetadataGenerator(this);
    mdGenerator.setOnSucceeded(h -> {
      itemList = mdGenerator.getValue();
    });

    Thread th = new Thread(mdGenerator);
    th.setDaemon(true);
    th.start();
  }

  //#region
  private class ShufflePlaylistEventListener implements EventListener<ShufflePlaylistEvent> {
    private ArrayList<Media> temp;

    @Override
    public void handle(ShufflePlaylistEvent event) {
      switch(event.getStatus()) {
        case SHUFFLE_OFF: {
          // the theory is to revert to an unshuffled state
          mediaList = temp;
          shuffled = false;
          break;
        }
        case SHUFFLE_ON: {
          temp = new ArrayList<>(mediaList);
          shuffle();
          shuffled = true;
          mediaList = shuffledMedia;
          break;
        }
        case SHUFFLE_DEFAULT: {
          // Playlist will never be here
          break;
        }
      }
      // need to play the now shuffled list
    }
  }
  //#endregion
}
