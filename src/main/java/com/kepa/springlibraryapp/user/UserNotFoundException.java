package com.kepa.springlibraryapp.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Brak użytkownika o takim ID")
public class UserNotFoundException extends RuntimeException {
}
