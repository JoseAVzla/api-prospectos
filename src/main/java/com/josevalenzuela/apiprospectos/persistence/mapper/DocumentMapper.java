package com.josevalenzuela.apiprospectos.persistence.mapper;

import com.josevalenzuela.apiprospectos.domain.DocumentDomain;
import com.josevalenzuela.apiprospectos.domain.ProspectDomain;
import com.josevalenzuela.apiprospectos.persistence.entity.DocumentoEntity;
import com.josevalenzuela.apiprospectos.persistence.entity.ProspectoEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DocumentMapper {
    @Mappings({
            @Mapping(source = "idProspecto", target = "idProspect"),
    })
    DocumentDomain toDocument(DocumentoEntity documentoEntity);
    List<DocumentDomain> toDocuments(List<DocumentoEntity> documentoEntities);

    @InheritInverseConfiguration
    DocumentoEntity toDocumentoEntity(DocumentDomain prospectDomain);
}
