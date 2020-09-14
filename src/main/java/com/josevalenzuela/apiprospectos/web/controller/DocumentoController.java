package com.josevalenzuela.apiprospectos.web.controller;

import com.josevalenzuela.apiprospectos.domain.DocumentDomain;
import com.josevalenzuela.apiprospectos.domain.ProspectDomain;
import com.josevalenzuela.apiprospectos.domain.service.DocumentoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/documentos")
public class DocumentoController {
    @Autowired
    private DocumentoService documentoService;

    @GetMapping(path = "/{idProspect}")
    @ApiOperation("Obtiene todos los documentos del prospecto seleccionado")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK")    ,
        @ApiResponse(code = 404, message = "Documentos no encotrados")
    })
    public ResponseEntity<List<DocumentDomain>> getByProspectId(@PathVariable int idProspect) {
        return documentoService.getByProspectId(idProspect)
                .map(documentDomains -> new ResponseEntity<>(documentDomains, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(path = "/save")
    @ApiOperation("Guardar un nuevo documento")
    @ApiResponse(code = 201, message = "Documento guardado satisfactoriamente")
    public ResponseEntity<DocumentDomain> save(@RequestBody DocumentDomain documentDomain) {
        return new ResponseEntity<>(documentoService.save(documentDomain), HttpStatus.CREATED);
    }
}
