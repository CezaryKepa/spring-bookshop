package com.kepa.springlibraryapp.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {
    private BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookDto> findAll() {
        return bookRepository.findAll()
                .stream()
                .map(BookMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<BookDto> findById(Long id) {
        return bookRepository.findById(id).map(BookMapper::toDto);
    }

    public List<BookDto> findAllByNameOrAuthor(String text) {
       return bookRepository.findAllByNameOrAuthor(text)
               .stream()
               .map(BookMapper::toDto)
               .collect(Collectors.toList());

    }
}
