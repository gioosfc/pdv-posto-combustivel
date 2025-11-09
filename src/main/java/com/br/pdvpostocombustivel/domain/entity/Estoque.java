package com.br.pdvpostocombustivel.domain.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "estoque")
public class Estoque implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "produto_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Produto produto;

    @Column(nullable = false)
    private BigDecimal quantidade;

    public Estoque() {}

    public Estoque(Produto produto, BigDecimal quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public Long getId() { return id; }
    public Produto getProduto() { return produto; }
    public void setProduto(Produto produto) { this.produto = produto; }
    public BigDecimal getQuantidade() { return quantidade; }
    public void setQuantidade(BigDecimal quantidade) { this.quantidade = quantidade; }
}
