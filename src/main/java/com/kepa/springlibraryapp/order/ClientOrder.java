package com.kepa.springlibraryapp.order;

import com.kepa.springlibraryapp.book.Book;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class ClientOrder {

    private Order order;

    public ClientOrder() {
        clear();
    }

    Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    void add(Book book) {
        order.getBooks().add(book);
    }

    void clear() {
        order = new Order();
        order.setStatus(OrderStatus.NEW);
    }
}
