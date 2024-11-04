package com.example.springproject.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Presence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int nbrDeJour;

    @OneToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
