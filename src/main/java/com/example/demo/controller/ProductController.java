package com.example.demo.controller;


import com.example.demo.domain.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/product")
@Controller
public class ProductController {

  @Autowired
  private ProductService productService;

  @PostMapping("/add")
  public Product createProduct(@RequestBody Product product) {
    return productService.addProduct(product);
  }

  @DeleteMapping("/delete")
  public String deleteProduct(@RequestBody Product product){
    productService.deleteProductByNameAndPrice(product);
    return "delete success";
  }

  @GetMapping("/selectall")
  public List<Product> selectAllProduct(){
    return productService.selectAll();
  }

  @PostMapping("/update")
  public String updateProduct(@RequestBody Product product){
    productService.updateProduct(product);
    return "update success";
  }

  @GetMapping("/select/{name}")
  public Product updateProduct(@PathVariable String name){
    return productService.selectByName(name);
  }

  @GetMapping("/member/add")
  public String addMemBerPage(){
    return "addMemberPage";
  }
}
