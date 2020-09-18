package com.josevalenzuela.apiprospectos.domain.dto;

import com.josevalenzuela.apiprospectos.domain.DocumentDomain;

import java.util.List;

public class ProspectoResponseDTO {
    private int prospectoId;
    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private String calle;
    private String numero;
    private String colonia;
    private String codigoPostal;
    private String telefono;
    private String rfc;
    private String estatus;
    private String observaciones;
    private List<String> documentosUrl;

    public List<String> getDocumentosUrl() {
        return documentosUrl;
    }

    public void setDocumentosUrl(List<String> documentosUrl) {
        this.documentosUrl = documentosUrl;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public int getProspectoId() {
        return prospectoId;
    }

    public void setProspectoId(int prospectoId) {
        this.prospectoId = prospectoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }
}
