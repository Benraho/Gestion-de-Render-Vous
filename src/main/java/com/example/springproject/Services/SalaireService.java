package com.example.springproject.Services;

import com.example.springproject.DAO.SalaireRepository;
import com.example.springproject.Entities.Salaire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaireService {
    @Autowired
    private SalaireRepository salaireRepository;

    public List<Salaire> getAllSalaires() {
        return salaireRepository.findAll();
    }

    public Salaire getSalaireById(Integer id) {
        return salaireRepository.findById(id).orElse(null);
    }

    public Salaire saveSalaire(Salaire salaire) {
        return salaireRepository.save(salaire);
    }

    public void deleteSalaire(Integer id) {
        salaireRepository.deleteById(id);
    }
}
