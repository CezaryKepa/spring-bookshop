package com.kepa.springlibraryapp.verification;

import com.kepa.springlibraryapp.verification.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token,Long> {
    Optional<Token> findByValue(String value);
}
