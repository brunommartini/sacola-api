package me.dio.sacola.resource.dto;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@Embeddable
@NoArgsConstructor
public class ExcluirItemDto {
	
	private Long produtoId;
	private Long idSacola;

}
