/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package senadi.com.ditramites.dao.ren;

/**
 *
 * @author Michael
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author michael
 * @param <T>
 */
public abstract class DAOAbstractRen<T> {

    private T instancia;

    @PersistenceContext
    private EntityManager entityManager;

    public DAOAbstractRen(T t) {
        instancia = t;
        entityManager = EntityManagerUtilRen.getEntityManager();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public T getInstancia() {
        return instancia;
    }

    public void setInstancia(T instancia) {
        this.instancia = instancia;
    }

    public void persist() throws Exception {
        this.entityManager.getTransaction().begin();
        try {
            this.entityManager.persist(instancia);
            this.entityManager.getTransaction().commit();
            this.entityManager.close();
        } catch (Exception e) {
            if (this.entityManager.getTransaction().isActive()) // Arreglar el Error y reintentar
            {
                this.entityManager.getTransaction().rollback();
            }
            throw e;
        }
    }

    public void update() throws Exception {
        this.entityManager.getTransaction().begin();
        try {
            instancia = this.entityManager.merge(instancia);
            this.entityManager.getTransaction().commit();
            this.entityManager.close();
        } catch (Exception e) {
            if (this.entityManager.getTransaction().isActive()) {
                this.entityManager.getTransaction().rollback(); // Arreglar el Error y reiintentar
            }
            throw e;
        }
    }

    public void remove() throws Exception {
        this.entityManager.getTransaction().begin();
        try {
            this.entityManager.remove(instancia);
            this.entityManager.getTransaction().commit();
            this.entityManager.close();
        } catch (Exception e) {
            if (this.entityManager.getTransaction().isActive()) {
                this.entityManager.getTransaction().rollback(); // Arreglar el Error y reiintentar
            }
            throw e;
        }
    }

    public void clearInstance() {
        this.getEntityManager().clear();
    }

    public T findById(Long id) {
        return (T) this.entityManager.find(this.instancia.getClass(), id);
    }

    public abstract List<T> buscarTodos();

}
