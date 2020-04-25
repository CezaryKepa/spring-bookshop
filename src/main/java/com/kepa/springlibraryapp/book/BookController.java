package com.kepa.springlibraryapp.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class BookController {
    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/book")
    public String book(@RequestParam Long bookId, Model model) {
        System.out.println(bookId);
        Optional<BookDto> book= bookService.findById(bookId);
        model.addAttribute("book", book.get());
        return "book";
    }
}