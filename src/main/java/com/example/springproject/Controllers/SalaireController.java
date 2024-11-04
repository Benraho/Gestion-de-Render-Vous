package com.example.springproject.Controllers;

import com.example.springproject.Entities.Salaire;
import com.example.springproject.Services.SalaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salaires")
public class SalaireController {
    @Autowired
    private SalaireService salaireService;

    @GetMapping
    public List<Salaire> getAllSalaires() {
        return salaireService.getAllSalaires();
    }

    @GetMapping("/{id}")
    public Salaire getSalaireById(@PathVariable Integer id) {
        return salaireService.getSalaireById(id);
    }

    @PostMapping
    public Salaire saveSalaire(@RequestBody Salaire salaire) {
        return salaireService.saveSalaire(salaire);
    }

    @DeleteMapping("/{id}")
    public void deleteSalaire(@PathVariable Integer id) {
        salaireService.deleteSalaire(id);
    }
}
