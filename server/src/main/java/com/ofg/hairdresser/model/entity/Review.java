package com.ofg.hairdresser.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@Data
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // The customer who wrote the review

    @ManyToOne
    @JoinColumn(name = "hairdresser_id", nullable = false)
    private Hairdresser hairdresser;

    private int rating;

    private String comment;

    private LocalDateTime createdAt;
}