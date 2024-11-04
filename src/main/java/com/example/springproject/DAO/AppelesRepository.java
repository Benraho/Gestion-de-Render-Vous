package com.example.springproject.DAO;

import com.example.springproject.Entities.Appeles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppelesRepository extends JpaRepository<Appeles, Integer> {
}
