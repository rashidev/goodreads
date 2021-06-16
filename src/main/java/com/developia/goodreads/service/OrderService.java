package com.developia.goodreads.service;

import com.developia.goodreads.dao.entity.BooksEntity;
import com.developia.goodreads.dao.entity.BucketsEntity;
import com.developia.goodreads.dao.entity.CardsEntity;
import com.developia.goodreads.dao.entity.OrdersEntity;
import com.developia.goodreads.dao.entity.UsersEntity;
import com.developia.goodreads.dao.repository.BucketsRepository;
import com.developia.goodreads.dao.repository.CardsRepository;
import com.developia.goodreads.dao.repository.OrdersRepository;
import com.developia.goodreads.dao.repository.UsersRepository;
import com.developia.goodreads.model.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    private OrdersRepository ordersRepository;
    private UsersRepository usersRepository;
    private BucketsRepository bucketsRepository;
    private CardsRepository cardsRepository;

    public OrderService(OrdersRepository ordersRepository,
                        UsersRepository usersRepository,
                        BucketsRepository bucketsRepository,
                        CardsRepository cardsRepository) {
        this.ordersRepository = ordersRepository;
        this.usersRepository = usersRepository;
        this.bucketsRepository = bucketsRepository;
        this.cardsRepository = cardsRepository;
    }

    public List<OrdersEntity> getOrders(String login) {
        UsersEntity user = usersRepository.findByLogin(login)
                .orElseThrow(
                        () -> {
                            throw new NotFoundException("User not found");
                        }
                );

        List<OrdersEntity> orders = ordersRepository.findAllByUserId(user.getId());

        return orders;
    }

    public BigDecimal calculateOrderTotalAmount(String login) {
        UsersEntity user = usersRepository.findByLogin(login)
                .orElseThrow(
                        () -> {
                            throw new NotFoundException("User not found");
                        }
                );

        BucketsEntity bucket = bucketsRepository.findByUserId(user.getId())
                .orElseThrow(
                        () -> {
                            throw new NotFoundException("Bucket not found");
                        }
                );

        List<BooksEntity> books = bucket.getBooks();
        BigDecimal amount = BigDecimal.ZERO;
        for (BooksEntity book : books) {
            amount = amount.add(book.getPrice()); // double olsaydi amount = amount + book.getPrice();
        }

        return amount;
    }

    public void createOrder(String login, OrdersEntity order, String cardNumber) {
        UsersEntity user = usersRepository.findByLogin(login)
                .orElseThrow(
                        () -> {
                            throw new NotFoundException("User not found");
                        }
                );

        BucketsEntity bucket = bucketsRepository.findByUserId(user.getId())
                .orElseThrow(
                        () -> {
                            throw new NotFoundException("Bucket not found");
                        }
                );

        List<BooksEntity> books = bucket.getBooks();
        BigDecimal amount = BigDecimal.ZERO;
        for (BooksEntity book : books) {
            amount = amount.add(book.getPrice()); // double olsaydi amount = amount + book.getPrice();
        }

        order.setTotalAmount(amount);
        order.setUserId(user.getId());
        order.setDate(LocalDateTime.now());
        CardsEntity card = cardsRepository.findByNumber(cardNumber)
                .orElseThrow(() -> {
                    throw new NotFoundException("Card not found");
                });
        order.setCard(card);

        ordersRepository.save(order);
    }
}
