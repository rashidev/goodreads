package com.developia.goodreads.dao.repository;

import com.developia.goodreads.dao.entity.BookReviewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookReviewsRepository extends JpaRepository<BookReviewsEntity, Long> {
}
