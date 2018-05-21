/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import AppBanco.ejb.ClienteFacade;
import AppBanco.ejb.CuentaFacade;
import AppBanco.entity.Cliente;
import AppBanco.entity.Cuenta;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

/**
 *
 * @author user
 */
@Named(value = "clienteSessionBeanAbstract")
@Dependent
public abstract class ClienteSessionBeanAbstract {
    @Inject
    private LoginClienteBean l;
    private Cliente cliente;
    private double saldo;
    private Cuenta cuenta;
    @EJB
    private ClienteFacade clf;
    @EJB
    private CuentaFacade cf;
    /**
     * Creates a new instance of ClienteSessionBeanAbstract
     */
    public ClienteSessionBeanAbstract() {
        
    }
    public Cliente getCliente(){
        return cliente;
    }

    public double getSaldo() {
        return saldo;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }
    
    public String doLogout(){
        return "LoginCliente.xhtml";
    }
    @PostConstruct
    public void falsoConstruct(){
        cliente=l.getCliente();
        cuenta=clf.getCuenta(cliente.getDni());
        saldo=cf.getSaldoCuenta(cuenta.getNumeroStr());
    }
}
