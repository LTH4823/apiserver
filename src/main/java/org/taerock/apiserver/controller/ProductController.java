package org.taerock.apiserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.taerock.apiserver.dto.ProductDTO;
import org.taerock.apiserver.util.CustomFileUtil;

import java.util.List;
import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final CustomFileUtil fileUtil;

    @PostMapping("/")
    public Map<String,String> register(ProductDTO productDTO) {

        log.info("register" + productDTO);

        List<MultipartFile> files = productDTO.getFiles();

        List<String> uploadedFileName = fileUtil.saveFiles(files);

        productDTO.setUploadedFileNames(uploadedFileName);

        log.info(uploadedFileName);

        return Map.of("RESULT", "SUCCESS");
    }

}
