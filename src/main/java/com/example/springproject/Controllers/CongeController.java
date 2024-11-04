package com.example.springproject.Controllers;

import com.example.springproject.Entities.Conge;
import com.example.springproject.Services.CongeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conges")
public class CongeController {
    @Autowired
    private CongeService congeService;

    @GetMapping
    public List<Conge> getAllConges() {
        return congeService.getAllConges();
    }

    @GetMapping("/{id}")
    public Conge getCongeById(@PathVariable Integer id) {
        return congeService.getCongeById(id);
    }

    @PostMapping
    public Conge saveConge(@RequestBody Conge conge) {
        return congeService.saveConge(conge);
    }

    @DeleteMapping("/{id}")
    public void deleteConge(@PathVariable Integer id) {
        congeService.deleteConge(id);
    }
}
