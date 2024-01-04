    package org.taerock.apiserver.service;

    import lombok.RequiredArgsConstructor;
    import lombok.extern.log4j.Log4j2;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.PageRequest;
    import org.springframework.data.domain.Pageable;
    import org.springframework.data.domain.Sort;
    import org.springframework.stereotype.Service;
    import org.taerock.apiserver.domain.Product;
    import org.taerock.apiserver.domain.ProductImage;
    import org.taerock.apiserver.dto.PageRequestDTO;
    import org.taerock.apiserver.dto.PageResponseDTO;
    import org.taerock.apiserver.dto.ProductDTO;
    import org.taerock.apiserver.repository.ProductRepository;

    import java.util.List;
    import java.util.stream.Collectors;

    @Service
    @Log4j2
    @RequiredArgsConstructor
    public class ProductServiceImpl implements ProductService{

        private final ProductRepository productRepository;

        @Override
        public PageResponseDTO<ProductDTO> getList(PageRequestDTO pageRequestDTO) {

            Pageable pageable = PageRequest.of(
                    pageRequestDTO.getPage()-1,
                    pageRequestDTO.getSize(),
                    Sort.by("pno").descending());

            Page<Object[]> result = productRepository.selectList(pageable);

            //Object[] => 0 product 1 productImage
            //Object[] => 0 product 1 productImage
            //Object[] => 0 product 1 productImage

            // map을 이용한 dto 변환 처리
            // 해당 방법말고 projection 방법도 있으니 추후 찾아서 참고
            List<ProductDTO>dtoList = result.get().map(arr -> {

               ProductDTO productDTO = null;

                Product product = (Product) arr[0];
                ProductImage productImage = (ProductImage) arr[1];

                productDTO = ProductDTO.builder()
                        .pno(product.getPno())
                        .pname(product.getPname())
                        .pdesc(product.getPdesc())
                        .price(product.getPrice())
                        .build();

                String imageStr = productImage.getFileName();
                productDTO.setUploadFileNames(List.of(imageStr));

               return productDTO;
            }).collect(Collectors.toList());

            long totalCount = result.getTotalElements();

            return PageResponseDTO.<ProductDTO>withAll()
                    .dtoList(dtoList)
                    .total(totalCount)
                    .pageRequestDTO(pageRequestDTO)
                    .build();
        }

    }
