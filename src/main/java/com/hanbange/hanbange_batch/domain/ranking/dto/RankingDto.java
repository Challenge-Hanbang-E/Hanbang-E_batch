package com.hanbange.hanbange_batch.domain.ranking.dto;

import com.hanbange.hanbange_batch.domain.product.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RankingDto {

    Product product;

    Long quantity;
}