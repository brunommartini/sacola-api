package me.dio.sacola.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.dio.sacola.model.Sacola;

@Repository
public interface SacolaRepository extends JpaRepository<Sacola, Long> {

}
