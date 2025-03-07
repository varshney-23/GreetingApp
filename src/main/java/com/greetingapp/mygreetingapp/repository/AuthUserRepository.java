package com.greetingapp.mygreetingapp.repository;

import com.greetingapp.mygreetingapp.model.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {
    @Query(value = "SELECT * FROM authuser WHERE EMAIL = :email", nativeQuery = true)
    AuthUser findByEmail(@Param("email") String email);
    AuthUser findByResetToken(String resetToken);
}
