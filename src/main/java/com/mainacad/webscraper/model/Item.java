package com.mainacad.webscraper.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode

public class Item {

  private String name;
  private String itemId;
  private String url;
  private String imgURL;
  private boolean isAvailable;
  private double price;
  private double priceWithoutDiscount;

}
