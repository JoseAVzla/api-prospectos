package com.josevalenzuela.apiprospectos.domain.dto;

public class UpdateRequest {
    private String estatus;
    private String observaciones;

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
