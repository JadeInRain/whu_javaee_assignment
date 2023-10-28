package com.example.assignment4.service;

import com.example.assignment4.entity.Product;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ProductService {

    private Map<Long, Product> productMap =
            Collections.synchronizedMap(new HashMap<Long, Product>());

    public Product addProduct(Product product) {
        productMap.put(product.getId(), product);
        return product;
    }

    //根据Id查找
    public Product getProduct(long id) {
        return productMap.get(id);
    }

    //根据名称查找
    public List<Product> findProduct(String name, Float quantity) {
        List<Product> result=new ArrayList<>();
        for (Product product: productMap.values()){
            if (name!=null && !product.getName().contains(name)) {
                continue;
            }
            result.add(product);
        }
        return result;
    }

    //根据id更新product
    public void updateProduct(long id, Product product) {
        Product product1  = productMap.get(id);
        if(product1!=null){
            productMap.put(id,product);
        }
    }

    //删除商品
    public void deleteProduct(long id) {
        productMap.remove(id);
    }

}
