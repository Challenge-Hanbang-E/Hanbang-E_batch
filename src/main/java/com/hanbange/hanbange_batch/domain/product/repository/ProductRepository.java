package com.hanbange.hanbange_batch.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hanbange.hanbange_batch.domain.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
}
