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
    import java.util.Optional;
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

        @Override
        public Long register(ProductDTO poProductDTO) {

            Product product = dtoToEntity(poProductDTO);
            log.info("-------------------");
            log.info(product);
            log.info(product.getImageList());

            Long pno = productRepository.save(product).getPno();

            return pno;
        }

        @Override
        public ProductDTO get(Long pno) {

            Optional<Product> result = productRepository.findById(pno);

            Product product = result.orElseThrow();

            return entityToDTO(product);

        }

        @Override
        public void modify(ProductDTO productDTO) {

            //조회
            Optional<Product> result = productRepository.findById(productDTO.getPno());

            Product product = result.orElseThrow();

            //변경 내용 반영
            product.changePrice(productDTO.getPrice());
            product.changeName(productDTO.getPname());
            product.changeDesc(productDTO.getPdesc());
            product.changeDel(productDTO.isDelFlag());

            //이미지 처리
            List<String> uploadFileNames = productDTO.getUploadFileNames();

            product.clearList();

            if (uploadFileNames != null && !uploadFileNames.isEmpty()){
                uploadFileNames.forEach(uploadName -> {
                    product.addImageString(uploadName);
                });
            }

            //수정
            productRepository.save(product);

        }

        @Override
        public void remove(Long pno) {
            productRepository.deleteById(pno);
        }

        private ProductDTO entityToDTO(Product product){

            ProductDTO productDTO = ProductDTO.builder()
                    .pno(product.getPno())
                    .pname(product.getPname())
                    .pdesc(product.getPdesc())
                    .price(product.getPrice())
                    .delFlag(product.isDelFlag())
                    .build();

            List<ProductImage> imageList = product.getImageList();

            if(imageList == null || imageList.size() ==0){
                return productDTO;
            }

            List<String>fileNameList = imageList.stream().map(productImage ->
                    productImage.getFileName()).toList();

            productDTO.setUploadFileNames(fileNameList);

            return productDTO;
        }


        private Product dtoToEntity(ProductDTO productDTO){
            Product product = Product.builder()
                    .pno(productDTO.getPno())
                    .pname(productDTO.getPname())
                    .pdesc(productDTO.getPdesc())
                    .price(productDTO.getPrice())
                    .build();

            // 업로드가 되었다면 있을 파일 명
            List<String> uploadFileNames = productDTO.getUploadFileNames();

            // 업로드 파일이 없는 경우
            if(uploadFileNames == null || uploadFileNames.size() == 0){
                return product;
            }

            // 업로드 파일이 있는 경우
            uploadFileNames.forEach(fileName -> {
                product.addImageString(fileName);
            });

            return product;
        }

    }
