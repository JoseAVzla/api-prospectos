package com.josevalenzuela.apiprospectos.persistence.entity;

import javax.persistence.*;

@Entity
@Table(name = "prospectos")
public class ProspectoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prospecto")
    private Integer idProspecto;

    private String nombre;

    @Column(name = "pri_apellido")
    private String primerApellido;

    @Column(name = "seg_apellido")
    private String segundoApellido;

    @Column(name = "direccion_calle")
    private String dirCalle;

    @Column(name = "direccion_numero")
    private String dirNumero;

    @Column(name = "direccion_colonia")
    private String dirColonia;

    @Column(name = "dirccion_cp")
    private String dirCp;

    private String telefono;

    private String rfc;

    private Estatus estatus;

    public Estatus getEstatus() {
        return estatus;
    }

    public void setEstatus(Estatus estatus) {
        this.estatus = estatus;
    }

    public Integer getIdProspecto() {
        return idProspecto;
    }

    public void setId(Integer idProspecto) {
        this.idProspecto = idProspecto;
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

    public String getDirCalle() {
        return dirCalle;
    }

    public void setDirCalle(String dirCalle) {
        this.dirCalle = dirCalle;
    }

    public String getDirNumero() {
        return dirNumero;
    }

    public void setDirNumero(String dirNumero) {
        this.dirNumero = dirNumero;
    }

    public String getDirColonia() {
        return dirColonia;
    }

    public void setDirColonia(String dirColonia) {
        this.dirColonia = dirColonia;
    }

    public String getDirCp() {
        return dirCp;
    }

    public void setDirCp(String dirCp) {
        this.dirCp = dirCp;
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

    private enum Estatus {
        ENVIADO, AUTORIZADO, RECHAZADO
    }
}
