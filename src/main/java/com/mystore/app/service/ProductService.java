package com.mystore.app.service;

import com.mystore.app.entity.Product;
import com.mystore.app.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private Integer currentId = 1;

    @Autowired
    private ProductRepository productRepository;

    public Product addProduct(Product product) {
        product.setId(currentId++);
        productRepository.save(product);
        return product;
    }

    public Page<Product> getAllProducts(Integer page, Integer pageSize, String sortBy, String sortDir) {
        Sort.Direction direction= Sort.DEFAULT_DIRECTION;
        if(sortDir.equalsIgnoreCase("asc")){
            direction=Sort.Direction.ASC;
        } else if (sortDir.equalsIgnoreCase("desc")) {
            direction=Sort.Direction.DESC;
        }
        Sort sort=Sort.by(direction,sortBy);
        PageRequest pageRequest=PageRequest.of(page,pageSize,sort);

        return productRepository.findAll(pageRequest);
    }

    public Product getProduct(Integer id) {
        Optional<Product> productOptional = productRepository.findById(id);
        return productOptional.get();
    }

    public Product updateProduct(Integer id, Product product) {
        Product p = productRepository.findById(id).get();
        if (p == null) return null;
        p.setName(product.getName());
        p.setPrice(product.getPrice());
        p.setCategory(product.getCategory());
        p.setStockQuantity(product.getStockQuantity());
        productRepository.save(p);
        return p;
    }

    public String deleteProduct(Integer id) {
        Product p = productRepository.findById(id).get();
        if (p == null) return "Product Not Found";
        productRepository.delete(p);
        return "Product Deleted Successfully";
    }

    // TODO: Method to search products by name
    public List<Product> getProductsByName(String name){
        return productRepository.findByNameContainsIgnoreCase(name);
    }
    // TODO: Method to filter products by category
    public List<Product> filterByCategory(String category){
        return productRepository.findByCategoryContains(category);
    }


    // TODO: Method to filter products by price range
    public List<Product> filterProductByPrice(Integer min,Integer max){
        return productRepository.findByPriceBetween(min,max);
    }

    // TODO: Method to filter products by stock quantity range
    public List<Product> filterProductByStock(Integer min,Integer max){
        return productRepository.findByStockQuantityBetween(min,max);
    }


}
