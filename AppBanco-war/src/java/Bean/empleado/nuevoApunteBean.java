/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean.empleado;

import AppBanco.ejb.MovimientoFacade;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

/**
 *
 * @author elias
 */
@Named(value = "nuevoApunteBean")
@Dependent
public class nuevoApunteBean {

    @EJB
    private MovimientoFacade movimientoFacade;
    
    @Inject
    EmpleadoSessionBeanAbstract empleadosession;
    
    
    
    private String cantidadTexto;
    private String tipo;
    private String error="";
    /**
     * Creates a new instance of nuevoApunteBean
     */
    public nuevoApunteBean() {
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public String getCantidad() {
        return cantidadTexto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setCantidad(String cantidad) {
        this.cantidadTexto = cantidad;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    // aqui se usa la primera cuenta de la lista de cuentas del cliente, tengo que preguntar como se la cuenta concreta seleccionada.
    public String doApunte(){
        String ret="nuevoApunte.xhtml";
        try{
        double cantidad=Double.parseDouble(cantidadTexto);
        movimientoFacade.nuevoApunte(tipo, empleadosession.getLoginBean().getEmpleado() , empleadosession.getCuenta(), cantidad);
        }catch(NumberFormatException e){
            setError("La cantidad introducida no es un numero");
        }
        if(error.equals("")){
            ret="principalEmpleado";
        }
        
        
        return ret;
    }
    
    
}
