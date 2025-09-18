package com.example.demo.controller;


import com.example.demo.dao.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@RequestMapping("/product")
@Controller
public class ProductController {

  @Autowired
  private ProductService productService;

  @PostMapping("/add")
  public String addProduct(@ModelAttribute Product product,
                           @RequestParam("imageFile") MultipartFile imageFile,
                           RedirectAttributes redirectAttributes) throws IOException {

    if (!imageFile.isEmpty()) {
      // 儲存圖片到本地
      String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
      Path uploadPath = Paths.get("uploads/images");
      if (!Files.exists(uploadPath)) {
        Files.createDirectories(uploadPath);
      }
      Path filePath = uploadPath.resolve(fileName);
      Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

      // 存檔案路徑到商品
      product.setImageUrl("/uploads/images/" + fileName);
    }

    productService.addProduct(product);
    redirectAttributes.addFlashAttribute("successMessage", "新增成功！");
    return "redirect:/product/list";
  }
  @GetMapping("/list")
  public String listProducts(Model model) {
    model.addAttribute("products", productService.selectAll());
    return "product/list"; // 對應 templates/product/list.html
  }

  //沒用到
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
  public String updateProduct(@ModelAttribute Product product,
                              @RequestParam("imageFile") MultipartFile imageFile,
                              RedirectAttributes redirectAttributes) throws IOException {
    if (!imageFile.isEmpty()) {
      String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
      Path uploadPath = Paths.get("uploads/images");
      if (!Files.exists(uploadPath)) {
        Files.createDirectories(uploadPath);
      }
      Path filePath = uploadPath.resolve(fileName);
      Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

      product.setImageUrl("/uploads/images/" + fileName);
    }

    productService.updateProduct(product);
    redirectAttributes.addFlashAttribute("successMessage", "修改成功！");

    return "redirect:/product/list"; // 更新後回到商品清單
  }


  @GetMapping("/select/{name}")
  public Product updateProduct(@PathVariable String name){
    return productService.selectByName(name);
  }

}
