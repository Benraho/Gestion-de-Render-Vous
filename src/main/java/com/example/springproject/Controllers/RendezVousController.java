package com.example.springproject.Controllers;

import com.example.springproject.Entities.RendezVous;
import com.example.springproject.Services.RendezVousService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rendezvous")
public class RendezVousController {
    @Autowired
    private RendezVousService rendezVousService;

    @GetMapping
    public List<RendezVous> getAllRendezVous() {
        return rendezVousService.getAllRendezVous();
    }

    @GetMapping("/{id}")
    public RendezVous getRendezVousById(@PathVariable Integer id) {
        return rendezVousService.getRendezVousById(id);
    }

    @PostMapping
    public RendezVous saveRendezVous(@RequestBody RendezVous rendezVous) {
        return rendezVousService.saveRendezVous(rendezVous);
    }

    @DeleteMapping("/{id}")
    public void deleteRendezVous(@PathVariable Integer id) {
        rendezVousService.deleteRendezVous(id);
    }
}
