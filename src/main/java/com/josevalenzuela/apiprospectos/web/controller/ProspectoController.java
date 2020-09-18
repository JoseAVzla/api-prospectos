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


            ProspectoDTO response = new ProspectoDTO();
            response.setProspectoId(prospectoDomain.getProspectoId());
            response.setNombre(prospectoDomain.getNombre());
            response.setPrimerApellido(prospectoDomain.getPrimerApellido());
            response.setSegundoApellido(prospectoDomain.getSegundoApellido());
            response.setCalle(prospectoDomain.getCalle());
            response.setColonia(prospectoDomain.getColonia());
            response.setNumero(prospectoDomain.getNumero());
            response.setTelefono(prospectoDomain.getTelefono());
            response.setRfc(prospectoDomain.getRfc());
            response.setEstatus(prospectoDomain.getEstatus());
            response.setCodigoPostal(prospectoDomain.getCodigoPostal());
            response.setObservaciones(prospectoDomain.getObservaciones());
            response.setDocumentosUrl(documentosUrl);
            responseDTOList.add(response);
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
        //Mapeando los datos del request
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

        //Guardando los urls de los documentos
        for (String url : prospectoDTORequest.getDocumentosUrl()) {
            DocumentDomain documentDomainAguardar = new DocumentDomain();
            documentDomainAguardar.setIdProspect(prospectoGuardado.getProspectoId());
            documentDomainAguardar.setUrl(url);
            DocumentDomain documentGuardado = documentoService.save(documentDomainAguardar);
            documentosUrlResponse.add(documentGuardado.getUrl());
        }

        //Agregando los datos para el response..
        ProspectoDTO prospectoDTOResponse = new ProspectoDTO();
        prospectoDTOResponse.setProspectoId(prospectoGuardado.getProspectoId());
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

    @PostMapping (path = "/update")
    @ApiOperation("Actualiza el estatus del prospecto y agrega las observaciones")
    @ApiResponse(code = 200, message = "Actualizacion realizada correctamente")
    public ResponseEntity<ProspectoDTO> update(@RequestBody ProspectoDTO prospectoRequestDTO) {

        //Mapeando los datos del request
        ProspectDomain prospectDomain = new ProspectDomain();
        prospectDomain.setProspectoId(prospectoRequestDTO.getProspectoId());
        prospectDomain.setNombre(prospectoRequestDTO.getNombre());
        prospectDomain.setPrimerApellido(prospectoRequestDTO.getPrimerApellido());
        prospectDomain.setSegundoApellido(prospectoRequestDTO.getSegundoApellido());
        prospectDomain.setEstatus(prospectoRequestDTO.getEstatus());
        prospectDomain.setCalle(prospectoRequestDTO.getCalle());
        prospectDomain.setColonia(prospectoRequestDTO.getColonia());
        prospectDomain.setCodigoPostal(prospectoRequestDTO.getCodigoPostal());
        prospectDomain.setNumero(prospectoRequestDTO.getNumero());
        prospectDomain.setRfc(prospectoRequestDTO.getRfc());
        prospectDomain.setTelefono(prospectoRequestDTO.getTelefono());
        prospectDomain.setObservaciones(prospectoRequestDTO.getObservaciones());
        //Actualizar datos del prospecto guardando el objeto completo en el mismo registro con el mismo Id
        ProspectDomain prospectoGuardado = prospectoService.save(prospectDomain);
        //Agregando los datos para el response..
        ProspectoDTO prospectoResponseDTO = new ProspectoDTO();
        prospectoResponseDTO.setProspectoId(prospectoGuardado.getProspectoId());
        prospectoResponseDTO.setNombre(prospectoGuardado.getNombre());
        prospectoResponseDTO.setPrimerApellido(prospectoGuardado.getPrimerApellido());
        prospectoResponseDTO.setSegundoApellido(prospectoGuardado.getSegundoApellido());
        prospectoResponseDTO.setCalle(prospectoGuardado.getCalle());
        prospectoResponseDTO.setColonia(prospectoGuardado.getColonia());
        prospectoResponseDTO.setNumero(prospectoGuardado.getNumero());
        prospectoResponseDTO.setTelefono(prospectoGuardado.getTelefono());
        prospectoResponseDTO.setRfc(prospectoGuardado.getRfc());
        prospectoResponseDTO.setEstatus(prospectoGuardado.getEstatus());
        prospectoResponseDTO.setCodigoPostal(prospectoGuardado.getCodigoPostal());
        prospectoResponseDTO.setDocumentosUrl(prospectoRequestDTO.getDocumentosUrl());
        return new ResponseEntity<>(prospectoResponseDTO, HttpStatus.OK);
    }
}
