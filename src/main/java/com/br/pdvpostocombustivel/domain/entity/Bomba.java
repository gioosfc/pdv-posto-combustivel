package com.br.pdvpostocombustivel.domain.entity;

import jakarta.persistence.*;

@Entity
public class Bomba {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String identificacao; // "Bomba 1", "Bomba 2", ...
    @ManyToOne
    private Produto produto; // produto atrelado à bomba (Gasolina, Etanol, Diesel)

    // getters/setters
}
