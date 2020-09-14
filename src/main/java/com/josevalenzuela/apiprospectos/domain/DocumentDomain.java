package com.josevalenzuela.apiprospectos.domain;

public class DocumentDomain {
    private int idDoc;
    private int idProspect;
    private String url;

    public int getIdDoc() {
        return idDoc;
    }

    public void setIdDoc(int idDoc) {
        this.idDoc = idDoc;
    }

    public int getIdProspecto() {
        return idProspect;
    }

    public void setIdProspecto(int idProspect) {
        this.idProspect = idProspect;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
