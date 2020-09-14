package com.josevalenzuela.apiprospectos.persistence.repository;

import com.josevalenzuela.apiprospectos.domain.ProspectDomain;
import com.josevalenzuela.apiprospectos.domain.repository.ProspectDomainRepository;
import com.josevalenzuela.apiprospectos.persistence.crud.ProspectoCrudRepository;
import com.josevalenzuela.apiprospectos.persistence.entity.ProspectoEntity;
import com.josevalenzuela.apiprospectos.persistence.mapper.ProspectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProspectoRepository implements ProspectDomainRepository {
    @Autowired
    private ProspectoCrudRepository crudRepository;
    @Autowired
    private ProspectMapper mapper;

    @Override
    public List<ProspectDomain> getAllProspectos() {
        return mapper.toProspects((List<ProspectoEntity>) crudRepository.findAll());
    }

    @Override
    public Optional<ProspectDomain> getProspect(int id) {
        return crudRepository.findById(id).map(prospectoEntity -> mapper.toProspect(prospectoEntity));
    }

    @Override
    public Optional<List<ProspectDomain>> getAllProspectosByStatus(String estatus) {
        return Optional.of(mapper.toProspects(crudRepository.findByEstatus(estatus)));
    }

    @Override
    public ProspectDomain save(ProspectDomain prospecto) {
        return mapper.toProspect(crudRepository.save(mapper.toProstectoEntity(prospecto)));
    }

    @Override
    public void updateStatusWithObservations(int idProspecto, String estatus, String obsverbaciones) {
        crudRepository.actualizarEstatusConObservaciones(idProspecto, estatus, obsverbaciones);
    }
}
