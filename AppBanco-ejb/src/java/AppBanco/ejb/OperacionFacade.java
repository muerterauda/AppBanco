/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AppBanco.ejb;

import AppBanco.entity.Empleado;
import AppBanco.entity.Operacion;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author user
 */
@Stateless
public class OperacionFacade extends AbstractFacade<Operacion> {

    @PersistenceContext(unitName = "AppBanco-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OperacionFacade() {
        super(Operacion.class);
    }
    /**
     * CRea una operacion de Ingreso o Reintegro
    @param tipo Debe ser Ingreso o Reintegro
    @param em No debe ser null
    */
    public Operacion crearOperacionIngresoOReintegro(String tipo, Empleado em){
        Operacion op=null;
        op= new Operacion(4, tipo);
        op.setEmpleado(em);
        create(op);
        return op;
    }
}
