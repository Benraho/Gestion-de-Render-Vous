package com.example.springproject.Services;

import com.example.springproject.DAO.RendezVousRepository;
import com.example.springproject.Entities.RendezVous;
import com.example.springproject.Entities.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RendezVousService {
    @Autowired
    private RendezVousRepository rendezVousRepository;

    public RendezVous saveRendezVous(RendezVous rendezVous) {
        return rendezVousRepository.save(rendezVous);
    }

    public List<RendezVous> getAllRendezVous() {
        return rendezVousRepository.findAll();
    }

    public RendezVous getRendezVousById(Integer id) {
        return rendezVousRepository.findById(id).orElse(null);
    }

    public void deleteRendezVous(Integer id) {
        rendezVousRepository.deleteById(id);
    }

    public List<RendezVous> getRendezVousByEmployeeId(Integer employeeId) {
        return rendezVousRepository.findByEmployeeId(employeeId);
    }

    public List<RendezVous> getRendezVousByAdminIdAndStatus(Integer adminId, Status status) {
        return rendezVousRepository.findByAdminIdAndStatus(adminId, status);
    }

    public Long countRendezVousByEmployeeIdAndStatus(Integer employeeId, Status status) {
        return rendezVousRepository.countByEmployeeIdAndStatus(employeeId, status);
    }

    public Long countAllRendezVousByAdminId(Integer adminId) {
        return rendezVousRepository.countAllRendezVousByAdminId(adminId);
    }

    public List<RendezVous> getRendezVousByEmployeeIdAndStatus(Integer adminId, Integer employeeId, Status status) {
        return rendezVousRepository.findByEmployeeIdAndStatus(adminId, employeeId, status);
    }

    public Long countRendezVousByAdminIdAndStatus(Integer adminId, Status status) {
        return rendezVousRepository.countByAdminIdAndStatus(adminId, status);
    }

    public Long countTotalRendezVousByEmployeeIdAndAdminId(Integer employeeId, Integer adminId) {
        return rendezVousRepository.countTotalRendezVousByEmployeeIdAndAdminId(employeeId, adminId);
    }


}
