package com.example.springproject.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    private String nom;
    private String prenom;
    private String email;
    private String image; // Image encodée en base64
    private String motDePass;
    private int matricule;
    private Integer salaireId;
    private Integer presenceId;
    private Integer congeId;

    // Getters et Setters
}
