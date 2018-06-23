/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import AppBanco.ejb.ClienteFacade;
import AppBanco.entity.Cliente;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;

/**
 *
 * @author user
 */
@Named(value = "LoginClienteBean")
@SessionScoped
public class LoginClienteBean implements Serializable {

    @EJB
    private ClienteFacade Conectorcliente;
    private String dni;
    private String password;
    private int error;
    private Cliente cliente;
    /**
     * Creates a new instance of LoginClienteBean
     */
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public Cliente getCliente(){
        return cliente;
    }
    
    public String doLogin(){
        cliente=Conectorcliente.find(dni);
        String page="LoginCliente.xhtml";
        error=0;
        if(cliente==null||!cliente.getContrasenya().equals(password)){
              if(cliente==null){
                 error=1;
              }else if(!cliente.getContrasenya().equals(password)){
                 error=2;
              }
         }else{
              page="MovimientosCliente.xhtml"; //Cambiar al bueno una vez este hecho
         }
         return page;
    }
    
    public LoginClienteBean() {
    }
    public int doError(){
        return error;
    }
}
