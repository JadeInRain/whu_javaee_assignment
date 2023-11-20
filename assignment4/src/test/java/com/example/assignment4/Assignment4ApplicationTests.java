package com.example.assignment4;

import com.example.assignment4.entity.Product;
import com.example.assignment4.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

//import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class Assignment4ApplicationTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ProductService productService;

    @AfterEach
    private void clean(){
        productService.deleteAll();
    }
    @BeforeEach
    private void initData(){
        Product product=new Product();
        product.setId(1);
        product.setPrice(7999);
        product.setName("iphone15pro");
        product.setCategory("phone");
        productService.addProduct(product);
    }

    @Test
    public void testAddProduct() throws Exception{
        Product product=new Product();
        product.setId(2);
        product.setName("iphone16");
        product.setPrice(12000);
        product.setCategory("phone");

        ResultActions resultActions=this.mockMvc.perform(MockMvcRequestBuilders
                .post("/products")
                .content(new ObjectMapper().writeValueAsString(product))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2));

        assertNotNull(productService.getProduct(2));
    }
    @Test
    public void testFindProduct() throws Exception{
        Product product=new Product();
        product.setId(2);
        product.setName("iphone16");
        product.setPrice(12000);
        product.setCategory("phone");
        productService.addProduct(product);
        ResultActions resultActions=this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/products/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(2));

        resultActions=this.mockMvc.perform(MockMvcRequestBuilders
                .get("/products?name=iphone16&category=phone")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").value(2));

    }
    @Test
    public void testUpdateProduct() throws Exception{
        Product product=productService.getProduct((1));
        product.setPrice(4999);

        ResultActions resultActions=this.mockMvc.perform(MockMvcRequestBuilders
                .put("/products/1")
                .content(new ObjectMapper().writeValueAsString(product))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isOk());
        product=productService.getProduct(1);
        assertEquals(4999,product.getPrice());
    }
    @Test
    public void testDeleteProduct() throws Exception {
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        assertNull(productService.getProduct(1));

    }
}
