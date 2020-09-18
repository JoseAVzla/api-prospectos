package com.josevalenzuela.apiprospectos.web.controller;


import com.josevalenzuela.apiprospectos.domain.DocumentDomain;
import com.josevalenzuela.apiprospectos.domain.ProspectDomain;
import com.josevalenzuela.apiprospectos.domain.dto.ProspectoRequestDTO;
import com.josevalenzuela.apiprospectos.domain.dto.ProspectoResponseDTO;
import com.josevalenzuela.apiprospectos.domain.service.DocumentoService;
import com.josevalenzuela.apiprospectos.domain.service.ProspectoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    public ResponseEntity<List<ProspectoResponseDTO>> getAllProspects() {
        List<ProspectoResponseDTO> responseDTOList = new ArrayList<>();
        List<ProspectDomain> prospectosGuardados = prospectoService.getAllProspects();

        for (ProspectDomain prospectoDomain: prospectosGuardados) {
            List<String> documentosUrl = new ArrayList<>();
            List<DocumentDomain> documentDomains = documentoService.getByProspectId(prospectoDomain.getProspectoId()).get();
            for (DocumentDomain documentDomain:documentDomains) {
                documentosUrl.add(documentDomain.getUrl());
            }


            ProspectoResponseDTO prospectoResponseDTO = new ProspectoResponseDTO();
            prospectoResponseDTO.setNombre(prospectoDomain.getNombre());
            prospectoResponseDTO.setPrimerApellido(prospectoDomain.getPrimerApellido());
            prospectoResponseDTO.setSegundoApellido(prospectoDomain.getSegundoApellido());
            prospectoResponseDTO.setCalle(prospectoDomain.getCalle());
            prospectoResponseDTO.setColonia(prospectoDomain.getColonia());
            prospectoResponseDTO.setNumero(prospectoDomain.getNumero());
            prospectoResponseDTO.setTelefono(prospectoDomain.getTelefono());
            prospectoResponseDTO.setRfc(prospectoDomain.getRfc());
            prospectoResponseDTO.setEstatus(prospectoDomain.getEstatus());
            prospectoResponseDTO.setCodigoPostal(prospectoDomain.getCodigoPostal());
            prospectoResponseDTO.setDocumentosUrl(documentosUrl);
            responseDTOList.add(prospectoResponseDTO);
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
    public ResponseEntity<ProspectoResponseDTO> save(@RequestBody ProspectoRequestDTO prospectoRequest) {
        String folder = "/Users/josevalenzuela/Desktop/docsFiles/";
        List<String> documentosUrlResponse =  new ArrayList<>();
        ProspectDomain prospectDomain = new ProspectDomain();
        prospectDomain.setNombre(prospectoRequest.getNombre());
        prospectDomain.setPrimerApellido(prospectoRequest.getPrimerApellido());
        prospectDomain.setSegundoApellido(prospectoRequest.getSegundoApellido());
        prospectDomain.setEstatus(prospectoRequest.getEstatus());
        prospectDomain.setCalle(prospectoRequest.getCalle());
        prospectDomain.setColonia(prospectoRequest.getColonia());
        prospectDomain.setCodigoPostal(prospectoRequest.getCodigoPostal());
        prospectDomain.setNumero(prospectoRequest.getNumero());
        prospectDomain.setRfc(prospectoRequest.getRfc());
        prospectDomain.setTelefono(prospectoRequest.getTelefono());
        ProspectDomain prospectoGuardado = prospectoService.save(prospectDomain);

        int index = 0;
        for (String encodedDoc: prospectoRequest.getDocumentosEncoded()) {
            byte[] data = Base64.decodeBase64(encodedDoc);
            Path path = Paths.get(folder + prospectoGuardado.getProspectoId() + index);
            try {
                Files.write(path, data);
            } catch (IOException e) {
                e.printStackTrace();
            }

            /*try (OutputStream stream = new FileOutputStream(path)) {
                stream.write(data);
                DocumentDomain documentDomain = new DocumentDomain();
                documentDomain.setIdProspect(prospectoGuardado.getProspectoId());
                documentDomain.setUrl(path + prospectoGuardado.getProspectoId() + index);
                documentosUrlResponse.add(documentoService.save(documentDomain).getUrl());
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            index++;
        }
        ProspectoResponseDTO prospectoResponseDTO = new ProspectoResponseDTO();
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
        prospectoResponseDTO.setDocumentosUrl(documentosUrlResponse);
        return new ResponseEntity<>(prospectoResponseDTO, HttpStatus.CREATED);
    }

    @PutMapping(path = "/update/")
    @ApiOperation("Actualiza el estatus del prospecto y agrega las observaciones")
    @ApiResponse(code = 200, message = "Actualizacion realizada correctamente")
    public ResponseEntity<ProspectDomain> update( @RequestBody ProspectDomain prospectDomain) {
        return new ResponseEntity<>(prospectoService.save(prospectDomain), HttpStatus.OK);
    }
}
