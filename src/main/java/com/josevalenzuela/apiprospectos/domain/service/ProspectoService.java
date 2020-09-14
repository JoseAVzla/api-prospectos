package com.josevalenzuela.apiprospectos.domain.service;

import com.josevalenzuela.apiprospectos.domain.ProspectDomain;
import com.josevalenzuela.apiprospectos.domain.repository.ProspectDomainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProspectoService {

    @Autowired
    private ProspectDomainRepository prospectDomainRepository;

    public List<ProspectDomain> getAllProspects(){
        return prospectDomainRepository.getAllProspectos();
    }

    public Optional<ProspectDomain> getProspect(int id){
        return prospectDomainRepository.getProspect(id);
    }

    public Optional<List<ProspectDomain>> getByStatus(String estatus){
        return prospectDomainRepository.getAllProspectosByStatus(estatus);
    }

    public ProspectDomain save(ProspectDomain prospectDomain){
        return prospectDomainRepository.save(prospectDomain);
    }

    public boolean updateStatus(int id, String status, String obser){
        return getProspect(id).map(prospectDomain -> {
                prospectDomainRepository.updateStatusWithObservations(id, status, obser);
                return true;
            }
        ).orElse(false);
    }
}
