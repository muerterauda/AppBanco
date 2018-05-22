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
import javax.enterprise.context.RequestScoped;


/**
 *
 * @author user
 */
@Named(value = "movimientosClienteBean")
@RequestScoped
public class MovimientosClienteBean extends ClienteSessionBeanAbstract {

    @EJB
    private MovimientoFacade mf;
    private List<Movimiento> movimientos;
    private String conceptoFiltro;
    private Boolean ingresos;
    private Boolean gastos;

    /**
     * Creates a new instance of MovimientosClienteBean
     */
    public MovimientosClienteBean() {
        ingresos=false;
        gastos=false;
        conceptoFiltro="";
    }

   
    @PostConstruct
    public void doInit() {
        movimientos = mf.buscarPorCuenta(super.getCuenta());
    }

    public MovimientoFacade getMf() {
        return mf;
    }

    public void setMf(MovimientoFacade mf) {
        this.mf = mf;
    }

    public List<Movimiento> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(List<Movimiento> movimientos) {
        this.movimientos = movimientos;
    }

    public String getConceptoFiltro() {
        return conceptoFiltro;
    }

    public void setConceptoFiltro(String conceptoFiltro) {
        this.conceptoFiltro = conceptoFiltro;
    }

    public Boolean getIngresos() {
        return ingresos;
    }

    public void setIngresos(Boolean ingresos) {
        this.ingresos = ingresos;
    }

    public Boolean getGastos() {
        return gastos;
    }

    public void setGastos(Boolean gastos) {
        this.gastos = gastos;
    }
     public void doFilter() {
         movimientos=mf.buscarPorCuentaOrderByFechaDesc(getCuenta(), ingresos, gastos, conceptoFiltro);
    }

}
