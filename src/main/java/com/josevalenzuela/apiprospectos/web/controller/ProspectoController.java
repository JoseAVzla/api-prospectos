package com.josevalenzuela.apiprospectos.web.controller;


import com.josevalenzuela.apiprospectos.domain.DocumentDomain;
import com.josevalenzuela.apiprospectos.domain.ProspectDomain;
import com.josevalenzuela.apiprospectos.domain.dto.ProspectoDTO;
import com.josevalenzuela.apiprospectos.domain.service.DocumentoService;
import com.josevalenzuela.apiprospectos.domain.service.ProspectoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/prospectos")
public class ProspectoController {
    @Autowired
    private ProspectoService prospectoService;
    @Autowired
    private DocumentoService documentoService;


    @GetMapping(path = "/all")
    @ApiOperation("Obtiene todos los prospectos")
    @ApiResponse(code = 200, message = "OK")
    public ResponseEntity<List<ProspectoDTO>> getAllProspects() {
        List<ProspectoDTO> responseDTOList = new ArrayList<>();
        List<ProspectDomain> prospectosGuardados = prospectoService.getAllProspects();

        for (ProspectDomain prospectoDomain: prospectosGuardados) {
            List<String> documentosUrl = new ArrayList<>();
            List<DocumentDomain> documentDomains = documentoService.getByProspectId(prospectoDomain.getProspectoId()).get();
            for (DocumentDomain documentDomain:documentDomains) {
                documentosUrl.add(documentDomain.getUrl());
            }


            ProspectoDTO prospectoDTO = new ProspectoDTO();
            prospectoDTO.setNombre(prospectoDomain.getNombre());
            prospectoDTO.setPrimerApellido(prospectoDomain.getPrimerApellido());
            prospectoDTO.setSegundoApellido(prospectoDomain.getSegundoApellido());
            prospectoDTO.setCalle(prospectoDomain.getCalle());
            prospectoDTO.setColonia(prospectoDomain.getColonia());
            prospectoDTO.setNumero(prospectoDomain.getNumero());
            prospectoDTO.setTelefono(prospectoDomain.getTelefono());
            prospectoDTO.setRfc(prospectoDomain.getRfc());
            prospectoDTO.setEstatus(prospectoDomain.getEstatus());
            prospectoDTO.setCodigoPostal(prospectoDomain.getCodigoPostal());
            prospectoDTO.setDocumentosUrl(documentosUrl);
            responseDTOList.add(prospectoDTO);
        }

        return new ResponseEntity<>(responseDTOList, HttpStatus.OK);
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
    public ResponseEntity<ProspectoDTO> save(@RequestBody ProspectoDTO prospectoDTORequest) {
        List<String> documentosUrlResponse =  new ArrayList<>();
        ProspectDomain prospectDomain = new ProspectDomain();
        prospectDomain.setNombre(prospectoDTORequest.getNombre());
        prospectDomain.setPrimerApellido(prospectoDTORequest.getPrimerApellido());
        prospectDomain.setSegundoApellido(prospectoDTORequest.getSegundoApellido());
        prospectDomain.setEstatus(prospectoDTORequest.getEstatus());
        prospectDomain.setCalle(prospectoDTORequest.getCalle());
        prospectDomain.setColonia(prospectoDTORequest.getColonia());
        prospectDomain.setCodigoPostal(prospectoDTORequest.getCodigoPostal());
        prospectDomain.setNumero(prospectoDTORequest.getNumero());
        prospectDomain.setRfc(prospectoDTORequest.getRfc());
        prospectDomain.setTelefono(prospectoDTORequest.getTelefono());
        ProspectDomain prospectoGuardado = prospectoService.save(prospectDomain);

        for (String url : prospectoDTORequest.getDocumentosUrl()) {
            DocumentDomain documentDomainAguardar = new DocumentDomain();
            documentDomainAguardar.setIdProspect(prospectoGuardado.getProspectoId());
            documentDomainAguardar.setUrl(url);
            DocumentDomain documentGuardado = documentoService.save(documentDomainAguardar);
            documentosUrlResponse.add(documentGuardado.getUrl());
        }


        ProspectoDTO prospectoDTOResponse = new ProspectoDTO();
        prospectoDTOResponse.setNombre(prospectoGuardado.getNombre());
        prospectoDTOResponse.setPrimerApellido(prospectoGuardado.getPrimerApellido());
        prospectoDTOResponse.setSegundoApellido(prospectoGuardado.getSegundoApellido());
        prospectoDTOResponse.setCalle(prospectoGuardado.getCalle());
        prospectoDTOResponse.setColonia(prospectoGuardado.getColonia());
        prospectoDTOResponse.setNumero(prospectoGuardado.getNumero());
        prospectoDTOResponse.setTelefono(prospectoGuardado.getTelefono());
        prospectoDTOResponse.setRfc(prospectoGuardado.getRfc());
        prospectoDTOResponse.setEstatus(prospectoGuardado.getEstatus());
        prospectoDTOResponse.setCodigoPostal(prospectoGuardado.getCodigoPostal());
        prospectoDTOResponse.setDocumentosUrl(documentosUrlResponse);
        return new ResponseEntity<>(prospectoDTOResponse, HttpStatus.CREATED);
    }

    @PutMapping(path = "/update/")
    @ApiOperation("Actualiza el estatus del prospecto y agrega las observaciones")
    @ApiResponse(code = 200, message = "Actualizacion realizada correctamente")
    public ResponseEntity<ProspectDomain> update( @RequestBody ProspectDomain prospectDomain) {
        return new ResponseEntity<>(prospectoService.save(prospectDomain), HttpStatus.OK);
    }
}
