package me.dio.sacola.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.dio.sacola.enumeration.FormaPagamento;
import me.dio.sacola.model.Item;
import me.dio.sacola.model.Restaurante;
import me.dio.sacola.model.Sacola;
import me.dio.sacola.repository.ProdutoRepository;
import me.dio.sacola.repository.SacolaRepository;
import me.dio.sacola.resource.dto.ExcluirItemDto;
import me.dio.sacola.resource.dto.ItemDto;
import me.dio.sacola.service.SacolaService;

@Service
@RequiredArgsConstructor
public class SacolaServiceImpl implements SacolaService {

	private final SacolaRepository sacolaRepository;
	private final ProdutoRepository produtoRepository;
	

	@Override
	public Item incluirItemNaSacola(ItemDto itemDto) {
		Sacola sacola = verSacola(itemDto.getIdSacola());

		if (sacola.isFechada()) {
			throw new RuntimeException("Esta sacola está fechada.");
		}

		Item itemParaSerInserido = Item.builder().quantidade(itemDto.getQuantidade()).sacola(sacola)
				.produto(produtoRepository.findById(itemDto.getProdutoId()).orElseThrow(() -> {
					throw new RuntimeException("Esse produto não existe.");
				})).build();

		List<Item> itensDaSacola = sacola.getItens();
		if (itensDaSacola.isEmpty()) {
			itensDaSacola.add(itemParaSerInserido);
		} else {
			Restaurante restauranteAtual = itensDaSacola.get(0).getProduto().getRestaurante();
			Restaurante restauranteDoItemParaAdicionar = itemParaSerInserido.getProduto().getRestaurante();
			if (restauranteAtual.equals(restauranteDoItemParaAdicionar)) {
				itensDaSacola.add(itemParaSerInserido);
			} else {
				throw new RuntimeException(
						"Não é possível adicionar produtos de restaurantes diferentes. Feche a sacola ou esvazie.");
			}
		}

		calcularValorDaSacola(sacola);
		sacolaRepository.save(sacola);
		return itemParaSerInserido;
	}

	@Override
	public Sacola verSacola(Long id) {
		return sacolaRepository.findById(id).orElseThrow(() -> {
			throw new RuntimeException("Essa sacola não existe.");
		});
	}

	@Override
	public Sacola fecharSacola(Long id, int numeroFormaPagamento) {
		Sacola sacola = verSacola(id);

		if (sacola.getItens().isEmpty()) {
			throw new RuntimeException("Inclua itens na sacola.");
		}

		FormaPagamento formaPagamento = numeroFormaPagamento == 0 ? FormaPagamento.DINHEIRO : FormaPagamento.MAQUINETA;

		sacola.setFormaPagamento(formaPagamento);
		sacola.setFechada(true);

		return sacolaRepository.save(sacola);
	}

	@Override
	public Item excluirItemDaSacola(ExcluirItemDto excluirItemDto) {
		Sacola sacola = verSacola(excluirItemDto.getIdSacola());
		
		if (sacola.isFechada()) {
			throw new RuntimeException("Esta sacola está fechada.");
		}

		Item itemParaSerExcluido = Item.builder().sacola(sacola)
				.produto(produtoRepository.findById(excluirItemDto.getProdutoId()).orElseThrow(() -> {
					throw new RuntimeException("Esse produto não existe.");
				})).build();
		
		List<Item> itensDaSacola = sacola.getItens();
		
		if(itensDaSacola.isEmpty()) {
			throw new RuntimeException("Não há nada para ser excluído.");
		} else {
			for(int i = 0; i < itensDaSacola.size(); i++) {
				if (itensDaSacola.get(i).getProduto().getId().equals(excluirItemDto.getProdutoId())) {
					itensDaSacola.remove(i);
					sacola.setItens(itensDaSacola);
					calcularValorDaSacola(sacola);
					sacolaRepository.save(sacola);
				} else {
					throw new RuntimeException("Não existe o produto na sacola para ser deletado.");
				}
			}
			
		}
		
		return itemParaSerExcluido;
	}

	@Override
	public void calcularValorDaSacola(Sacola sacola) {
		List<Double> valorDosItens = new ArrayList<>();
		List<Item> itensDaSacola = sacola.getItens();
		for (Item itemDaSacola : itensDaSacola) {
			double valorTotalItem = itemDaSacola.getProduto().getValorUnitario() * itemDaSacola.getQuantidade();
			valorDosItens.add(valorTotalItem);
		}

		Double valorTotalSacola = valorDosItens.stream()
				.mapToDouble(valorTotaldeCadaItem -> valorTotaldeCadaItem)
				.sum();

		sacola.setValorTotal(valorTotalSacola);
	}

}
