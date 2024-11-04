package com.example.springproject.DAO;

import com.example.springproject.Entities.Presence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PresenceRepository extends JpaRepository<Presence, Integer> {
}
