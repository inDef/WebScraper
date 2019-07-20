package com.mainacad.webscraper.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mainacad.webscraper.model.Item;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.junit.jupiter.api.Test;

class WebScraperServiceTest {

  @Test
  void getItem() {
    Item expectedItem = new Item(
        "Ноутбук Acer Nitro 5 AN515-42-R8VD (NH.Q3REX.005)",
        "",
        "https://prom.ua/p970447264-noutbuk-acer-nitro.html",
        "https://images.ua.prom.st/1788285827_w640_h640_noutbuk-acer-nitro.jpg",
        true,
        18375,
        18375);
    Item actualItem = WebScraperService
        .getItem("https://prom.ua/p970447264-noutbuk-acer-nitro.html");
    assertEquals(expectedItem, actualItem);
  }

  @Test
  void getItemUrls() {
    ConcurrentLinkedQueue<String> urls = WebScraperService.getItemUrls("https://prom.ua/Mangaly");
    for (String url :
        urls) {
      assertTrue(url.matches("https://prom.ua/.*\\.html"));
    }
  }
}