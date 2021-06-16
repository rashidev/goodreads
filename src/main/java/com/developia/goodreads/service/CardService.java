package com.developia.goodreads.service;

import com.developia.goodreads.dao.entity.CardsEntity;
import com.developia.goodreads.dao.entity.UsersEntity;
import com.developia.goodreads.dao.repository.CardsRepository;
import com.developia.goodreads.dao.repository.UsersRepository;
import com.developia.goodreads.model.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CardService {
    private CardsRepository cardsRepository;
    private UsersRepository usersRepository;

    public CardService(CardsRepository cardsRepository,
                       UsersRepository usersRepository) {
        this.cardsRepository = cardsRepository;
        this.usersRepository = usersRepository;
    }

    public List<CardsEntity> getCards(String login) {
        UsersEntity user = usersRepository.findByLogin(login)
                .orElseThrow(
                        () -> {
                            throw new NotFoundException("User not found");
                        }
                );
        List<UsersEntity> users = new ArrayList<>();
        users.add(user);
        List<CardsEntity> cards = cardsRepository.findAllByUsersIn(users);
        return cards;
    }

    public void addNewCard(CardsEntity newCard, String login) {
        UsersEntity user = usersRepository.findByLogin(login)
                .orElseThrow(
                        () -> {
                            throw new NotFoundException("User not found");
                        }
                );
        CardsEntity card = cardsRepository.findByNumber(newCard.getNumber()).orElse(new CardsEntity());

        card.setNumber(newCard.getNumber());
        card.setCvv(newCard.getCvv());

        List<UsersEntity> users = card.getUsers();

        users.add(user);
        card.setUsers(users);

        cardsRepository.save(card);
    }
}
