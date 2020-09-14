package com.josevalenzuela.apiprospectos.domain.repository;

import com.josevalenzuela.apiprospectos.domain.DocumentDomain;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

public interface DocumentDomainRespository {
    List<DocumentDomain> getAllDocs();
    Optional<List<DocumentDomain>> getAllDocsByProspectID(int iD);
    DocumentDomain saveDoc(DocumentDomain document);
}
