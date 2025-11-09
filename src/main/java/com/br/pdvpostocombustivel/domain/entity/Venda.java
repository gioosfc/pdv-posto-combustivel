package com.br.pdvpostocombustivel.domain.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
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
    private Date dataHora = new Date();

    @Column(length = 10)
    private String placa;

    @Column(length = 20)
    private String formaPagamento;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total = BigDecimal.ZERO;

    @ManyToOne(optional = true)
    @JoinColumn(name = "acesso_id")
    private Acesso acesso;

    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<VendaItem> itens = new ArrayList<>();

    // ===========================
    // === Getters e Setters ===
    // ===========================

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Date getDataHora() { return dataHora; }
    public void setDataHora(Date dataHora) { this.dataHora = dataHora; }

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public String getFormaPagamento() { return formaPagamento; }
    public void setFormaPagamento(String formaPagamento) { this.formaPagamento = formaPagamento; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public Acesso getAcesso() { return acesso; }
    public void setAcesso(Acesso acesso) { this.acesso = acesso; }

    public List<VendaItem> getItens() { return itens; }
    public void setItens(List<VendaItem> itens) { this.itens = itens; }

    @PrePersist
    public void prePersist() {
        if (this.dataHora == null) {
            this.dataHora = new Date();
        }
        if (this.total == null) {
            this.total = BigDecimal.ZERO;
        }
    }
}
