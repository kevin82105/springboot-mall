package com.example.demo.dao;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(collection = "products") // 指定 collection 名稱
public class Product {

  @Id
  private String id;
  @Indexed(unique = true)
  private String name;
  private BigDecimal price;
  private String category;
  private String imageUrl; // 新增圖片欄位
  // 建構子
  public Product() {}
  public Product(String name, BigDecimal price, String category,String imageUrl) {
    this.name = name;
    this.price = price;
    this.category = category;
    this.imageUrl = imageUrl;
  }

  // getter & setter
  public String getId() { return id; }
  public void setId(String id) { this.id = id; }
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public BigDecimal getPrice() { return price; }
  public void setPrice(BigDecimal price) { this.price = price; }
  public String getCategory() { return category; }
  public void setCategory(String category) { this.category = category; }
  public String getImageUrl() {
    return imageUrl;
  }
  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }
}