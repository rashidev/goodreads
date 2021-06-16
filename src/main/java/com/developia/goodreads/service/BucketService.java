package com.developia.goodreads.service;

import com.developia.goodreads.dao.entity.BooksEntity;
import com.developia.goodreads.dao.entity.BucketsEntity;
import com.developia.goodreads.dao.entity.UsersEntity;
import com.developia.goodreads.dao.repository.BooksRepository;
import com.developia.goodreads.dao.repository.BucketsRepository;
import com.developia.goodreads.dao.repository.UsersRepository;
import com.developia.goodreads.model.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BucketService {
    private UsersRepository usersRepository;
    private BucketsRepository bucketsRepository;
    private BooksRepository booksRepository;

    public BucketService(UsersRepository usersRepository,
                         BucketsRepository bucketsRepository,
                         BooksRepository booksRepository) {
        this.usersRepository = usersRepository;
        this.bucketsRepository = bucketsRepository;
        this.booksRepository = booksRepository;
    }

    public BucketsEntity getBucket(String login) {
        UsersEntity user = usersRepository.findByLogin(login)
                .orElseThrow(() -> {
                    throw new NotFoundException("User not found");
                });

        // Optional olmasaydi bele kod yazardiq
//        if (user == null) {
//            throw new NotFoundException("User not found");
//        }

        BucketsEntity bucket = bucketsRepository.findByUserId(user.getId())
                .orElse(new BucketsEntity());

        // Optional olmasaydi bele kod yazardiq
//        if(bucket == null) {
//            bucket = new BucketsEntity();
//        }

        return bucket;
    }

    public void addToBucket(String login, Long bookId) {
        UsersEntity user = usersRepository.findByLogin(login)
                .orElseThrow(() -> {
                    throw new NotFoundException("User not found");
                });
        BucketsEntity bucket = bucketsRepository.findByUserId(user.getId())
                .orElse(new BucketsEntity());
        BooksEntity book = booksRepository.findById(bookId)
                .orElseThrow(() -> {
                    throw new NotFoundException("Book not found");
                });

        bucket.setUserId(user.getId());
        List<BooksEntity> books = bucket.getBooks();
        books.add(book);

        bucket.setBooks(books);

        bucketsRepository.save(bucket);
    }

    public void deleteBookFromBucket(String login, Long bookId) {
        UsersEntity user = usersRepository.findByLogin(login)
                .orElseThrow(() -> {
                    throw new NotFoundException("User not found");
                });
        BucketsEntity bucket = bucketsRepository.findByUserId(user.getId())
                .orElseThrow(() -> {
                    throw new NotFoundException("Bucket not found");
                });
        BooksEntity book = booksRepository.findById(bookId).orElseThrow(() -> {
            throw new NotFoundException("Book not found");
        });

        List<BooksEntity> books = bucket.getBooks();
        books.remove(book);

        bucket.setBooks(books);

        // eger bookId = 2
        /* books_buckets
         * book_id bucket_id
         * 1       1
         * 2       1  bu yazini silir
         * 3       1
         * */

        bucketsRepository.save(bucket);

    }
}
