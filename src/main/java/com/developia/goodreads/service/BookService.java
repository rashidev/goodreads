package com.developia.goodreads.service;

import com.developia.goodreads.dao.entity.BookReviewsEntity;
import com.developia.goodreads.dao.entity.BooksEntity;
import com.developia.goodreads.dao.entity.UsersEntity;
import com.developia.goodreads.dao.repository.BookReviewsRepository;
import com.developia.goodreads.dao.repository.BooksRepository;
import com.developia.goodreads.dao.repository.UsersRepository;
import com.developia.goodreads.model.enums.BookStatus;
import com.developia.goodreads.model.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private BooksRepository booksRepository;
    private BookReviewsRepository reviewsRepository;
    private UsersRepository usersRepository;

    public BookService(BooksRepository booksRepository,
                       BookReviewsRepository reviewsRepository, UsersRepository usersRepository) {
        this.booksRepository = booksRepository;
        this.reviewsRepository = reviewsRepository;
        this.usersRepository = usersRepository;
    }

    public List<BooksEntity> getBooks() {
        List<BooksEntity> books = booksRepository.findAllByStatus(BookStatus.CREATED);

        return books;
    }

    public BooksEntity getBook(Long id) {
        BooksEntity book = booksRepository.findById(id)
                .orElseThrow(() -> {
                    throw new NotFoundException("Book not found");
                });

        return book;
    }

    public void addReview(BookReviewsEntity review, Long bookId, String login) {
        UsersEntity user = usersRepository.findByLogin(login)
                .orElseThrow( // orElseThrow metodu eger findByLogin metodu esasinda bazadan melumat gelibse onda
                        // UsersEntity qaytaracaq eks halda dushecek asahgidaki exception atan koda
                        () -> {
                            throw new NotFoundException("User not found");
                        }
                );
        review.setId(null);
        review.setUser(user);
        review.setBookId(bookId);
        reviewsRepository.save(review);
    }

    public void createBook(BooksEntity book) {
        book.setStatus(BookStatus.CREATED);
        booksRepository.save(book);
    }

    public void deleteBook(Long id) {
        BooksEntity book = booksRepository.findById(id).orElseThrow(
                () -> {
                    throw new NotFoundException("Book not found");
                }
        );

        book.setStatus(BookStatus.DELETED);

        //upsert - update + insert
        booksRepository.save(book);
    }

    public void updateBook(Long id, BooksEntity newBook) {
        BooksEntity oldBook = booksRepository.findById(id).orElseThrow(
                () -> {
                    throw new NotFoundException("Book not found");
                }
        );

        // mapstruct
        oldBook.setAuthor(newBook.getAuthor());
        oldBook.setDescription(newBook.getDescription());
        oldBook.setGenre(newBook.getGenre());
        oldBook.setImage(newBook.getImage());
        oldBook.setLanguage(newBook.getLanguage());
        oldBook.setPrice(newBook.getPrice());
        oldBook.setName(newBook.getName());
        oldBook.setLeftInStock(newBook.getLeftInStock());
        oldBook.setPageCount(newBook.getPageCount());
        oldBook.setPublisher(newBook.getPublisher());

        booksRepository.save(oldBook);
    }
}
