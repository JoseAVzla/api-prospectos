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

    public int getIdProspect() {
        return idProspect;
    }

    public void setIdProspect(int idProspect) {
        this.idProspect = idProspect;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
