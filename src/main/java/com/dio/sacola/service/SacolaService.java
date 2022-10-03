package com.dio.sacola.service;

import com.dio.sacola.model.Item;
import com.dio.sacola.model.Sacola;
import com.dio.sacola.resource.dto.ItemDto;

public interface SacolaService {
    Item incluirItemSacola (ItemDto itemDto);
    Sacola verSacola(Long id);
    Sacola fecharSacola(Long id, int formaPagamento);

}
