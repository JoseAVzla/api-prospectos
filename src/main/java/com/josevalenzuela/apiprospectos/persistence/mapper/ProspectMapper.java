package com.josevalenzuela.apiprospectos.persistence.mapper;

import com.josevalenzuela.apiprospectos.domain.ProspectDomain;
import com.josevalenzuela.apiprospectos.persistence.entity.ProspectoEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface ProspectMapper {
    @Mappings({
            @Mapping(source = "idProspecto", target = "prospectoId"),
            @Mapping( source = "dirCalle", target = "calle"),
            @Mapping( source = "dirNumero", target = "numero"),
            @Mapping( source = "dirColonia", target = "colonia"),
            @Mapping( source = "dirCp", target = "codigoPostal"),
    })
    ProspectDomain toProspect(ProspectoEntity prospectoEntity);
    List<ProspectDomain> toProspects(List<ProspectoEntity> prospectoEntities);

    @InheritInverseConfiguration
    ProspectoEntity toProstectoEntity(ProspectDomain prospectDomain);
}
