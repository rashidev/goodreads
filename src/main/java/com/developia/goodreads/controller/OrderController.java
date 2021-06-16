package com.developia.goodreads.controller;

import com.developia.goodreads.dao.entity.OrdersEntity;
import com.developia.goodreads.service.CardService;
import com.developia.goodreads.service.OrderService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {
    private OrderService orderService;
    private CardService cardService;

    public OrderController(OrderService orderService,
                           CardService cardService) {
        this.orderService = orderService;
        this.cardService = cardService;
    }

    @GetMapping
    public String getOrders(Model model, Authentication authentication) {
        List<OrdersEntity> orders = orderService.getOrders(authentication.getName());

        model.addAttribute("orders", orders);

        return "orders";
    }

    @GetMapping("/new")
    public String getOrderPage(Model model, Authentication authentication) {
        model.addAttribute("order", new OrdersEntity());
        model.addAttribute("cards", cardService.getCards(authentication.getName()));
        model.addAttribute("totalAmount", orderService.calculateOrderTotalAmount(authentication.getName()));

        return "order";
    }

    @PostMapping
    public String createOrder(@ModelAttribute("order") OrdersEntity order,
                              @ModelAttribute("cardNumber") String cardNumber,
                              Authentication authentication) {

        orderService.createOrder(authentication.getName(), order, cardNumber);

        return "redirect:/orders";
    }
}
