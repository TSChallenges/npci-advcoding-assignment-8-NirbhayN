package com.mystore.app.repositories;

import com.mystore.app.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Integer> {

    // TODO
    public  List<Product> findByNameContainsIgnoreCase(String name);
    public  List<Product> findByCategoryContains(String category);
    public  List<Product> findByPriceBetween(Integer min,Integer max);
    public  List<Product> findByStockQuantityBetween(Integer min,Integer max);

}
