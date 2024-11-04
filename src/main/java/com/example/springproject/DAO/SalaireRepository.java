package com.example.springproject.DAO;

import com.example.springproject.Entities.Salaire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaireRepository extends JpaRepository<Salaire, Integer> {
}
