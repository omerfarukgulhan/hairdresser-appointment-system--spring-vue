package com.ofg.hairdresser.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "hairdressers")
@Data
public class Hairdresser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String bio;

    private double rating;

    private int yearsOfExperience;

    @ElementCollection
    private List<String> specialties;

    @OneToMany(mappedBy = "hairdresser")
    private List<Review> reviews;

    @OneToMany(mappedBy = "hairdresser")
    private List<Treatment> treatments;
}