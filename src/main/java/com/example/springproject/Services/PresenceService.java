package com.example.springproject.Services;

import com.example.springproject.DAO.PresenceRepository;
import com.example.springproject.Entities.Presence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PresenceService {
    @Autowired
    private PresenceRepository presenceRepository;

    public List<Presence> getAllPresences() {
        return presenceRepository.findAll();
    }

    public Presence getPresenceById(Integer id) {
        return presenceRepository.findById(id).orElse(null);
    }

    public Presence savePresence(Presence presence) {
        return presenceRepository.save(presence);
    }

    public void deletePresence(Integer id) {
        presenceRepository.deleteById(id);
    }
}
