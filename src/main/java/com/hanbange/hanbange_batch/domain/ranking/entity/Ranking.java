package com.hanbange.hanbange_batch.domain.ranking.entity;

import com.hanbange.hanbange_batch.domain.orders.entity.Orders;
import com.hanbange.hanbange_batch.domain.product.entity.Product;
import com.hanbange.hanbange_batch.domain.ranking.dto.RankingDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Builder
@Getter
@Setter
@Entity
@AllArgsConstructor
public class Ranking {

    public Ranking() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    public Ranking(Orders orders) {
        this.product = orders.getProduct();
        this.quantity = orders.getQuantity();
    }

    public Ranking(RankingDto item) {
        this.product = item.getProduct();
        this.quantity = item.getQuantity().intValue();
    }

    public void plusQuantity(int quantity) {
        this.quantity += quantity;
    }
}
