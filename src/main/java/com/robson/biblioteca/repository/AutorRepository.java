package com.robson.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.robson.biblioteca.model.Autor;

@Repository 
public interface AutorRepository extends JpaRepository<Autor, Integer>{

}
