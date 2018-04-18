/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AppBanco.ejb;

import AppBanco.entity.Cuenta;
import AppBanco.entity.Empleado;
import AppBanco.entity.Movimiento;
import AppBanco.entity.Operacion;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless
public class MovimientoFacade extends AbstractFacade<Movimiento> {

    @PersistenceContext(unitName = "AppBanco-ejbPU")
    private EntityManager em;
    
    @EJB
    private OperacionFacade opF;
    
    @EJB
    private CuentaFacade cuF;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MovimientoFacade() {
        super(Movimiento.class);
    }
    
    public List<Movimiento> buscarPorCuentaOrderByFechaDesc(Cuenta cuenta, Boolean ingreso, Boolean gastos, String concepto) {
        String qq = "SELECT m FROM Movimiento m WHERE m.cuenta.numeroCuenta = :cuenta";
        
        if (concepto != null)
            qq = qq + " AND m.concepto LIKE :concepto";
        
        if (ingreso && !gastos)
            qq = qq + " AND m.importe >= 0";

        if (!ingreso && gastos)
            qq = qq + " AND m.importe < 0";
        
        qq = qq + " ORDER BY m.fecha DESC";
        
        Query query = getEntityManager()
              .createQuery(qq);
        query.setParameter("cuenta", cuenta.getNumeroCuenta());
        
        if (concepto != null)
            query.setParameter("concepto", "%" + concepto + "%");
        
        return query.getResultList();
    }
    /**
     * Crea un nuevo apunte de Ingreso o Reintegro en una cuenta ya existente
     * @param operacion I (Ingreso) o R (Reintegro)
     * @param em Obejto Empleado, el que realiza la transaccion
     * @param cuenta Cuenta a la que se va a realizar el apunte
     * @param cantidad Cantidad referida al apunte, unsigned mayor que 0
     */
    public Movimiento nuevoApunte(String operacion, Empleado em, Cuenta cuenta, int cantidad) {
        if(em==null||cuenta==null||cantidad<=0||!(operacion.equals("I")||operacion.equals("R"))){
            throw new RuntimeException("Parametros incorrectos");
        }else{
            String concepto;
            Movimiento mov;
            int saldo= cuF.getSaldoCuenta(cuenta.getNumeroCuenta());
            if(saldo-cantidad<0&&operacion.equals("R")){
               throw new RuntimeException("Reintegro mayor que saldo actual");
            }
            if(operacion.equals("I")){
                concepto="Ingreso";
                saldo+=cantidad;
            }else{
                concepto="Reintegro";
                saldo-=cantidad;
            }
            Operacion op=opF.crearOperacionIngresoOReintegro(concepto, em);
            mov= new Movimiento(0, concepto, new Date(), saldo, saldo);
            mov.setOperacion(op);
            mov.setCuenta(cuenta);
            create(mov);
            return mov;
        }
    }
    
}
