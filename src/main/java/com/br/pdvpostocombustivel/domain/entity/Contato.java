package com.br.pdvpostocombustivel.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "contato")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Contato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome não pode estar em branco")
    private String nome;

    @NotBlank(message = "Email não pode estar em branco")
    @Email(message = "Email deve ser válido")
    private String email;

    private String telefone;

    private String endereco;
}
