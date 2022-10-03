package com.dio.sacola.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder //Design Pattner - ajuda na criação do objeto, terá o padrão
@AllArgsConstructor //Anotação Lombok para criar os métodos construtores (reduzindo código).
@Data // Lombok cria todos os construtores Getters e Setters
@NoArgsConstructor //Cria um construtor vazio para criar um objeto (requisito para o Hibernate)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Ignora erros de atributo Lazy do Hibernate
@Entity //Converte a classe em tabela para o banco de dados

public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    private Produto produto;
    private int quantidade;
    @ManyToOne
    @JsonIgnore
    private Sacola sacola;
}
