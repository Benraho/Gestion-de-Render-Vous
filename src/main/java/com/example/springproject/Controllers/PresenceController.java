package com.example.springproject.Controllers;

import com.example.springproject.Entities.Presence;
import com.example.springproject.Services.PresenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/presences")
public class PresenceController {
    @Autowired
    private PresenceService presenceService;

    @GetMapping
    public List<Presence> getAllPresences() {
        return presenceService.getAllPresences();
    }

    @GetMapping("/{id}")
    public Presence getPresenceById(@PathVariable Integer id) {
        return presenceService.getPresenceById(id);
    }

    @PostMapping
    public Presence savePresence(@RequestBody Presence presence) {
        return presenceService.savePresence(presence);
    }

    @DeleteMapping("/{id}")
    public void deletePresence(@PathVariable Integer id) {
        presenceService.deletePresence(id);
    }
}
