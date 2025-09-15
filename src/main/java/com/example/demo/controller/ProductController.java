package com.example.demo.controller;


import com.example.demo.domain.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;

@RequestMapping("/product")
@Controller
public class ProductController {

  @Autowired
  private ProductService productService;

  @GetMapping("/add")
  public String showAddForm(Model model) {
    model.addAttribute("product",new Product());
    return "/product/add";
  }

  @PostMapping("/add")
  public String createProduct(@ModelAttribute Product product) {
     productService.addProduct(product);
     return "redirect:/product/list";
  }

  @GetMapping("/list")
  public String listProducts(Model model) {
    model.addAttribute("products", productService.selectAll());
    return "product/list"; // 對應 templates/product/list.html
  }

  @DeleteMapping("/delete")
  public String deleteProduct(@RequestBody Product product){
    productService.deleteProductByNameAndPrice(product);
    return "delete success";
  }
  // 刪除商品（GET 方法）
  @GetMapping("/delete/{id}")
  public String deleteProductById(@PathVariable String id, RedirectAttributes redirectAttributes) {
    productService.deleteProductById(id);
    redirectAttributes.addFlashAttribute("successMessage", "刪除成功！");
    return "redirect:/product/list"; // 刪除後回到商品清單
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
