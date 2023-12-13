package edu.whu.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.whu.demo.aspect.ApiAspect;
import edu.whu.demo.domain.Product;
import edu.whu.demo.service.IProductService;
import edu.whu.demo.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class ApiAspectTest {



    @Autowired
    ProductServiceImpl productService;
    @Autowired
    private MockMvc mockMvc;


    @Autowired
    ApiAspect apiAspect;





    //测试统计api调用次数
    @Test
    void testCallNum() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/product/{id}",1l));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/product/{id}",2l));
        assertEquals(2,apiAspect.getCallNum().get("ResponseEntity edu.whu.demo.controller.ProductController.getProduct(long)"));

    }

    //测试计算api运行时间
    @Test
    void testTime() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/product/{id}",1l));
        Product product=new Product();
        product.setId(2l);
        product.setName("IPhone 18");
        product.setPrice(12000f);
        product.setProductType("SND-dff");
        product.setCategory("手机");
        product.setStockQuantity(10f);

        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/product")
                        .content(new ObjectMapper().writeValueAsString(product))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        this.mockMvc.perform(MockMvcRequestBuilders.get("/product/{id}",1l));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/product/{id}",1l));
        for(String name:apiAspect.getTime().keySet())
        {
            System.out.println(name+": ");
            int i=0;
            for(Integer time :apiAspect.getTime().get(name)){
                switch (i)
                {
                    case 0:
                        System.out.println("最长运行时间："+time+" ");
                        i++;
                        break;
                    case 1:
                        System.out.println("最短运行时间："+time+" ");
                        i++;
                        break;
                    case 2:
                        System.out.println("平均运行时间："+time+" ");
                        i=0;
                        break;
                }




            }
        }
    }

    @Test
    void testExNum() throws Exception {
        Product product=new Product();
        product.setId(2l);
        product.setName("IPhone 18");
        product.setPrice(12000f);
        product.setProductType("SND-dff");
        product.setCategory("手机");
        product.setStockQuantity(10f);
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders
                .put("/product/1")
                .content(new ObjectMapper().writeValueAsString(product))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        assertEquals(1,apiAspect.getExNum().get("String edu.whu.demo.controller.ProductController.updateProduct(long,Product)"));

    }

}
