package com.example.springproject.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RendezVous {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nomDeClient;
    private String prenomDeClient;
    private int tel;
    private String nomDentreprise;
    private String adresse;
    private String ville;
    private int zip;
    private int numeroFiscale;
    private String commentaire;
    private Date dateDeRendezVous;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Enumerated(EnumType.STRING)
    private Status status;
}
