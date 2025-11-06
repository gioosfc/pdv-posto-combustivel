package com.br.pdvpostocombustivel.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(
        name = "acessos",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_acessos_usuario", columnNames = "usuario")
        }
)
public class Acesso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, length = 50, unique = true)
    private String usuario;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String senha;

    @NotBlank
    @Size(max = 20)
    @Column(nullable = false, length = 20)
    private String papel; // ADMIN ou OPERADOR

    /** Construtor JPA */
    public Acesso() {}

    public Acesso(String usuario, String senha, String papel) {
        this.usuario = usuario;
        this.senha = senha;
        this.papel = papel;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getPapel() { return papel; }
    public void setPapel(String papel) { this.papel = papel; }

}
