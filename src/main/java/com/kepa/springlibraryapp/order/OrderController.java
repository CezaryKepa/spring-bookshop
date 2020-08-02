package com.kepa.springlibraryapp.order;

import com.kepa.springlibraryapp.book.Book;
import com.kepa.springlibraryapp.common.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import javax.validation.Valid;
import java.util.*;

@Controller
@Transactional
public class OrderController {
    private OrderService orderService;
    private ClientOrder clientOrder;

    @Autowired
    public OrderController(OrderService orderService, ClientOrder clientOrder) {
        this.orderService = orderService;
        this.clientOrder = clientOrder;
    }

    @GetMapping("/order-add")
    public String addBookToOrder(@RequestParam Long bookId, Model model) {
        Optional<Book> book = orderService.addBookToOrder(bookId);

        if (book.isPresent())
            model.addAttribute("message", new Message("Dodano", "Do zamówienia dodano: " + book.get().getName()));
        else
            model.addAttribute("message", new Message("Nie dodano", "Porano błędne id z menu: " + bookId));

        return "message";
    }

    @GetMapping("/order-delete")
    public String deleteBookFromOrder(@RequestParam Long bookIndex, Model model) {
        orderService.deleteBookFromOrder(bookIndex);

        model.addAttribute("order", clientOrder.getOrder());
        model.addAttribute("sum", orderService.sumOrderCost());
        model.addAttribute("orderDetails", new OrderDetails());

        return "orderView";
    }

    @GetMapping("/order")
    public String getCurrentOrder(Model model) {
        model.addAttribute("order", clientOrder.getOrder());
        model.addAttribute("sum", orderService.sumOrderCost());
        model.addAttribute("orderDetails", new OrderDetails());
        return "orderView";
    }

    @PostMapping("/order-finalize")
    public String proceedOrder(Model model, @Valid OrderDetails orderDetails, BindingResult bindingResult, Authentication authentication) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("order", clientOrder.getOrder());
            model.addAttribute("sum", orderService.sumOrderCost());
            return "orderView";
        }

        orderService.proceedOrder(orderDetails, authentication);

        model.addAttribute("message", new Message("Dziękujemy", "Zamówienie przekazane do realizacji"));
        return "message";
    }

}
