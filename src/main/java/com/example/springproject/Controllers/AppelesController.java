package com.example.springproject.Controllers;

import com.example.springproject.Entities.Appeles;
import com.example.springproject.Services.AppelesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appeles")
public class AppelesController {
    @Autowired
    private AppelesService appelesService;

    @GetMapping
    public List<Appeles> getAllAppeles() {
        return appelesService.getAllAppeles();
    }

    @GetMapping("/{id}")
    public Appeles getAppelesById(@PathVariable Integer id) {
        return appelesService.getAppelesById(id);
    }

    @PostMapping
    public Appeles saveAppeles(@RequestBody Appeles appeles) {
        return appelesService.saveAppeles(appeles);
    }

    @DeleteMapping("/{id}")
    public void deleteAppeles(@PathVariable Integer id) {
        appelesService.deleteAppeles(id);
    }
}
