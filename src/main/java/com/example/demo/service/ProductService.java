package com.example.demo.service;


import com.example.demo.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService {

  @Autowired
  private MongoTemplate mongoTemplate;

  public Product addProduct(Product product) {
    return mongoTemplate.insert(product); // 直接存入 MongoDB
  }

  public void deleteProductByNameAndPrice(Product product){
    Query query = new Query();
    query.addCriteria(
        Criteria.where("name").is(product.getName())
        .and("price").is(product.getPrice()));
    mongoTemplate.remove(query, Product.class);
  }

  public void deleteProductById(String id){
    Query query = new Query();
    query.addCriteria(
        Criteria.where("id").is(id));
    mongoTemplate.remove(query, Product.class);
  }

  public List<Product> selectAll(){
    return mongoTemplate.findAll(Product.class);
  }
  public void updateProduct(Product product){
    Query query = new Query();
    query.addCriteria(Criteria.where("name").is(product.getName()));
    Update update = new Update();
    update.set("price",product.getPrice());
    mongoTemplate.updateFirst(query,update,Product.class);
  }

  public Product selectByName(String name){
    Query query = new Query();
    query.addCriteria(Criteria.where("name").is(name));
    return mongoTemplate.findOne(query,Product.class);
  }
}