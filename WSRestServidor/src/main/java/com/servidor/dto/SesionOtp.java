/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servidor.dto;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jonathan
 */
@Entity
@Table(name = "sesion_otp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SesionOtp.findAll", query = "SELECT s FROM SesionOtp s"),
    @NamedQuery(name = "SesionOtp.findByCodSesionOtp", query = "SELECT s FROM SesionOtp s WHERE s.codSesionOtp = :codSesionOtp"),
    @NamedQuery(name = "SesionOtp.findByCodigoOtp", query = "SELECT s FROM SesionOtp s WHERE s.codigoOtp = :codigoOtp"),
    @NamedQuery(name = "SesionOtp.findByFechaExpuesta", query = "SELECT s FROM SesionOtp s WHERE s.fechaExpuesta = :fechaExpuesta")})
public class SesionOtp implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_sesion_otp")
    private Integer codSesionOtp;
    @Basic(optional = false)
    @Column(name = "codigo_otp")
    private String codigoOtp;
    @Column(name = "fecha_expuesta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaExpuesta;
    @JoinColumn(name = "cod_usuario", referencedColumnName = "cod_usuario")
    @ManyToOne(optional = false)
    private Usuario codUsuario;

    public SesionOtp() {
    }

    public SesionOtp(Integer codSesionOtp) {
        this.codSesionOtp = codSesionOtp;
    }

    public SesionOtp(Integer codSesionOtp, String codigoOtp) {
        this.codSesionOtp = codSesionOtp;
        this.codigoOtp = codigoOtp;
    }

    public Integer getCodSesionOtp() {
        return codSesionOtp;
    }

    public void setCodSesionOtp(Integer codSesionOtp) {
        this.codSesionOtp = codSesionOtp;
    }

    public String getCodigoOtp() {
        return codigoOtp;
    }

    public void setCodigoOtp(String codigoOtp) {
        this.codigoOtp = codigoOtp;
    }

    public Date getFechaExpuesta() {
        return fechaExpuesta;
    }

    public void setFechaExpuesta(Date fechaExpuesta) {
        this.fechaExpuesta = fechaExpuesta;
    }

    public Usuario getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(Usuario codUsuario) {
        this.codUsuario = codUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codSesionOtp != null ? codSesionOtp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SesionOtp)) {
            return false;
        }
        SesionOtp other = (SesionOtp) object;
        if ((this.codSesionOtp == null && other.codSesionOtp != null) || (this.codSesionOtp != null && !this.codSesionOtp.equals(other.codSesionOtp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.servidor.dto.SesionOtp[ codSesionOtp=" + codSesionOtp + " ]";
    }
    
}
