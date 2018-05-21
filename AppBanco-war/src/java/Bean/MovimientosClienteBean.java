/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import AppBanco.ejb.MovimientoFacade;
import AppBanco.entity.Movimiento;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;

/**
 *
 * @author user
 */
@Named(value = "movimientosClienteBean")
@Dependent
public class MovimientosClienteBean extends ClienteSessionBeanAbstract{
    
    @EJB
    private MovimientoFacade mf;
    private List<Movimiento>movimientos; 
    
    /**
     * Creates a new instance of MovimientosClienteBean
     */
    public MovimientosClienteBean() {
    }

    public List<Movimiento> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(List<Movimiento> movimientos) {
        this.movimientos = movimientos;
    }

    
    @PostConstruct
    public void doInit(){
        movimientos=mf.buscarPorCuentaOrderByFechaDesc(getCuenta(), Boolean.TRUE, Boolean.TRUE, null);
    }
}
