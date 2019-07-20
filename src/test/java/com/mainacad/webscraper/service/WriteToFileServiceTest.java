package com.mainacad.webscraper.service;

import com.mainacad.webscraper.model.Item;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class WriteToFileServiceTest {

  private static Logger logger = Logger.getLogger(WriteToFileServiceTest.class.getName());

  @Test
  void writeToFile() {
    String fileName = "writeToFileTest.csv";
    Item item = new Item(
        "Ноутбук Acer Nitro 5 AN515-42-R8VD (NH.Q3REX.005)",
        "",
        "https://prom.ua/p970447264-noutbuk-acer-nitro.html",
        "https://images.ua.prom.st/1788285827_w640_h640_noutbuk-acer-nitro.jpg",
        true,
        18375,
        18375);
    int linesBefore = 0;
    int linesAfter = 0;
    File file = WriteToFileService.getFilePath(fileName);

    if (file.exists()) {
      try {
        linesBefore = Files.readAllLines(file.toPath()).size();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    WriteToFileService.writeToFile(fileName, item);

    try {
      linesAfter = Files.readAllLines(file.toPath()).size();
    } catch (
        IOException e) {
      e.printStackTrace();
    }
    logger.info(file.toString());
    Assertions.assertEquals(1, (linesAfter - linesBefore));
  }
}
