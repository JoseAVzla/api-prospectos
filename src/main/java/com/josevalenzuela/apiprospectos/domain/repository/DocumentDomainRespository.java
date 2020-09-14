package com.josevalenzuela.apiprospectos.domain.repository;

import com.josevalenzuela.apiprospectos.domain.DocumentDomain;

import java.util.List;

public interface DocumentDomainRespository {
    List<DocumentDomain> getAllDocs();
    List<DocumentDomain> getAllDocsByID(int iD);
    DocumentDomain saveDoc(DocumentDomain document);
}
