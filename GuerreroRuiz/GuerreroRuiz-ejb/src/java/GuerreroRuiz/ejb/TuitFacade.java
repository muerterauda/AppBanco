/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuerreroRuiz.ejb;

import GuerreroRuiz.entity.Tuit;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author GRJuanjo
 */
@Stateless
public class TuitFacade extends AbstractFacade<Tuit> {

    @PersistenceContext(unitName = "GuerreroRuiz-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TuitFacade() {
        super(Tuit.class);
    }
    
}
