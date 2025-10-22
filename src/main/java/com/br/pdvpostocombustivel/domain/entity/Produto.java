package com.br.pdvpostocombustivel.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(
        name = "produtos",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_produtos_referencia", columnNames = "referencia")
        }
)
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String nome;

    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, length = 50, unique = true)
    private String referencia;

    @Size(max = 50)
    @Column(length = 50)
    private String marca;

    @Size(max = 50)
    @Column(length = 50)
    private String categoria;

    @Size(max = 100)
    @Column(length = 100)
    private String fornecedor;

    /** Construtor JPA */
    public Produto() {}

    public Produto(String nome, String referencia, String marca, String categoria, String fornecedor) {
        this.nome = nome;
        this.referencia = referencia;
        this.marca = marca;
        this.categoria = categoria;
        this.fornecedor = fornecedor;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getReferencia() { return referencia; }
    public void setReferencia(String referencia) { this.referencia = referencia; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public String getFornecedor() { return fornecedor; }
    public void setFornecedor(String fornecedor) { this.fornecedor = fornecedor; }
}
