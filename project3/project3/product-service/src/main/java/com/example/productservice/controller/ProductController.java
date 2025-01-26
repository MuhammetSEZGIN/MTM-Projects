package com.example.productservice.controller;

import com.example.productservice.dtos.ProductDto;
import com.example.productservice.dtos.ProductRequestDto;
import com.example.productservice.dtos.ResponseDto;
import com.example.productservice.model.Product;
import com.example.productservice.service.ProductService;
import com.example.productservice.util.Mapper;
import com.example.productservice.util.ResponseUtil;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ResponseDto> createProduct(@Valid @RequestBody ProductRequestDto productDto) {
        try {
            Product product = new Product();

            Mapper.map(productDto, product);
            productService.createProduct(product);
            return ResponseUtil.createSuccessResponse(productDto);
        } catch (Exception e) {
            return ResponseUtil.createFailureResponse(e.getMessage());
        }
    }
    @GetMapping("/all")
    public ResponseEntity<ResponseDto> getAllProducts() {
        try {
            return ResponseUtil.createSuccessResponse(productService.getAllProducts());
        } catch (Exception e) {
            return ResponseUtil.createFailureResponse(e.getMessage());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto> updateProduct(@PathVariable int id, @Valid @RequestBody ProductRequestDto productDto) {
        try {
            Product product = new Product();
            Mapper.map(productDto, product);
            product.setProductID(id);
            productService.updateProduct(product);
            return ResponseUtil.createSuccessResponse(productDto);
        } catch (Exception e) {
            return ResponseUtil.createFailureResponse(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteProduct(@PathVariable int id) {
        try {
            productService.deleteProduct(id);
            return ResponseUtil.createSuccessResponse(null);
        } catch (Exception e) {
            return ResponseUtil.createFailureResponse(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getProductById(@PathVariable int id) {
        try {
            Product product = productService.getProductById(id);
            ProductDto productDto = new ProductDto();
            Mapper.map(product, productDto);
            return ResponseUtil.createSuccessResponse(productDto);
        } catch (Exception e) {
            return ResponseUtil.createFailureResponse(e.getMessage());
        }
    }
}

