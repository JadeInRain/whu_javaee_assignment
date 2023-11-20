package com.example.assignment4.controller;

import com.example.assignment4.entity.Product;
import com.example.assignment4.service.ProductService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    ProductService productService;
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable long id){
        Product result=productService.getProduct(id);
        if(result!=null)
            return ResponseEntity.ok(result);
        else return ResponseEntity.noContent().build();
    }
    @GetMapping("")
    public ResponseEntity<List<Product>> findProduct(String name, String category){
        List<Product> result=productService.findProduct(name,category);
        return ResponseEntity.ok(result);
    }
    @PostMapping("")
    public ResponseEntity<Product> addProduct(@RequestBody Product product){
        Product result=productService.addProduct(product);
        return ResponseEntity.ok(result);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable long id,@RequestBody Product product ){
        productService.updateProduct(id,product);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable long id){
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
