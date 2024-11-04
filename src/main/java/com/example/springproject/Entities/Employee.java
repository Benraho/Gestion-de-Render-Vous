package com.example.springproject.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nom;
    private String prenom;
    private String email;

    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;

    private String motDePasse;
    private int matricule;
    private String adresse;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @ManyToOne
    @JoinColumn(name = "salaire_id")
    private Salaire salaire;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "presence_id", referencedColumnName = "id")
    private Presence presence;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Appeles> appels;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<RendezVous> rendezVous;

    @ManyToOne
    @JoinColumn(name = "conge_id", nullable = true) // Allowing null values
    private Conge conge;
}
