package com.br.pdvpostocombustivel.domain.entity;

import jakarta.persistence.*;
import java.io.Serializable;

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

    // ✅ Cada produto tem um custo (valor de compra)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "custo_id")
    private Custo custo;

    // ✅ Cada produto tem um preço de venda
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "preco_id")
    private Preco preco;

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

    public Custo getCusto() {
        return custo;
    }

    public void setCusto(Custo custo) {
        this.custo = custo;
    }

    public Preco getPreco() {
        return preco;
    }

    public void setPreco(Preco preco) {
        this.preco = preco;
    }
}
