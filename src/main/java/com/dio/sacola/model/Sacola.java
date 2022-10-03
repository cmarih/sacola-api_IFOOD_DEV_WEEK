package com.dio.sacola.model;

import com.dio.sacola.enumeration.FormaPagamento;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Builder //Design Pattner - ajuda na criação do objeto, terá o padrão
@AllArgsConstructor //Anotação Lombok para criar os métodos construtores (reduzindo código).
@Data // Lombok cria todos os construtores Getters e Setters
@NoArgsConstructor //Cria um construtor vazio (requisito do Hibernate)
@JsonIgnoreProperties ({"hibernateLazyInitializer", "handler"}) // Ignora erros de atributo Lazy do Hibernate
@Entity
public class Sacola {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonIgnore
    private Cliente cliente;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Item> itens;
    private Double valorTotal;
    @Enumerated
    private FormaPagamento FormaPagamento;
    private boolean fechada;

}
