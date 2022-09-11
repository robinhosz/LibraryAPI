package com.robson.biblioteca.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.robson.biblioteca.model.Livro;

@Repository 
public interface LivroRepository extends JpaRepository<Livro, Integer>{

	Optional<Livro> findByIsbn(Integer isbn);
}
