package com.example.springproject.Entities;

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
public class Admin {
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

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL)
    private List<Employee> employees;
}
