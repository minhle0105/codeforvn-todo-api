package com.codeforvn.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private String priorityLevel;
}
