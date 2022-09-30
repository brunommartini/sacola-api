package me.dio.sacola.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.dio.sacola.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
