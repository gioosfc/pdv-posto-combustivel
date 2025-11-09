package com.br.pdvpostocombustivel.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "produto")
public class Produto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String referencia;

    private String marca;

    private String categoria;

    private String fornecedor;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Preco> precos = new ArrayList<>();

    public Produto() {}

    public Produto(String nome, String referencia, String marca, String categoria, String fornecedor) {
        this.nome = nome;
        this.referencia = referencia;
        this.marca = marca;
        this.categoria = categoria;
        this.fornecedor = fornecedor;
    }

    // 🔹 Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }

    @Transient
    @JsonIgnore
    public BigDecimal getPrecoAtual() {
        return precos.isEmpty() ? null : precos.get(0).getValor();
    }

    // helper
    public void addPreco(Preco preco) {
        preco.setProduto(this);
        this.precos.add(preco);
    }
}
