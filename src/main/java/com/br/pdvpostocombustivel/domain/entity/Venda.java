package com.br.pdvpostocombustivel.domain.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "vendas")
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date dataHora = new Date(); // data e hora da venda

    @Column(length = 10)
    private String placa; // opcional, placa do veículo

    @Column(length = 20)
    private String formaPagamento; // DINHEIRO, PIX, CARTAO etc.

    @Column(precision = 10, scale = 2)
    private BigDecimal total;

    // 🔗 Relacionamento com o operador (usuário logado)
    @ManyToOne
    @JoinColumn(name = "acesso_id")
    private Acesso acesso;

    // 🔗 Itens da venda
    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VendaItem> itens;

    // ===========================
    // === Getters e Setters ===
    // ===========================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Acesso getAcesso() {
        return acesso;
    }

    public void setAcesso(Acesso acesso) {
        this.acesso = acesso;
    }

    public List<VendaItem> getItens() {
        return itens;
    }

    public void setItens(List<VendaItem> itens) {
        this.itens = itens;
    }

    // ===========================
    // === Métodos auxiliares ===
    // ===========================

    @PrePersist
    public void prePersist() {
        if (this.dataHora == null) {
            this.dataHora = new Date();
        }
    }
}
