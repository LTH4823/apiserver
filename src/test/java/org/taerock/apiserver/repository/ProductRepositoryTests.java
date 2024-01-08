package org.taerock.apiserver.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.taerock.apiserver.domain.Product;
import org.taerock.apiserver.dto.PageRequestDTO;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@Log4j2
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testInsert(){
        for (int i = 0; i < 10; i++) {
            Product product = Product.builder()
                    .pname("Test " + i)
                    .pdesc("Test Desc" + i)
                    .price(1000)
                    .build();
            product.addImageString(UUID.randomUUID()+"_"+"IMAGE1.jps");
            product.addImageString(UUID.randomUUID()+"_"+"IMAGE2.jps");

            productRepository.save(product);
        }

    }

    // Transactional을 재외할 시 이유는 db 검색을 2번 하기 떄문에 에러 발생
    @Transactional
    @Test
    public void testRead(){

        Long pno  = 1L;

        Optional<Product> result = productRepository.findById(pno);

        Product product = result.orElseThrow();

        log.info(product);

        log.info(product.getImageList());

    }

    // 정상적으로 결과가 나오는데 이는 EntityGraph에 의해서 imageList가 자동으로 참조되어 조인 되기 떄문입니다.
    @Test
    public void testRead2(){

        Long pno  = 1L;

        Optional<Product> result = productRepository.selectOne(pno);

        Product product = result.orElseThrow();

        log.info(product);

        log.info(product.getImageList());

    }

    @Commit
    @Transactional
    @Test
    public void testDelete(){

        Long pno = 1L;

        productRepository.updateToDelete(pno, true);

    }

    @Test
    public void testUpdate(){

        Product product = productRepository.selectOne(1L).get();

        product.changePrice(3000);

        product.clearList();

        // jpa가 다른 arrayList로 바꿀 시 문제가 발생할 수가 있어서 주의해야합니다.
        product.addImageString(UUID.randomUUID()+"_"+"PIMAGE1");

        product.addImageString(UUID.randomUUID()+"_"+"PIMAGE2");

        product.addImageString(UUID.randomUUID()+"_"+"PIMAGE3");

        productRepository.save(product);
    }

    @Test
    public void testList(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("pno").descending());

        Page<Object[]> result = productRepository.selectList(pageable);

        result.getContent().forEach(arr -> log.info(Arrays.toString(arr)));

    }

    @Test
    public void testSearch(){

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().build();

        //productRepository.searchList(pageRequestDTO);

    }

}
