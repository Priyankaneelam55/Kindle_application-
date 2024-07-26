package com.example.kindle.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private String publicationDate;
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}