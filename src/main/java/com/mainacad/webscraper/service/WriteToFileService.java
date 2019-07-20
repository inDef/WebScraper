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
      if (!file.exists()) {
        String header = "\"Name\";"
            + "\"Item ID\";"
            + "\"Item page URL\";"
            + "\"Item IMG URL\";"
            + "\"Item availability\";"
            + "\"Item price\";"
            + "\"Item price w/o discount\"\n";
        try (FileWriter fileWriter = new FileWriter(file, false)) {
          fileWriter.write(header);
          fileWriter.flush();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      writeToCSV(file, item);
    }
  }

  private static void writeToCSV(File file, Item item) {
    try (FileWriter fileWriter = new FileWriter(file, Charset.forName("UTF-8"), true)) {
      fileWriter.write("\"" + item.getName() + "\"\t");
      fileWriter.write("\"" + item.getItemId() + "\"\t");
      fileWriter.write("\"" + item.getUrl() + "\"\t");
      fileWriter.write("\"" + item.getImgURL() + "\"\t");
      fileWriter.write("\"" + item.isAvailable() + "\"\t");
      fileWriter.write("\"" + item.getPrice() + "\"\t");
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
