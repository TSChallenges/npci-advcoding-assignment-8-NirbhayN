package com.mystore.app.rest;

import com.mystore.app.entity.Product;
import com.mystore.app.service.ProductService;
import jakarta.validation.Valid;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("")
    public ResponseEntity<Object> addProduct(@RequestBody @Valid Product product, BindingResult bindingResult) {
        Map<String,String> mp=new HashMap<>();

        if(bindingResult.hasErrors()){
            for(FieldError fieldError: bindingResult.getFieldErrors()){
                mp.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return new ResponseEntity<>(mp,HttpStatus.BAD_REQUEST);
        }

        Product p = productService.addProduct(product);
        return new ResponseEntity<>(p, HttpStatus.CREATED);
    }

    @GetMapping("")
    public Page<Product> getAllProducts(@RequestParam("page") Integer page,@RequestParam("pageSize") Integer pageSize,@RequestParam("sortBy") String sortBy, @RequestParam("sortDir") String sortDir) {

        return productService.getAllProducts(page,pageSize,sortBy,sortDir);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") Integer id) {
        Product p = productService.getProduct(id);
        if (p != null) {
            return new ResponseEntity<>(p, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable("id") Integer id, @Valid @RequestBody Product product,BindingResult bindingResult) {
        Map<String,String> mp=new HashMap<>();

        if(bindingResult.hasErrors()){
            for(FieldError fieldError: bindingResult.getFieldErrors()){
                mp.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return new ResponseEntity<>(mp,HttpStatus.BAD_REQUEST);
        }


        Product p = productService.updateProduct(id, product);
        if (p != null) {
            return new ResponseEntity<>(p, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Integer id) {
        String message = productService.deleteProduct(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // TODO: API to search products by name

    @GetMapping("/search")
    public List<Product> getProductByName(@RequestParam("name") String name){
        return productService.getProductsByName(name);
    }

    // TODO: API to filter products by category
    @GetMapping("/filter/category")
    public List<Product> getProductByCategory(@RequestParam("category") String category){
        return productService.filterByCategory(category);
    }

    // TODO: API to filter products by price range
    @GetMapping("/filter/price")
    public List<Product> getProductByPriceRange(@RequestParam("minPrice") Integer minPrice,@RequestParam("maxPrice") Integer maxPrice){
        return productService.filterProductByPrice(minPrice,maxPrice);
    }


    // TODO: API to filter products by stock quantity range
    @GetMapping("/filter/stock")
    public List<Product> getProductByStockQuantity(@RequestParam("minStock") Integer minStock,@RequestParam("maxStock") Integer maxStock){
        return productService.filterProductByStock(minStock,maxStock);
    }


}
