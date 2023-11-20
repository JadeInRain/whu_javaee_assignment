package com.example.assignment4.service;

import com.example.assignment4.entity.Product;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ProductService {
    private Map<Long, Product> productMap=
            Collections.synchronizedMap(new HashMap<Long, Product>());

    public Product addProduct(Product product){
        productMap.put(product.getId(),product);
        return product;
    }
    public Product getProduct(long id){
        return productMap.get(id);
    }
    public List<Product> findProduct(String name,String category){
        System.out.println("find!");
        List<Product> result=new ArrayList<>();
        for(Product product:productMap.values()){
            if(Objects.equals(name, product.getName()) && Objects.equals(category, product.getCategory()))
                result.add(product);
        }
        return result;
    }
    public void updateProduct(long id,Product product){
        Product product1=productMap.get(id);
        if(product1!=null)
            productMap.put(id,product);
    }
    public void deleteProduct(long id){
        productMap.remove(id);
    }
    public void deleteAll(){
        productMap.clear();
    }
}
