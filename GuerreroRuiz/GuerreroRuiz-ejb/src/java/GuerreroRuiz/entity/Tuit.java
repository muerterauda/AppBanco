/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuerreroRuiz.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author GRJuanjo
 */
@Entity
@Table(name = "TUIT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tuit.findAll", query = "SELECT t FROM Tuit t")
    , @NamedQuery(name = "Tuit.findByStatus", query = "SELECT t FROM Tuit t WHERE t.status = :status")
    , @NamedQuery(name = "Tuit.findByTexto", query = "SELECT t FROM Tuit t WHERE t.texto = :texto")
    , @NamedQuery(name = "Tuit.findByFecha", query = "SELECT t FROM Tuit t WHERE t.fecha = :fecha")})
public class Tuit implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "STATUS")
    private Long status;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 140)
    @Column(name = "TEXTO")
    private String texto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @JoinTable(name = "TUIT_MEGUSTA", joinColumns = {
        @JoinColumn(name = "STATUS", referencedColumnName = "STATUS")}, inverseJoinColumns = {
        @JoinColumn(name = "IDUSUARIO_MG", referencedColumnName = "IDUSUARIO")})
    @ManyToMany
    private List<Usuario> usuarioList;
    @JoinTable(name = "RETUIT", joinColumns = {
        @JoinColumn(name = "STATUS", referencedColumnName = "STATUS")}, inverseJoinColumns = {
        @JoinColumn(name = "IDUSUARIO_RT", referencedColumnName = "IDUSUARIO")})
    @ManyToMany
    private List<Usuario> usuarioList1;
    @JoinColumn(name = "PAIS", referencedColumnName = "PAIS_ISO_CODE")
    @ManyToOne(optional = false)
    private Pais pais;
    @JoinColumn(name = "USUARIO", referencedColumnName = "IDUSUARIO")
    @ManyToOne(optional = false)
    private Usuario usuario;

    public Tuit() {
    }

    public Tuit(Long status) {
        this.status = status;
    }

    public Tuit(Long status, String texto, Date fecha) {
        this.status = status;
        this.texto = texto;
        this.fecha = fecha;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @XmlTransient
    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    @XmlTransient
    public List<Usuario> getUsuarioList1() {
        return usuarioList1;
    }

    public void setUsuarioList1(List<Usuario> usuarioList1) {
        this.usuarioList1 = usuarioList1;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (status != null ? status.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tuit)) {
            return false;
        }
        Tuit other = (Tuit) object;
        if ((this.status == null && other.status != null) || (this.status != null && !this.status.equals(other.status))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "GuerreroRuiz.entity.Tuit[ status=" + status + " ]";
    }
    
}
