package com.developia.goodreads.dao.repository;

import com.developia.goodreads.dao.entity.BooksEntity;
import com.developia.goodreads.model.enums.BookStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BooksRepository extends JpaRepository<BooksEntity, Long> {
    List<BooksEntity> findAllByStatus(BookStatus status);
}
