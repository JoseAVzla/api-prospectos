package com.josevalenzuela.apiprospectos.persistence.crud;

import com.josevalenzuela.apiprospectos.domain.ProspectDomain;
import com.josevalenzuela.apiprospectos.persistence.entity.ProspectoEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProspectoCrudRepository extends CrudRepository<ProspectoEntity, Integer> {
    List<ProspectoEntity> findByEstatus(String estatus);

    @Transactional
    @Modifying
    @Query(value = "UPDATE prospectos SET estatus = :estatus, observaciones = :observaciones WHERE id_prospecto :idProspecto", nativeQuery = true)
    void actualizarEstatusConObservaciones(@Param("idProspecto") int idProspecto, @Param("estatus") String estatus, @Param("observaciones") String observaciones);
}
