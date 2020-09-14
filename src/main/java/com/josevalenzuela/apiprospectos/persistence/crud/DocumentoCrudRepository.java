package com.josevalenzuela.apiprospectos.persistence.crud;

import com.josevalenzuela.apiprospectos.persistence.entity.DocumentoEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DocumentoCrudRepository extends CrudRepository<DocumentoEntity, Integer> {
    List<DocumentoEntity> findByIdProspecto(int idProspecto);
}
