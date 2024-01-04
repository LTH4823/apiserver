package org.taerock.apiserver.repository.search;

import org.taerock.apiserver.dto.PageRequestDTO;
import org.taerock.apiserver.dto.PageResponseDTO;
import org.taerock.apiserver.dto.ProductDTO;

public interface ProductSearch {

        PageResponseDTO<ProductDTO>searchList(PageRequestDTO pageRequestDTO);

}
