package com.developia.goodreads.service;

import com.developia.goodreads.dao.entity.BooksEntity;
import com.developia.goodreads.dao.entity.BucketsEntity;
import com.developia.goodreads.dao.entity.UsersEntity;
import com.developia.goodreads.dao.repository.BooksRepository;
import com.developia.goodreads.dao.repository.BucketsRepository;
import com.developia.goodreads.dao.repository.UsersRepository;
import com.developia.goodreads.model.exception.NotFoundException;
import liquibase.pro.packaged.B;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.validation.constraints.Null;
import java.util.Optional;

class BucketServiceTest {

    @Mock
    UsersRepository usersRepository;
    @Mock
    BucketsRepository bucketsRepository;
    @Mock
    BooksRepository booksRepository;

    @InjectMocks
    BucketService bucketService;

    @BeforeEach
    void setUp() {
        System.out.println("BeforeEach setUp method");
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deleteBookFromBucketUserRepositoryException() {
        Optional<UsersEntity> user = Optional.empty();
        Mockito.when(usersRepository.findByLogin("test")).thenReturn(user);

        Assertions.assertThrows(
                NotFoundException.class,
                () -> bucketService.deleteBookFromBucket("test", 1L)
        );
    }

    @Test
    void deleteBookFromBucketSuccess() {
        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setId(1L);
        Optional<UsersEntity> user = Optional.of(usersEntity);

        Mockito.when(usersRepository.findByLogin("test")).thenReturn(user);

        BucketsEntity bucketsEntity = new BucketsEntity();
        Optional<BucketsEntity> bucket = Optional.of(bucketsEntity);
        Mockito.when(bucketsRepository.findByUserId(1L)).thenReturn(bucket);

        BooksEntity booksEntity = new BooksEntity();
        Optional<BooksEntity> book = Optional.of(booksEntity);

        Mockito.when(booksRepository.findById(5L)).thenReturn(book);

        bucketService.deleteBookFromBucket("test", 5L);
    }
}