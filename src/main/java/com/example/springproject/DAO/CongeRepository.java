package com.example.springproject.DAO;

import com.example.springproject.Entities.Conge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CongeRepository extends JpaRepository<Conge, Integer> {
}
