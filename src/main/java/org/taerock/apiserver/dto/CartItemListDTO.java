package org.taerock.apiserver.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class CartItemListDTO {

    private Long cino;

    private int qty;

    private String pname;

    private int price;

    private String imageFile;

    // jap projections 라는 기능 DTO를 바로 추출하는 기능 사용 하려고 사용
    public CartItemListDTO(Long cino, int qty, String pname, int price, String imageFile) {
        this.cino = cino;
        this.qty = qty;
        this.pname = pname;
        this.price = price;
        this.imageFile = imageFile;
    }
}
