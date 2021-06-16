package com.developia.goodreads.dao.repository;

import com.developia.goodreads.dao.entity.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdersRepository extends JpaRepository<OrdersEntity, Long> {
    List<OrdersEntity> findAllByUserId(Long userId);
}
