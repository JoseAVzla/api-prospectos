package com.josevalenzuela.apiprospectos.web.controller;


import com.josevalenzuela.apiprospectos.domain.ProspectDomain;
import com.josevalenzuela.apiprospectos.domain.dto.UpdateRequest;
import com.josevalenzuela.apiprospectos.domain.service.ProspectoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prospectos")
public class ProspectoController {
    @Autowired
    private ProspectoService prospectoService;

    @GetMapping(path = "/all")
    @ApiOperation("Obtiene todos los prospectos")
    @ApiResponse(code = 200, message = "OK")
    public ResponseEntity<List<ProspectDomain>> getAllProspects() {
        return new ResponseEntity<>(prospectoService.getAllProspects(), HttpStatus.OK);
    }

    @GetMapping(path = "/{prospectId}")
    @ApiOperation("Obtiene un prospecto mediante el id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Prospecto no encontrado")
    })
    public ResponseEntity<ProspectDomain> getProspect(@PathVariable int prospectId) {
        return prospectoService.getProspect(prospectId).map(prospectDomain -> new ResponseEntity<>(prospectDomain, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/estatus/{status}")
    @ApiOperation("Obtiene una lista de prospectos que coincidan con el estatus indicado")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Prospectos no encontrados")
    })
    public ResponseEntity<List<ProspectDomain>> getByStatus(@PathVariable String status) {
        return prospectoService.getByStatus(status).map(prospectDomains ->
                new ResponseEntity<>(prospectDomains, HttpStatus.OK)).orElse(
                new ResponseEntity<>(HttpStatus.NOT_FOUND)
        );
    }

    @PostMapping(path = "/save")
    @ApiOperation("Guardar un nuevo prospecto")
    @ApiResponse(code = 201, message = "Prospecto guardado satisfactoriamente")
    public ResponseEntity<ProspectDomain> save(@RequestBody ProspectDomain prospectDomain) {
        return new ResponseEntity<>(prospectoService.save(prospectDomain), HttpStatus.CREATED);
    }

    @PutMapping(path = "/update/{id_prospecto}")
    @ApiOperation("Actualiza el estatus del prospecto y agrega las observaciones")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Actualizacion realizada correctamente"),
            @ApiResponse(code = 404, message = "No se encontró el prospecto seleccionado para actualizar")
    })
    public ResponseEntity updateStatus(@PathVariable int id_prospecto, @RequestBody UpdateRequest updateRequest) {
        if (prospectoService.updateStatus(id_prospecto, updateRequest.getEstatus(), updateRequest.getObservaciones())) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
