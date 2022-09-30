package me.dio.sacola.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.dio.sacola.model.Restaurante;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {

}
