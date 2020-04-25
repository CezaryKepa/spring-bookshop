package com.kepa.springlibraryapp.order;

import com.kepa.springlibraryapp.book.Book;
import com.kepa.springlibraryapp.book.BookRepository;
import com.kepa.springlibraryapp.common.Message;
import com.kepa.springlibraryapp.user.User;
import com.kepa.springlibraryapp.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Optional;

@Controller
public class OrderController {
    private ClientOrder clientOrder;
    private BookRepository bookRepository;
    private OrderRepository orderRepository;
    private UserRepository userRepository;

    @Autowired
    public OrderController(ClientOrder clientOrder, BookRepository bookRepository, OrderRepository orderRepository, UserRepository userRepository) {
        this.clientOrder = clientOrder;
        this.bookRepository = bookRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }


    @GetMapping("/order-add")
    public String addBookToOrder(@RequestParam Long bookId, Model model) {
        Optional<Book> book = bookRepository.findById(bookId);

        book.ifPresent(clientOrder::add);
        if(book.isPresent()) {
            int stock=book.get().getStock();
            book.get().setStock(stock-1);
            bookRepository.save(book.get());
            model.addAttribute("message", new Message("Dodano", "Do zamówienia dodano: " + book.get().getName()));
        } else {
            model.addAttribute("message", new Message("Nie dodano", "Porano błędne id z menu: " + bookId));
        }
        return "message";
    }
    @GetMapping("/order")
    public String getCurrentOrder(Model model) {
        model.addAttribute("order", clientOrder.getOrder());
        model.addAttribute("sum", clientOrder
                .getOrder()
                .getBooks().stream()
                .mapToDouble(Book::getPrice)
                .sum());
        return "order";
    }
    @PostMapping("/order-finalize")
    public String proceedOrder(@RequestParam String address, @RequestParam String telephone, Model model, Authentication authentication) {
        String email = null;
        if (authentication != null) {
            email=authentication.getName();
        }
        System.out.println(email);
        User user=userRepository.findByEmail(email);
        Order order = clientOrder.getOrder();
        order.setAddress(address);
        order.setTelephone(telephone);

        order.setUser(user);
        orderRepository.save(order);
        clientOrder.clear();
        model.addAttribute("message", new Message("Dziękujemy", "Zamówienie przekazane do realizacji"));
        return "message";
    }
}
