package com.josevalenzuela.apiprospectos.persistence.crud;

import com.josevalenzuela.apiprospectos.domain.ProspectDomain;
import com.josevalenzuela.apiprospectos.persistence.entity.ProspectoEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProspectoCrudRepository extends CrudRepository<ProspectoEntity, Integer> {
    List<ProspectoEntity> findByEstatus(String estatus);

    @Modifying
    @Query("UPDATE prospectos p SET p.estatus = :estatus, p.observaciones = :observaciones WHERE p.id_prospecto :idProspecto")
    void actualizarEstatusConObservaciones(@Param("idProspecto") int idProspecto, @Param("estatus") String estatus, @Param("observaciones") String observaciones);
}
