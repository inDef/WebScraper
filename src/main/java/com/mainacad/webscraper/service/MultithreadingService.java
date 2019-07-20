package com.mainacad.webscraper.service;

import com.mainacad.webscraper.model.Item;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MultithreadingService extends Thread {

  private String url;
  private String fileName;

  @Override
  public void run() {
    Item item = WebScraperService.getItem(url);
    WriteToFileService.writeToFile(fileName, item);
  }

}


