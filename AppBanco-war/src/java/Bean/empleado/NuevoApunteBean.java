/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean.empleado;

import AppBanco.ejb.EmpleadoFacade;
import AppBanco.ejb.MovimientoFacade;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author elias
 */
@Named(value = "NuevoApunteBean")
@RequestScoped
public class NuevoApunteBean implements Serializable {

    @EJB
    private MovimientoFacade movimientoFacade;
    
    @Inject
    EmpleadoSessionBeanAbstract empleadosession;
    
    
    
    private String cantidadTexto;
    private String error;
    private String tipo;
    /**
     * Creates a new instance of nuevoApunteBean
     */
    public NuevoApunteBean() {
    }
    @PostConstruct
    public void init(){
        error="";
        tipo="I";
        cantidadTexto="";
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public String getCantidadTexto() {
        return cantidadTexto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setCantidadTexto(String cantidadt) {
        this.cantidadTexto = cantidadt;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public String doApunte(){
        String ret="nuevoApunte";
        try{
        double dinero=Double.parseDouble(cantidadTexto);
        
        movimientoFacade.nuevoApunte(tipo, empleadosession.getEmpleado() , empleadosession.getCuenta(), dinero);
        }catch(NumberFormatException e){
            setError("La cantidad introducida no es un numero");
        }catch(Exception e){
             setError("Transaccion no valida");
        }
        if(error.equals("")){
            ret="apuntesEmpleado";
        }
        
        
        return ret;
    }
    
    
}
