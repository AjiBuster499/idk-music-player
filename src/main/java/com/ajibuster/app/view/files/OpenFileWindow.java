// package com.ajibuster.app.view.files;

// public class OpenFileWindow {

//   private void openMedia() {
//     this.itemList = new ArrayList<MediaItem>();
//     if (this.fileList.get(0).getAbsolutePath().endsWith(".m3u")) {
//       // playlist file
//       this.itemList = parseM3U(this.fileList.get(0));
//     } else {
//       // individual media files
//       for (File file : fileList) {
//         MediaItem item = new MediaItem();
//         String filePath = this.uriAddOn + file.getAbsolutePath().replace("\\", "/").replace("\s", "%20");
//         item.setPath(filePath);
//         itemList.add(item);
//       }
//     }
//   }

//   private ArrayList<MediaItem> parseM3U(File m3u) {
//     ArrayList<MediaItem> itemList = new ArrayList<MediaItem>();
//     try {
//       BufferedReader reader = new BufferedReader(new FileReader(m3u));

//       // Start reading the file
//       String line;
//       MediaItem item = new MediaItem();
//       while ((line = reader.readLine()) != null) {
//         if (line.startsWith(extM3UTemplate) || line.isBlank()) {
//           continue;
//         } else {
//           // skip over "#EXTINF", "%d,", " - "
//           // remainder is, in order, Artist and Title
//           int artistIndex = line.indexOf(", "), titleIndex = line.indexOf(" - ");
//           item.setArtist(line.substring(artistIndex + 2, titleIndex));
//           item.setTitle(line.substring(titleIndex + 3, line.length()));

//           // read next line. will be the filePath.
//           line = reader.readLine();
//           item.setPath(this.uriAddOn + line.replace("\\", "/").replace("\s", "%20"));
//           itemList.add(item);
//           item = new MediaItem();
//         }
//       }
//       reader.close();
//     } catch (IOException e) {
//       e.printStackTrace();
//     }

//     return itemList;
//   }

// }
