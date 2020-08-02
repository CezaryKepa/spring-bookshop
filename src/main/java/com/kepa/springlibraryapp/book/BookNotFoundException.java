package com.kepa.springlibraryapp.book;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Brak ksiażki o takim id")
public class BookNotFoundException extends RuntimeException {
}
