package com.dio.sacola.service.impl;

import com.dio.sacola.enumeration.FormaPagamento;
import com.dio.sacola.model.Item;
import com.dio.sacola.model.Restaurante;
import com.dio.sacola.model.Sacola;
import com.dio.sacola.repository.ItemRepository;
import com.dio.sacola.repository.ProdutoRepository;
import com.dio.sacola.repository.SacolaRepository;
import com.dio.sacola.resource.dto.ItemDto;
import com.dio.sacola.service.SacolaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SacolaServiceImpl implements SacolaService {
    private final SacolaRepository sacolaRepository;
    private  final ProdutoRepository produtoRepository;

    private final ItemRepository itemRepository;
    @Override
    public Item incluirItemSacola(ItemDto itemDto) {
        Sacola sacola = verSacola(itemDto.getIdSacola());

        if (sacola.isFechada()) {
            throw new RuntimeException(
                    "Esta sacola está fechada."
            );

        }

        Item inserirItem = Item.builder()
                .quantidade(itemDto.getQuantidade())
                .sacola(sacola)
                .produto(produtoRepository.findById(itemDto.getProdutoId()).orElseThrow(
                        () -> {
                            throw new RuntimeException(
                                    "Esse produto não existe!"
                            );
                        }
                ))
                .build();

        List <Item> itensSacola = sacola.getItens();
            if(itensSacola.isEmpty()){
                itensSacola.add(inserirItem);
            } else {
                Restaurante restauranteAtual = itensSacola.get(0).getProduto().getRestaurante();

                Restaurante restauranteAddItem = inserirItem.getProduto().getRestaurante();

                if (restauranteAtual.equals(restauranteAddItem)){
                    itensSacola.add(inserirItem);
                } else {
                    throw new RuntimeException(
                            "Não é possível adicionar produtos de restaurantes diferentes." +
                                    "Esvazie a sacola atual ou continue no mesmo restaurante."
                    );
                }
            }
            //calcular a quantidade de itens com o valor de cada
            List<Double> valorDosItens = new ArrayList<>();

            for (Item itemDaSacola : itensSacola) {
                double valorTotalItem =
                        itemDaSacola.getProduto().getValorUnitario() * itemDaSacola.getQuantidade();
                valorDosItens.add(valorTotalItem);
            }

            //soma o valor dos itens e gera o valor total
        double valorTotalSacola = valorDosItens.stream()
                .mapToDouble(valorTotalDeCadaItem -> valorTotalDeCadaItem)
                .sum();

        sacola.setValorTotal(valorTotalSacola);
        sacolaRepository.save(sacola);
            return inserirItem;
    }

    @Override
    public Sacola verSacola(Long id) {
        return sacolaRepository.findById(id).orElseThrow(
                () -> {
                    throw new RuntimeException(
                            "Essa sacola não existe!"
                    );
                }
        );
    }

    @Override
    public Sacola fecharSacola(Long id, int formaPagamento) {
        Sacola sacola = verSacola(id);

        if (sacola.getItens().isEmpty()){
            throw new RuntimeException(
                    "Inclua itens na sacola!"
            );
        }
        FormaPagamento pagamento =
        formaPagamento == 0 ? FormaPagamento.DINHEIRO : FormaPagamento.MAQUINETA;

        sacola.setFormaPagamento(pagamento);
        sacola.setFechada(true);
        return sacolaRepository.save(sacola);

    }
}
