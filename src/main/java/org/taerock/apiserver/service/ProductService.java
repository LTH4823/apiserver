package org.taerock.apiserver.service;

import org.springframework.transaction.annotation.Transactional;
import org.taerock.apiserver.dto.PageRequestDTO;
import org.taerock.apiserver.dto.PageResponseDTO;
import org.taerock.apiserver.dto.ProductDTO;

@Transactional
public interface ProductService {

    PageResponseDTO<ProductDTO>getList(PageRequestDTO pageRequestDTO);

    Long register(ProductDTO poProductDTO);

}
