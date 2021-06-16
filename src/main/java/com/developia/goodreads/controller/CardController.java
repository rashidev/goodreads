package com.developia.goodreads.controller;

import com.developia.goodreads.dao.entity.CardsEntity;
import com.developia.goodreads.service.CardService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cards")
public class CardController {
    private CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("/new")
    public String getNewCardPage(Model model) {
        model.addAttribute("card", new CardsEntity());

        return "card";
    }

    @PostMapping
    public String addNewCard(@ModelAttribute("card") CardsEntity card, Authentication authentication) {
        cardService.addNewCard(card, authentication.getName());
        return "redirect:/orders/new";
    }
}
