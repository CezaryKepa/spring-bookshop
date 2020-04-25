package com.kepa.springlibraryapp.Controller;

import com.kepa.springlibraryapp.book.BookDto;
import com.kepa.springlibraryapp.book.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    private BookService bookService;

    @Autowired
    public HomeController(BookService bookService) {
        this.bookService = bookService;
    }
    @GetMapping("/")
    public String home(Model model,@RequestParam(value = "text",required = false) String text) {
        List<BookDto> books;
        if (text != null)
            books = bookService.findAllByNameOrAuthor(text);
        else
            books = bookService.findAll();

        model.addAttribute("books", books);
        return "index";
    }
}
