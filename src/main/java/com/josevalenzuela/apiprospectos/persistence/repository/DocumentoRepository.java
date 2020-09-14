package com.josevalenzuela.apiprospectos.persistence.repository;

import com.josevalenzuela.apiprospectos.domain.DocumentDomain;
import com.josevalenzuela.apiprospectos.domain.repository.DocumentDomainRespository;
import com.josevalenzuela.apiprospectos.persistence.crud.DocumentoCrudRepository;
import com.josevalenzuela.apiprospectos.persistence.entity.DocumentoEntity;
import com.josevalenzuela.apiprospectos.persistence.mapper.DocumentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DocumentoRepository implements DocumentDomainRespository {
    @Autowired
    DocumentoCrudRepository docsCrudRepository;
    @Autowired
    DocumentMapper mapper;

    @Override
    public List<DocumentDomain> getAllDocs() {
        return mapper.toDocuments((List<DocumentoEntity>) docsCrudRepository.findAll());
    }

    @Override
    public Optional<List<DocumentDomain>> getAllDocsByProspectID(int iD) {
        return Optional.of(mapper.toDocuments(docsCrudRepository.findByIdProspecto(iD)));
    }

    @Override
    public DocumentDomain saveDoc(DocumentDomain document) {
        return mapper.toDocument(docsCrudRepository.save(mapper.toDocumentoEntity(document)));
    }
}
