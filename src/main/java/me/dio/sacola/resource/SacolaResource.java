package me.dio.sacola.resource;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import me.dio.sacola.model.Item;
import me.dio.sacola.model.Sacola;
import me.dio.sacola.resource.dto.ExcluirItemDto;
import me.dio.sacola.resource.dto.ItemDto;
import me.dio.sacola.service.SacolaService;

@Api(value = "/ifood-devweek/sacolas")
@RestController
@RequestMapping("/ifood-devweek/sacolas")
@RequiredArgsConstructor
public class SacolaResource {

	private final SacolaService sacolaService;

	@ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping
	public Item incluirItemNaSacola(@RequestBody ItemDto itemDto) {
		return sacolaService.incluirItemNaSacola(itemDto);
	}

	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping("/{id}")
	public Sacola verSacola(@PathVariable("id") Long id) {
		return sacolaService.verSacola(id);
	}
	
	@ResponseStatus(code = HttpStatus.OK)
	@PatchMapping("/fecharSacola/{sacolaId}")
	public Sacola fecharSacola(@PathVariable("sacolaId") Long sacolaId, 
			@RequestParam("formaPagamento") int formaPagamento) {
		return sacolaService.fecharSacola(sacolaId, formaPagamento);
		
	}
	
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	@DeleteMapping("/excluirItemDaSacola")
	public Item excluirItemDaSacola(@RequestBody ExcluirItemDto excluirItemDto) {
		return sacolaService.excluirItemDaSacola(excluirItemDto);
	}

}
