package com.hanbange.hanbange_batch.domain.ranking.repository;

import com.hanbange.hanbange_batch.domain.product.entity.Product;
import com.hanbange.hanbange_batch.domain.ranking.entity.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RankingRepository extends JpaRepository<Ranking, Long> {

    List<Ranking> findByProduct(Product product);
}