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
   * A copy of the mediaList used only for saving the media.
   */
  private ArrayList<Media> listToSave;

  /**
   * A duplicate of mediaList that has been randomized.
   */
  private ArrayList<Media> shuffledMedia;

  /**
   * A list of MediaItems to be converted to Medias. 
   * Additionally, they need to be given any missing data.
   */
  private ArrayList<MediaItem> itemList;
  
  public Playlist (ArrayList<MediaItem> itemList, EventBus eventBus) {
    this.mediaList = new ArrayList<>(createMedia(itemList));
    this.listToSave = new ArrayList<>(this.mediaList);
    this.itemList = itemList;
    generateMetadata();
    for (Media media : mediaList) {
      System.out.println("Unshuffled: " + media.getSource());
    }

    eventBus.listen(ShufflePlaylistEvent.class, new ShufflePlaylistEventListener());
  }

  /**
   * Creates a playlist given a list of MediaItems.
   * @param itemList The list of MediaItems to generate Medias for.
   * @return The list of Medias generated.
   */
  private ArrayList<Media> createMedia (ArrayList<MediaItem> itemList) {
    ArrayList<Media> newMedias = new ArrayList<Media>();
    for (MediaItem item : itemList) {
      newMedias.add(new Media(item.getPath()));
    }
    return newMedias;
  }

  /**
   * Shuffle the mediaList. For the purpose 
   * of saving the playlist in its original order, 
   * we use a different ArrayList, shuffledMedia, 
   * to shuffle and then play.
   * @param unshuffledList the list of unshuffled Medias
   */
  private void shuffle(ArrayList<Media> unshuffledList) {
    // excludes the current media
    this.shuffledMedia = new ArrayList<>(unshuffledList);
    Collections.shuffle(this.shuffledMedia.subList(this.index + 1, shuffledMedia.size()));
  }

  /**
   * Generates new Medias and adds them to the playlist.
   * If the playlist has been shuffled, then it shuffles up the new additions 
   * before adding them in.
   * It also updates the listToSave.
   * @param newItems The list of items to be added.
   * @return An updated mediaList.
   * @implNote This method needs to shuffle the entire playlist when new 
   * medias are added, sans what has been already played.
   */
  public ArrayList<Media> queue (ArrayList<MediaItem> newItems) {
    ArrayList<Media> newMedias = createMedia(newItems);
    this.listToSave.addAll(newMedias);
    this.mediaList.addAll(newMedias);
    if (this.shuffled) {
      shuffle(this.mediaList);
    }
    return this.mediaList;
  }

  /**
   * Gets the next Media in the playlist by manipulating the index.
   * If the current Media is the last Media, it will get the first item in 
   * the playlist.
   * @return The next Media in the playlist.
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
   * @return The previous Media in the playlist.
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
   * Gets the current Media from the playlist.
   * @return The current Media.
   */
  public Media getCurrentMedia () {
    return this.mediaList.get(index);
  }

  /**
   * Gets the current listToSave.
   * @return The current listToSave.
   */
  public ArrayList<Media> getListToSave () {
    return this.listToSave;
  }

  /**
   * Gets the current itemList.
   * @return The current itemlist.
   */
  public ArrayList<MediaItem> getItemList () {
    return this.itemList;
  }

  /**
   * Checks if the current media is the last song in the playlist.
   * @return True if current media is last song, false otherwise.
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

    @Override
    public void handle(ShufflePlaylistEvent event) {
      switch(event.getStatus()) {
        case SHUFFLE_OFF: {
          // TODO: Need to update index to the current media
          // for example, if in an unshuffled list, a media has index 4,
          // and when shuffled it becomes index 1, then when the playlist is
          // unshuffled again, the index needs to be updated to 4.
          // This prevents duplicate playing of the media.
          // While I do dig a replay of Real Folk Blues,
          // it shouldn't be acting like that.
          mediaList = new ArrayList<>(listToSave);
          System.out.println("Before setting index: " + index);
          index = listToSave.indexOf(getCurrentMedia());
          System.out.println("After setting index: " + index);
          shuffled = false;
          break;
        }
        case SHUFFLE_ON: {
          shuffled = true;
          shuffle(mediaList);
          mediaList = new ArrayList<>(shuffledMedia);
          for (Media media : mediaList) {
            System.out.println("Shuffled: " + media.getSource());
          }
          break;
        }
        case SHUFFLE_DEFAULT: {
          // Playlist will never be here
          break;
        }
      }
    }
  }
  //#endregion
}
