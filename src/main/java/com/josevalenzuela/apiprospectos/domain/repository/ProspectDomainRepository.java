package com.josevalenzuela.apiprospectos.domain.repository;

import com.josevalenzuela.apiprospectos.domain.ProspectDomain;

import java.util.List;
import java.util.Optional;

public interface ProspectDomainRepository {
    List<ProspectDomain> getAllProspectos();
    Optional<ProspectDomain> getProspect(int id);
    Optional<List<ProspectDomain>> getAllProspectosByStatus(String estatus);
    ProspectDomain save(ProspectDomain prospecto);
    void updateStatusWithObservations(int idProspecto, String estatus, String obsverbaciones);

}
