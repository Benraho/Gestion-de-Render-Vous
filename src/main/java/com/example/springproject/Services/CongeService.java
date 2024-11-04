package com.example.springproject.Services;

import com.example.springproject.DAO.CongeRepository;
import com.example.springproject.Entities.Conge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CongeService {
    @Autowired
    private CongeRepository congeRepository;

    public List<Conge> getAllConges() {
        return congeRepository.findAll();
    }

    public Conge getCongeById(Integer id) {
        return congeRepository.findById(id).orElse(null);
    }

    public Conge saveConge(Conge conge) {
        return congeRepository.save(conge);
    }

    public void deleteConge(Integer id) {
        congeRepository.deleteById(id);
    }
}
