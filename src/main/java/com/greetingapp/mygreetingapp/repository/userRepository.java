package com.greetingapp.mygreetingapp.repository;

import com.greetingapp.mygreetingapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface userRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT * FROM AUTH_USER WHERE EMAIL = :email", nativeQuery = true)
    User findByEmail(@Param("email") String email);
    User findByResetToken(String resetToken);
}
