package edu.whu.demo.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import edu.whu.demo.domain.Product;
import edu.whu.demo.domain.ProductDTO;
import edu.whu.demo.domain.Result;
import edu.whu.demo.domain.Supplier;
import edu.whu.demo.exception.ProductAdminException;
import edu.whu.demo.service.IProductService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    IProductService productService;

    @ApiOperation("根据Id查询商品")
    @GetMapping("/{id}")
    public Product getProduct(@ApiParam("商品Id") @PathVariable long id) throws ProductAdminException{
        Product result = productService.getById(id);

            return result;
        }


    @ApiOperation("根据条件查询商品")
    @GetMapping("")
    public IPage<ProductDTO> findProduct(@ApiParam("商品名称")String name,
                                      @ApiParam("商品价格")Float price,
                                      @ApiParam("商品价格")Float stockQuantity,
                                      @ApiParam("商品价格")String category,
                                      @ApiParam("商品价格")String productType,
                                      @ApiParam("供应商名称")String supplierName,
                                      @ApiParam("页码")@RequestParam(defaultValue = "0")Integer pageNum,
                                      @ApiParam("每页记录数") @RequestParam(defaultValue = "10")Integer pageSize) throws ProductAdminException{
        Map<String,Object> condition=new HashMap<>();
        if(name!=null) {
            condition.put("name","%"+name+"%");
        }
        if(price!=null) {
            condition.put("price",price);
        }
        if(category!=null) {
            condition.put("category",category);
        }
        if(stockQuantity!=null) {
            condition.put("stockQuantity",stockQuantity);
        }
        if(productType!=null) {
            condition.put("productType",productType);
        }
        if(supplierName!=null) {
            condition.put("supplierName",supplierName);
        }
        IPage<ProductDTO> result = productService.findProduct(condition, pageNum, pageSize);
        return result;
    }


    @ApiOperation("添加商品")
    @PostMapping("")
    public Product addProduct(@RequestBody Product product) throws ProductAdminException{

            Product result = productService.addProduct(product);
            return result;

    }

    @ApiOperation("修改商品信息")
    @PutMapping("/{id}")
    public String updateProduct(@PathVariable long id,@RequestBody Product product) throws ProductAdminException {

           productService.updateProduct(id,product);
           return "success";

    }

    @ApiOperation("修改商品的供应商")
    @PutMapping("/{id}/suppliers")
    public String updateProductSuppliers(@PathVariable long id,@RequestBody List<Supplier> suppliers) throws ProductAdminException {

            productService.updateProductSuppliers(id,suppliers);
            return "success";

    }

    @ApiOperation("删除商品")
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable long id) throws ProductAdminException{

            productService.removeById(id);
            return "success";

    }
}

