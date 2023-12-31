package org.taerock.apiserver.service;

import org.springframework.transaction.annotation.Transactional;
import org.taerock.apiserver.dto.PageRequestDTO;
import org.taerock.apiserver.dto.PageResponseDTO;
import org.taerock.apiserver.dto.ProductDTO;

@Transactional
public interface ProductService {

    PageResponseDTO<ProductDTO>getList(PageRequestDTO pageRequestDTO);

    Long register(ProductDTO poProductDTO);

    ProductDTO get(Long pno);

    void modify(ProductDTO productDTO);

    // elementCollection은 엔티티 삭제 시 자동으로 삭제됨 종속적 특징
    void remove(Long pno);

}
