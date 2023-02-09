package com.hanbange.hanbange_batch.domain.orders.repository;

import com.hanbange.hanbange_batch.domain.orders.entity.Orders;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

}