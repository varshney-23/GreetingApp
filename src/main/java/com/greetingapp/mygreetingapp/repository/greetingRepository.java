package com.greetingapp.mygreetingapp.repository;

import com.greetingapp.mygreetingapp.model.greetingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface greetingRepository extends JpaRepository<greetingEntity, Long> {
}
