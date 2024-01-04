package org.taerock.apiserver.repository.search;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.taerock.apiserver.domain.Product;
import org.taerock.apiserver.domain.QProduct;
import org.taerock.apiserver.domain.QProductImage;
import org.taerock.apiserver.dto.PageRequestDTO;
import org.taerock.apiserver.dto.PageResponseDTO;
import org.taerock.apiserver.dto.ProductDTO;

import java.util.List;
import java.util.Objects;

@Log4j2
public class ProductSearchImpl extends QuerydslRepositorySupport implements ProductSearch {

    public ProductSearchImpl(){
        super(Product.class);
    }

    @Override
    public PageResponseDTO<ProductDTO> searchList(PageRequestDTO pageRequestDTO) {
        log.info("------------searchList------------");

        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() -1,
                pageRequestDTO.getSize(),
                Sort.by("pno").descending());

        QProduct product = QProduct.product;
        QProductImage productImage = QProductImage.productImage;

        // ElementCollection 가지고 queryDsl을 사용할 때
        // tbl_product from 절 생성
        JPQLQuery<Product> query = from(product);

        // left join 절
        query.leftJoin(product.imageList, productImage);

        query.where(productImage.ord.eq(0));

        // getQuerydsl().applyPagination(pageable, query);
        Objects.requireNonNull(getQuerydsl()).applyPagination(pageable, query);

        // 목록으로 전부 출력하는 방법
        //List<Product> productList = query.fetch();

        // 배열로 분할해서 뽑는 방법
        List<Tuple> productList = query.select(product, productImage).fetch();

        long count = query.fetchCount();

        log.info("===================================");
        log.info(productList);

        return null;
    }
}
