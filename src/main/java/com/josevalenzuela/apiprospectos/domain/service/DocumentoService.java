package com.josevalenzuela.apiprospectos.domain.service;

import com.josevalenzuela.apiprospectos.domain.DocumentDomain;
import com.josevalenzuela.apiprospectos.domain.repository.DocumentDomainRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentoService {
    @Autowired
    private DocumentDomainRespository documentDomainRespository;

    public List<DocumentDomain> getDocs(){
        return documentDomainRespository.getAllDocs();
    }

    public Optional<List<DocumentDomain>> getByProspectId(int prospectId){
        return documentDomainRespository.getAllDocsByProspectID(prospectId);
    }

    public DocumentDomain save(DocumentDomain documentDomain){
        return documentDomainRespository.saveDoc(documentDomain);
    }
}
