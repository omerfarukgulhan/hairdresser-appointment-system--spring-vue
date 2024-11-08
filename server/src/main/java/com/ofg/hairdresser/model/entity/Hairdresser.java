package com.ofg.hairdresser.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hairdressers", uniqueConstraints = @UniqueConstraint(columnNames = "user_id"))
@Data
public class Hairdresser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String bio;

    @Column(nullable = false)
    private int numberOfReviews = 0;

    @Column(nullable = false)
    private double averageRating = 0.0;

    @Column(nullable = false)
    private int yearsOfExperience;

    @Column(nullable = false)
    private boolean active = false;

    @ElementCollection
    @Column(nullable = false)
    private List<String> specialties;

    @OneToMany(mappedBy = "hairdresser")
    private List<Review> reviews;

    @OneToMany(mappedBy = "hairdresser", fetch = FetchType.EAGER)
    private List<Treatment> treatments;

    private String mainPhoto  = "default.png";

    @ElementCollection
    @CollectionTable(name = "hairdresser_side_photos", joinColumns = @JoinColumn(name = "hairdresser_id"))
    @Column(name = "side_photo")
    private List<String> sidePhotos = new ArrayList<>();
}