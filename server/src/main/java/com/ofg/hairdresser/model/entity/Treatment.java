package com.ofg.hairdresser.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "treatments")
@Data
public class Treatment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hairdresser_id", nullable = false)
    private Hairdresser hairdresser;

    private String name;

    private String description;

    private double price;

    private int duration;
}