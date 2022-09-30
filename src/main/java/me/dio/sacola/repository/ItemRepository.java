package me.dio.sacola.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.dio.sacola.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

}
