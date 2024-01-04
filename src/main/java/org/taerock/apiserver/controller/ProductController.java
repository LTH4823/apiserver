package org.taerock.apiserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import org.taerock.apiserver.dto.PageRequestDTO;
import org.taerock.apiserver.dto.PageResponseDTO;
import org.taerock.apiserver.dto.ProductDTO;
import org.taerock.apiserver.service.ProductService;
import org.taerock.apiserver.util.CustomFileUtil;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final CustomFileUtil fileUtil;

    private final ProductService productService;

    // 파일 업로드 테스트 때 사용 했던 코드
/*    @PostMapping("/")
    public Map<String,String> register(ProductDTO productDTO) {

        log.info("register" + productDTO);

        List<MultipartFile> files = productDTO.getFiles();

        List<String> uploadedFileName = fileUtil.saveFiles(files);

        productDTO.setUploadFileNames(uploadedFileName);

        log.info(uploadedFileName);

        return Map.of("RESULT", "SUCCESS");
    }*/

    @GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> viewFileGET(@PathVariable("fileName") String fileName){
        return fileUtil.getFile(fileName);
    }

    @GetMapping("/list")
    public PageResponseDTO<ProductDTO>list(PageRequestDTO pageRequestDTO){
        return productService.getList(pageRequestDTO);
    }

    @PostMapping("/")
    public Map<String, Long>register(ProductDTO productDTO) {

        List<MultipartFile> files = productDTO.getFiles();

        List<String> uploadFileNames = fileUtil.saveFiles(files);

        productDTO.setUploadFileNames(uploadFileNames);

        log.info(uploadFileNames);

        Long pno = productService.register(productDTO);

        return Map.of("result", pno);
    }

    @GetMapping("/{pno}")
    public ProductDTO read(@PathVariable("pno") Long pno){
        return productService.get(pno);
    }

    @PutMapping("/{pno}")
    public Map<String, String> modify(@PathVariable("pno")Long pno, ProductDTO productDTO){

        // ------------- 신규 이미지 포함 전의 파일 포함 등록
        productDTO.setPno(pno);

        //old product
        ProductDTO oldProductDTO = productService.get(pno);

        //file upload
        List<MultipartFile> files = productDTO.getFiles();
        List<String>currentUploadFileNames = fileUtil.saveFiles(files);

        //keep files String
        List<String>uploadedFileNames = productDTO.getUploadFileNames();

        if(currentUploadFileNames != null && !currentUploadFileNames.isEmpty()){

            uploadedFileNames.addAll(currentUploadFileNames);

        }

        productService.modify(productDTO);
        // -------------

        // ------------- 기존 파일 부분 삭제
        List<String>oldFileNames = oldProductDTO.getUploadFileNames();

        if(oldFileNames != null && oldFileNames.size() > 0){
            List<String>removeFiles =
                // oldFileNames 목록에서 filter로 uploadedFileNames fileName과 비교하여 결과 -1(없음)인 파일 분류
                oldFileNames.stream().filter(fileName -> uploadedFileNames.indexOf(fileName) == -1).collect(Collectors.toList());

            fileUtil.deleteFile(removeFiles);

        }//end if
        // -------------

        return Map.of("RESULT", "SUCCESS");

    }

}
