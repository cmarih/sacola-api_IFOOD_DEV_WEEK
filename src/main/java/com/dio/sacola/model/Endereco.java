package com.dio.sacola.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@AllArgsConstructor
@Builder
@Data
@Embeddable //Não cria a tabela endereço, porém vincula o endereço ao restaurante
@NoArgsConstructor
public class Endereco {
    private String cep;
    private String complemento;

}
