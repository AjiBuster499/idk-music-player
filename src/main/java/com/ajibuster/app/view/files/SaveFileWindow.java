// package com.ajibuster.app.view.files;

// public class SaveFileWindow {

//   public void saveToFile (Playlist playlist) {
//     File m3uFile = this.fc.showSaveDialog(window);
    
//     try {
//       FileWriter writer = new FileWriter (m3uFile);
//       writer.write(extM3UTemplate);
//       // grab all the metadata from the medias
//       // and put it into the mediaItems
//       for (int i = 0; i < playlist.getItemList().size(); i++) {
//         playlist.getItemList().get(i).setSeconds((int) playlist.getMediaList().get(i).getDuration().toSeconds());
//         if (playlist.getItemList().get(i).getArtist() == null) {
//           playlist.getItemList().get(i).setArtist(playlist.getMediaList().get(i).getMetadata().get("artist").toString());
//         }
//         if (playlist.getItemList().get(i).getTitle() == null) {
//           playlist.getItemList().get(i).setTitle(playlist.getMediaList().get(i).getMetadata().get("title").toString());
//         }
//       }
//       for (MediaItem item : playlist.getItemList()) {
//         writer.write("\n\n");
//         // Trim out the file://
//         String path = item.getPath().replace(uriAddOn, "");
//         // stand-in parts for right now.
//         writer.write(String.format(extInfTemplate, item.getSeconds(), item.getArtist(), item.getTitle()));
//         writer.write("\n");
//         writer.write(path);
//       }
//       writer.write("\n");
//       writer.close();
//     } catch (IOException e) {
//       e.printStackTrace();
//     }
//   }
  
// }
