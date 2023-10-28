package com.example.assignment4.controller;




import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.assignment4.entity.Product;
import com.example.assignment4.service.ProductService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ProductService productService;

    public ProductControllerTest() {
    }


    @BeforeEach
    private void initData() {
        Product product = new Product();
        product.setId(1L);
        product.setName("IPhone 13");
        product.setPrice(8000);
        this.productService.addProduct(product);
    }

    @Test
    public void testAddProduct() throws Exception {
        Product product = new Product();
        product.setId(2L);
        product.setName("IPhone 18");
        product.setPrice(12000);
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post("/products", new Object[0]).content((new ObjectMapper()).writeValueAsString(product)).contentType(MediaType.APPLICATION_JSON).accept(new MediaType[]{MediaType.APPLICATION_JSON})).andDo(MockMvcResultHandlers.print());
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.id", new Object[0]).value(2));
        Assertions.assertNotNull(this.productService.getProduct(2L));
    }

    @Test
    public void testFindProduct() throws Exception {
        Product product = new Product();
        product.setId(2L);
        product.setName("IPhone 18");
        product.setPrice(12000);
        this.productService.addProduct(product);
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.get("/products/2", new Object[0]).contentType(MediaType.APPLICATION_JSON).accept(new MediaType[]{MediaType.APPLICATION_JSON}));
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.id", new Object[0]).value(2));
        resultActions = this.mockMvc.perform(MockMvcRequestBuilders.get("/products?name=IPhone&quantity=15", new Object[0]).contentType(MediaType.APPLICATION_JSON).accept(new MediaType[]{MediaType.APPLICATION_JSON}));
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$[0].id", new Object[0]).value(2));
    }

    @Test
    public void testUpdateProduct() throws Exception {
        Product product = this.productService.getProduct(1L);
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.put("/products/1", new Object[0]).content((new ObjectMapper()).writeValueAsString(product)).contentType(MediaType.APPLICATION_JSON).accept(new MediaType[]{MediaType.APPLICATION_JSON}));
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        product = this.productService.getProduct(1L);
    }

    @Test
    public void testDeleteProduct() throws Exception {
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.delete("/products/1", new Object[0]).contentType(MediaType.APPLICATION_JSON).accept(new MediaType[]{MediaType.APPLICATION_JSON}));
        Assertions.assertNull(this.productService.getProduct(1L));
    }
}
