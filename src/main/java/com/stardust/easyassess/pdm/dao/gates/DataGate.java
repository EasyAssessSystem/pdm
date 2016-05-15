package com.stardust.easyassess.pdm.dao.gates;


import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

/**
 * Data Gate Implementation is based on Entity Manager
 * which supplies more flexibly, you can use it in your
 * service if the repository can not realize your requirement
 * but always consider the repository as your first choice.
 */
public abstract class DataGate<T> {

    //@PersistenceContext
    @Autowired
    protected EntityManager em;

    protected abstract Class getEntityClass();

    protected abstract String getKeyField();

    protected String getTableName() {
        return ((Table)getEntityClass().getAnnotation(Table.class)).name();
    }

    protected String getEntityName() {
        return ((Table)getEntityClass().getAnnotation(Entity.class)).name();
    }

    protected T fetchSingle(Query query) {
        T model;
        try {
            model = (T) query.getSingleResult();
        } catch (NoResultException e) {
            model = null;
        }
        return model;
    }

    public T find(Long id) {
        Query query = em.createQuery("select t from " + getEntityName() + " t where t.id=?id",  getEntityClass());
        query.setParameter("id", id);

        return fetchSingle(query);
    }

    public T find(String key) {
        Query query = em.createNativeQuery("select * from " + getTableName() + " where " + getKeyField() + "=:keyVal", getEntityClass());
        query.setParameter("keyVal", key);

        return fetchSingle(query);
    }

    // add more methods here...
}
