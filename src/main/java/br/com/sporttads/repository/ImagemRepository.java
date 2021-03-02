package br.com.sporttads.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.sporttads.model.ImagemModel;

public interface ImagemRepository extends JpaRepository<ImagemModel, Integer> {

}
