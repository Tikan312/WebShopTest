package com.webstore.order_management.controller;

import com.webstore.order_management.model.Product;
import com.webstore.order_management.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/products/{id}")
    public Product getProductById(@PathVariable Long id) {
        try {
            return productService.getProductById(id);
        } catch (NoSuchElementException e) {
            System.err.println("Продукта по такому ID не найдено: " + id);
            throw new NoSuchElementException("Продукта по такому ID не найдено: " + id);
        }
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createProduct = productService.saveProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(createProduct);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        try {
            Product existingProduct = productService.getProductById(id);
            existingProduct.setName(product.getName());
            existingProduct.setPrice(product.getPrice());
            Product savedProduct = productService.saveProduct(existingProduct);
            return ResponseEntity.ok(savedProduct);
        } catch (NoSuchElementException e) {
            System.err.println("Продукт по такому ID не найден: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Продукт по такому ID не найден: " + id);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            System.err.println("Продукт с таким ID не найден: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Продукт с таким ID не найден: " + id);
        }
    }
}
