package com.dio.sacola.resource.dto;

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

public class ItemDto {
    private Long produtoId;
    private int quantidade;
    private  Long idSacola;
}
