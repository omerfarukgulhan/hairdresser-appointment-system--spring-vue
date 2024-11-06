package com.ofg.hairdresser.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "treatments", uniqueConstraints = @UniqueConstraint(columnNames = {"hairdresser_id", "name"}))
@Data
public class Treatment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hairdresser_id", nullable = false)
    private Hairdresser hairdresser;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private int duration;
}