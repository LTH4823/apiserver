package org.taerock.apiserver.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "tbl_product")
@Builder
@AllArgsConstructor
@NoArgsConstructor
// 해당 객체가 다른 객체와 연관관계를 맺던, ElementCollection이면 우선 ToString을 적용하는 것이 좋습니다.
@ToString(exclude = "imageList")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pno;

    private String pname;

    private int price;

    private String pdesc;

    private boolean delFlag;

    // ElementCollection 자체가 주인공이 되지 않습니다. 쉽게 ElementCollection을 주로 사용하시면 안된다는 뜻 입니다.
    @ElementCollection
    @Builder.Default
    private List<ProductImage> imageList = new ArrayList<>();

    public void changePrice(int price){
        this.price = price;
    }

    public void changeDesc(String desc){
        this.pdesc = desc;
    }

    public void changeName(String name){
        this.pname = name;
    }

    public void changeDel(boolean delFlag){
        this.delFlag = delFlag;
    }

    public void addImage(ProductImage image){

        image.setOrd(imageList.size());
        imageList.add(image);

    }

    public void addImageString(String fileName){

        ProductImage productImage = ProductImage.builder()
                .fileName(fileName)
                .build();

        addImage(productImage);

    }

    public void clearList(){
        this.imageList.clear();
    }


}