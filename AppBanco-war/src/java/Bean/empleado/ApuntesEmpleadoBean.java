
package Bean.empleado;

import AppBanco.ejb.CuentaFacade;
import AppBanco.ejb.MovimientoFacade;
import AppBanco.entity.Cliente;
import AppBanco.entity.Movimiento;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author vikour
 */
@Named(value = "apuntesEmpleadoBean")
@RequestScoped
public class ApuntesEmpleadoBean {

    @EJB
    private CuentaFacade cuentaEJB;    
    @EJB
    private MovimientoFacade movEJB;
    @Inject
    private EmpleadoSessionBeanAbstract session;
    
    private String inputCuenta;
    private String error;
    private List<Movimiento> movimientos;
    
    public ApuntesEmpleadoBean() {
    }
    
    @PostConstruct
    public void apuntesEmpleadoInit() {
        
        if (session.getCuenta()!= null) {
            setInputCuenta(session.getCuenta().getNumeroStr());
            movimientos = movEJB.buscarPorCuenta(session.getCuenta());
        }
        
    }
    
    public void doSearchCuenta() {
        session.setCuenta(cuentaEJB.findCuentaNumeroStr(inputCuenta));
        
        if (session.getCuenta() != null) {
            session.setCliente(session.getCuenta().getCliente());
            movimientos = movEJB.buscarPorCuenta(session.getCuenta());
        }
        else
            setError("No existe la cuenta.");
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
    
    public Cliente getCliente() {
        return session.getCliente();
    }

    public void setInputCuenta(String inputCuenta) {
        this.inputCuenta = inputCuenta;
    }

    public String getInputCuenta() {
        return inputCuenta;
    }

    public void setMovimientos(List<Movimiento> movimientos) {
        this.movimientos = movimientos;
    }

    public List<Movimiento> getMovimientos() {
        return movimientos;
    }
    
    
}
