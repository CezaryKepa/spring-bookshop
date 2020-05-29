package com.kepa.springlibraryapp.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);
    @Query("select a from User a where a.email=:email")
    Optional<User> findByEmailOpt(String email);
    @Query("select a from User a where lower(a.firstname) like lower(concat('%', :search, '%')) " +
            "or lower(a.lastname) like lower(concat('%', :search, '%'))")
    List<User> findAllByNameOrLastName(@Param("search") String search);
}
