package com.mainacad.webscraper.service;

import com.mainacad.webscraper.model.Item;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;

class WriteToFileService {

  private static final String SEP = System.getProperty("file.separator");
  private static final File DIR = new File(System.getProperty("user.dir") + SEP + "files" + SEP);

  static void writeToFile(String fileName, Item item) {
    String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
    File file = getFilePath(fileName);
    if (fileExtension.equals("csv")) {
      writeToCSV(file, item);
    }
  }

  private static void writeToCSV(File file, Item item) {
    try (FileWriter fileWriter = new FileWriter(file, Charset.forName("UTF-8"), true)) {
      fileWriter.write("\"" + item.getName() + "\",");
      fileWriter.write("\"" + item.getItemId() + "\",");
      fileWriter.write("\"" + item.getUrl() + "\",");
      fileWriter.write("\"" + item.getImgURL() + "\",");
      fileWriter.write("\"" + item.isAvailable() + "\",");
      fileWriter.write("\"" + item.getPrice() + "\",");
      fileWriter.write("\"" + item.getPriceWithoutDiscount() + "\"\n");
      fileWriter.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static File getFilePath(String fileName) {
    if (!DIR.exists()) {
      DIR.mkdir();
    }
    return new File(DIR + "/" + fileName);
  }
}