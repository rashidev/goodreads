package com.developia.goodreads.dao.repository;

import com.developia.goodreads.dao.entity.BucketsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BucketsRepository extends JpaRepository<BucketsEntity, Long> {
    Optional<BucketsEntity> findByUserId(Long userId);
}
