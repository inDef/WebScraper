package com.mainacad.webscraper;

import java.util.concurrent.ConcurrentLinkedQueue;
import com.mainacad.webscraper.service.MultithreadingService;
import com.mainacad.webscraper.service.WebScraperService;


public class AppRunner {

  public static void main(String[] args) {

    ConcurrentLinkedQueue<String> urls;

    String categoryUrl = "https://prom.ua/Mangaly";
    String fileFormat = "csv";
    String fileName = categoryUrl.substring(categoryUrl.lastIndexOf("/") + 1) + "." + fileFormat;

    urls = WebScraperService.getItemUrls(categoryUrl);
    for (String url : urls) {
      MultithreadingService multithreadingService = new MultithreadingService(url, fileName);
      multithreadingService.start();
    }
  }
}
