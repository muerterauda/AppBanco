/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuerreroRuiz.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author GRJuanjo
 */
@Entity
@Table(name = "PAIS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pais.findAll", query = "SELECT p FROM Pais p")
    , @NamedQuery(name = "Pais.findByIdpais", query = "SELECT p FROM Pais p WHERE p.idpais = :idpais")
    , @NamedQuery(name = "Pais.findByPaisIsoCode", query = "SELECT p FROM Pais p WHERE p.paisIsoCode = :paisIsoCode")
    , @NamedQuery(name = "Pais.findByRegion", query = "SELECT p FROM Pais p WHERE p.region = :region")})
public class Pais implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 26)
    @Column(name = "IDPAIS")
    private String idpais;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "PAIS_ISO_CODE")
    private String paisIsoCode;
    @Size(max = 26)
    @Column(name = "REGION")
    private String region;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pais")
    private List<Tuit> tuitList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pais")
    private List<Usuario> usuarioList;

    public Pais() {
    }

    public Pais(String paisIsoCode) {
        this.paisIsoCode = paisIsoCode;
    }

    public Pais(String paisIsoCode, String idpais) {
        this.paisIsoCode = paisIsoCode;
        this.idpais = idpais;
    }

    public String getIdpais() {
        return idpais;
    }

    public void setIdpais(String idpais) {
        this.idpais = idpais;
    }

    public String getPaisIsoCode() {
        return paisIsoCode;
    }

    public void setPaisIsoCode(String paisIsoCode) {
        this.paisIsoCode = paisIsoCode;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @XmlTransient
    public List<Tuit> getTuitList() {
        return tuitList;
    }

    public void setTuitList(List<Tuit> tuitList) {
        this.tuitList = tuitList;
    }

    @XmlTransient
    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (paisIsoCode != null ? paisIsoCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pais)) {
            return false;
        }
        Pais other = (Pais) object;
        if ((this.paisIsoCode == null && other.paisIsoCode != null) || (this.paisIsoCode != null && !this.paisIsoCode.equals(other.paisIsoCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "GuerreroRuiz.entity.Pais[ paisIsoCode=" + paisIsoCode + " ]";
    }
    
}
