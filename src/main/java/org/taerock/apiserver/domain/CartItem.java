package org.taerock.apiserver.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
// 연관관계 제거(가능한 빼주는게 좋습니다.)
@ToString(exclude = {"cart", "product"})
@Table(
        name = "tbl_cart_item",
        indexes = {
                @Index(columnList = "cart_cno", name = "idx_cartitem_cart"),
                @Index(columnList = "product_pno, cart_cno", name = "idx_cartitem_pno_cart")

        }
)
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cino;

    @ManyToOne
    @JoinColumn(name = "product_pno")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "cart_cno")
    private Cart cart;

    private int qty;

    public void changeQty(int qty){
        this.qty = qty;
    }

}
