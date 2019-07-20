package com.mainacad.webscraper.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mainacad.webscraper.model.Item;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;
import joptsimple.internal.Strings;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebScraperService {


  private static Document itemPage;

  static Item getItem(String url) {
    try {
      itemPage = Jsoup.connect(url).get();
    } catch (IOException e) {
      e.printStackTrace();
    }
    Item item = new Item();
    item.setName(getItemName());
    item.setItemId(getItemId());
    item.setUrl(url);
    item.setImgURL(getImgUrl());
    item.setAvailable(getIsAvailable());
    item.setPrice(getPrice());
    item.setPriceWithoutDiscount(getPriceWithoutDiscount());
    return item;
  }

  private static double getPriceWithoutDiscount() {
    String textPrice = itemPage.getElementsByAttributeValue("data-qaid", "price_without_discount")
        .attr("data-qaprice");
    if (!Strings.isNullOrEmpty(textPrice)) {
      return Double.parseDouble(textPrice);
    }
    return getPrice();
  }

  private static double getPrice() {
    String textPrice = itemPage.getElementsByAttributeValue("data-qaid", "product_price")
        .attr("data-qaprice");
    if (!Strings.isNullOrEmpty(textPrice)) {
      return Double.parseDouble(textPrice);
    }
    return 0;
  }

  private static boolean getIsAvailable() {
    String textResult = itemPage.getElementsByAttributeValue("data-qaid", "product_presence")
        .text();
    return textResult.equals("В наличии") | textResult.equals("Заканчивается");
  }

  private static String getImgUrl() {
    String imgUrl = "";
    String json = itemPage.getElementsByAttributeValue("data-qaid", "image_block")
        .attr("data-bazooka-props");
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      JsonNode jsonNode = objectMapper.readTree(json);
      imgUrl += jsonNode.findValue("url").asText();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return imgUrl;
  }

  private static String getItemId() {
    return itemPage.getElementsByAttributeValue("data-qaid", "product-sku").text();
  }

  private static String getItemName() {
    String itemName = "";
    itemName += itemPage.getElementsByAttributeValue("data-qaid", "product_name").text();
    return itemName;
  }

  public static ConcurrentLinkedQueue<String> getItemUrls(String itemsPageUrl) {
    ConcurrentLinkedQueue<String> urls = new ConcurrentLinkedQueue<>();
    try {
      Document allItemsPage = Jsoup.connect(itemsPageUrl).get();
      Elements elements = allItemsPage.getElementsByAttributeValue("type", "application/ld+json");
      for (Element element :
          elements) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(element.html()).findValue("url");
        if (jsonNode != null) {
          urls.add(jsonNode.asText());
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return urls;
  }

}
