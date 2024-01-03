package org.taerock.apiserver.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.taerock.apiserver.domain.Product;

import java.util.UUID;

@SpringBootTest
@Log4j2
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testInsert(){
        Product product = Product.builder()
                .pname("Test")
                .pdesc("Test Desc")
                .price(1000)
                .build();
        product.addImageString(UUID.randomUUID()+"_"+"IMAGE1.jps");
        product.addImageString(UUID.randomUUID()+"_"+"IMAGE2.jps");

        productRepository.save(product);

    }

}
