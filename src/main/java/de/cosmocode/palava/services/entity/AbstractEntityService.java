/**
 * palava - a java-php-bridge
 * Copyright (C) 2007  CosmoCode GmbH
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package de.cosmocode.palava.services.entity;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import de.cosmocode.palava.model.base.Copyable;
import de.cosmocode.palava.model.base.EntityBase;

public abstract class AbstractEntityService<T extends EntityBase> implements EntityService<T> {

    protected abstract EntityManager getEntityManager();
    
    protected abstract Class<T> getEntityClass();
    
    @Override
    public T create(T entity) {
        entity.setCreated();
        getEntityManager().persist(entity);
        return entity;
    }

    @Override
    public T read(long identifier) {
        return getEntityManager().find(getEntityClass(), identifier);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public T read(Query query, Object... parameters) {
        return (T) prepare(query, parameters).getSingleResult();
    }
    
    @Override
    public T read(String queryName, Object... parameters) {
        return read(getEntityManager().createNamedQuery(queryName), parameters);
    }
    
    @Override
    public T reference(long identifier) {
        return getEntityManager().getReference(getEntityClass(), identifier);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public List<T> list(Query query, Object... parameters) {
        return prepare(query, parameters).getResultList();
    }
    
    @Override
    public List<T> list(String queryName, Object... parameters) {
        return list(getEntityManager().createNamedQuery(queryName), parameters);
    }

    @Override
    public T update(T entity) {
        entity.setModified();
        return entity;
    }

    @Override
    public <C extends Copyable<T>> T createCopy(C entity) {
        return create(entity.copy());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <P> P projection(Query query, Object... parameters) {
        return (P) prepare(query, parameters).getSingleResult();
    }

    @Override
    public <P> P projection(String queryName, Object... parameters) {
        return projection(getEntityManager().createNamedQuery(queryName), parameters);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <P> P[] projections(Query query, Object... parameters) {
        return (P[]) prepare(query, parameters).getSingleResult();
    }
    
    @Override
    public <P> P[] projections(String queryName, Object... parameters) {
        return projections(getEntityManager().createNamedQuery(queryName), parameters);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public <P> List<P> projectionList(Query query, Object... parameters) {
        return prepare(query, parameters).getResultList();
    }
    
    @Override
    public <P> List<P> projectionList(String queryName, Object... parameters) {
        return projectionList(getEntityManager().createNamedQuery(queryName), parameters);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public <P> List<P[]> projectionsList(Query query, Object... parameters) {
        return prepare(query, parameters).getResultList();
    }
    
    @Override
    public <P> List<P[]> projectionsList(String queryName, Object... parameters) {
        return projectionsList(getEntityManager().createNamedQuery(queryName), parameters);
    }
    
    @Override
    public Query prepare(Query query, Object... parameters) {
        for (int i = 0; i < parameters.length; i++) {
            query.setParameter(i, parameters[i]);
        }
        return query;
    }
    
    @Override
    public Query prepare(String queryName, Object... parameters) {
        return prepare(getEntityManager().createNamedQuery(queryName), parameters);
    }
    
}
