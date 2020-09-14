package com.josevalenzuela.apiprospectos.persistence.repository;

import com.josevalenzuela.apiprospectos.domain.DocumentDomain;
import com.josevalenzuela.apiprospectos.domain.repository.DocumentDomainRespository;
import com.josevalenzuela.apiprospectos.persistence.crud.DocumentoCrudRepository;
import com.josevalenzuela.apiprospectos.persistence.entity.DocumentoEntity;
import com.josevalenzuela.apiprospectos.persistence.mapper.DocumentMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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
    public List<DocumentDomain> getAllDocsByID(int iD) {
        return mapper.toDocuments(docsCrudRepository.findByIdProspecto(iD));
    }

    @Override
    public DocumentDomain saveDoc(DocumentDomain document) {
        return mapper.toDocument(docsCrudRepository.save(mapper.toDocumentoEntity(document)));
    }
}
