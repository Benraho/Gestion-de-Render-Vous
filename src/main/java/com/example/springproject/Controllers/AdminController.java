package com.example.springproject.Controllers;

import com.example.springproject.DTO.EmployeeDTO;
import com.example.springproject.Entities.*;
import com.example.springproject.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/admins")
public class AdminController {
    private static final Logger logger = Logger.getLogger(AdminController.class.getName());

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private RendezVousService rendezVousService;

    @Autowired
    private PresenceService presenceService;

    @Autowired
    private SalaireService salaireService;

//    @PostMapping(consumes = "application/json", produces = "application/json")
//    public Admin addAdmin(@RequestBody Admin admin) {
//        return adminService.saveAdmin(admin);
//    }
//
//    @GetMapping(produces = "application/json")
//    public List<Admin> getAllAdmins() {
//        return adminService.getAllAdmins();
//    }
//
//    @GetMapping(value = "/{id}", produces = "application/json")
//    public Admin getAdminById(@PathVariable Integer id) {
//        return adminService.getAdminById(id);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteAdmin(@PathVariable Integer id) {
//        adminService.deleteAdmin(id);
//    }
//
//
//    @GetMapping(value = "/{adminId}/rendezvous/count", produces = "application/json")
//    public Long countAllRendezVousByAdminId(@PathVariable Integer adminId) {
//        return rendezVousService.countAllRendezVousByAdminId(adminId);
//    }
//
//    @GetMapping(value = "/{adminId}/rendezvous/status/{status}", produces = "application/json")
//    public List<RendezVous> getRendezVousByAdminIdAndStatus(
//            @PathVariable Integer adminId,
//            @PathVariable Status status) {
//        return rendezVousService.getRendezVousByAdminIdAndStatus(adminId, status);
//    }
//
//    @GetMapping(value = "/{adminId}/rendezvous/count/status/{status}", produces = "application/json")
//    public Long countRendezVousByAdminIdAndStatus(
//            @PathVariable Integer adminId,
//            @PathVariable Status status) {
//        logger.info("Counting RendezVous for adminId: " + adminId + " with status: " + status);
//        return rendezVousService.countRendezVousByAdminIdAndStatus(adminId, status);
//    }
//
//    @GetMapping(value = "/{adminId}/employees/{employeeId}/rendezvous/status/{status}", produces = "application/json")
//    public List<RendezVous> getRendezVousByEmployeeIdAndStatus(
//            @PathVariable Integer adminId,
//            @PathVariable Integer employeeId,
//            @PathVariable Status status) {
//        logger.info("Fetching RendezVous for employeeId: " + employeeId + " under adminId: " + adminId + " with status: " + status);
//        return rendezVousService.getRendezVousByEmployeeIdAndStatus(adminId, employeeId, status);
//    }
//
//    @GetMapping(value = "/{adminId}/employees/{employeeId}/rendezvous/count/status/{status}", produces = "application/json")
//    public Long countRendezVousByEmployeeIdAndStatus(
//            @PathVariable Integer adminId,
//            @PathVariable Integer employeeId,
//            @PathVariable Status status) {
//        logger.info("Counting RendezVous for employeeId: " + employeeId + " under adminId: " + adminId + " with status: " + status);
//        return rendezVousService.countRendezVousByEmployeeIdAndStatus(employeeId, status);
//    }
//
//    @GetMapping(value = "/{adminId}/employees/{employeeId}/rendezvous/total", produces = "application/json")
//    public Long getTotalRendezVousByEmployee(
//            @PathVariable Integer adminId,
//            @PathVariable Integer employeeId) {
//        logger.info("Fetching total RendezVous for employeeId: " + employeeId + " under adminId: " + adminId);
//        return rendezVousService.countTotalRendezVousByEmployeeIdAndAdminId(employeeId, adminId);
//    }
//
//    @GetMapping(value = "/{adminId}/employees/{employeeId}/rendezvous", produces = "application/json")
//    public List<RendezVous> getRendezVousByEmployeeId(@PathVariable Integer adminId,
//                                                      @PathVariable Integer employeeId) {
//        logger.info("Fetching RendezVous for employeeId: " + employeeId + " under adminId: " + adminId);
//        return rendezVousService.getRendezVousByEmployeeId(employeeId);
//    }



//
//    @DeleteMapping(value = "/{adminId}/employees/{employeeId}")
//    public void deleteEmployee(@PathVariable Integer adminId, @PathVariable Integer employeeId) {
//        Employee employee = employeeService.getEmployeeById(employeeId);
//        if (employee != null && employee.getAdmin().getId().equals(adminId)) {
//            employeeService.deleteEmployee(employeeId);
//        } else {
//            throw new RuntimeException("Employee not found or does not belong to Admin with ID: " + adminId);
//        }
//    }




@PostMapping(value = "/{adminId}/employees", consumes = "application/json", produces = "application/json")
    public Employee addEmployee(
            @PathVariable Integer adminId,
            @RequestBody EmployeeDTO employeeDTO) throws IOException {

        Admin admin = adminService.getAdminById(adminId);
        if (admin == null) {
            throw new RuntimeException("Admin not found with ID: " + adminId);
        }

        Presence presence = presenceService.getPresenceById(employeeDTO.getPresenceId());
        if (presence == null) {
            throw new RuntimeException("Presence not found with ID: " + employeeDTO.getPresenceId());
        }

        Salaire salaire = salaireService.getSalaireById(employeeDTO.getSalaireId());
        if (salaire == null) {
            throw new RuntimeException("Salaire not found with ID: " + employeeDTO.getSalaireId());
        }

        // Décoder l'image encodée en base64
        byte[] imageBytes = Base64.getDecoder().decode(employeeDTO.getImage().split(",")[1]);

        Employee employee = new Employee();
        employee.setAdmin(admin);
        employee.setNom(employeeDTO.getNom());
        employee.setPrenom(employeeDTO.getPrenom());
        employee.setEmail(employeeDTO.getEmail());
        employee.setImage(imageBytes);
        employee.setMotDePasse(employeeDTO.getMotDePass());
        employee.setMatricule(employeeDTO.getMatricule());
        employee.setPresence(presence);
        employee.setSalaire(salaire);

        return employeeService.saveEmployee(employee);
    }

//    // Méthode pour récupérer la liste des employés avec leurs images
//    @GetMapping(value = "/{adminId}/employees-with-images", produces = "application/json")
//    public List<Employee> getEmployeesWithImagesByAdminId(@PathVariable Integer adminId) {
//        Admin admin = adminService.getAdminById(adminId);
//        if (admin == null) {
//            throw new RuntimeException("Admin not found with ID: " + adminId);
//        }
//        return employeeService.getEmployeesByAdminId(adminId);
//    }




}
