/**
 * palava - a java-php-bridge
 * Copyright (C) 2007-2010  CosmoCode GmbH
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

package de.cosmocode.palava.entity;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Provider;

import de.cosmocode.palava.model.base.Copyable;
import de.cosmocode.palava.model.base.EntityBase;

/**
 * Abstract base implementation of the {@link EntityService} interface.
 * 
 * <p>
 *   <strong>Note</strong>: This implementation does not provide a meaningful
 *   {@link EntityService#delete(EntityBase)} method. Decisions about deleting
 *   or hiding entites are left to sub-classes.
 * </p>
 *
 * @author Willi Schoenborn
 * @param <T>
 */
public abstract class AbstractEntityService<T extends EntityBase> implements EntityService<T> {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractEntityService.class);
    
    /**
     * Provides an {@link EntityManager} this implementation uses to do it's
     * work. Implementations will ususally delegate to an injected {@link Provider}.
     * 
     * @return an {@link EntityManager}
     */
    protected abstract EntityManager entityManager();
    
    /**
     * Provides the class object of this entity type.
     * 
     * @return this entity type's class object
     */
    protected abstract Class<T> entityClass();
    
    @Override
    public T create(T entity) {
        LOG.debug("Creating {} in database", entity);
        entityManager().persist(entity);
        return entity;
    }

    @Override
    public T read(long identifier) {
        return entityManager().find(entityClass(), identifier);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public T read(Query query, Object... parameters) {
        return (T) prepare(query, parameters).getSingleResult();
    }
    
    @Override
    public T read(String queryName, Object... parameters) {
        return read(entityManager().createNamedQuery(queryName), parameters);
    }
    
    @Override
    public T reference(long identifier) {
        return entityManager().getReference(entityClass(), identifier);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public List<T> list(Query query, Object... parameters) {
        return prepare(query, parameters).getResultList();
    }
    
    @Override
    public List<T> list(String queryName, Object... parameters) {
        return list(entityManager().createNamedQuery(queryName), parameters);
    }

    @Override
    public List<T> all() {
        final String query = String.format("from %s", entityClass().getSimpleName());
        return list(entityManager().createQuery(query));
    }
    
    @Override
    public T update(T entity) {
        // what should we do here? flush?
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
        return this.<P>projection(entityManager().createNamedQuery(queryName), parameters);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <P> P[] projections(Query query, Object... parameters) {
        return (P[]) prepare(query, parameters).getSingleResult();
    }
    
    @Override
    public <P> P[] projections(String queryName, Object... parameters) {
        return this.<P>projections(entityManager().createNamedQuery(queryName), parameters);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public <P> List<P> projectionList(Query query, Object... parameters) {
        return prepare(query, parameters).getResultList();
    }
    
    @Override
    public <P> List<P> projectionList(String queryName, Object... parameters) {
        return projectionList(entityManager().createNamedQuery(queryName), parameters);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public <P> List<P[]> projectionsList(Query query, Object... parameters) {
        return prepare(query, parameters).getResultList();
    }
    
    @Override
    public <P> List<P[]> projectionsList(String queryName, Object... parameters) {
        return projectionsList(entityManager().createNamedQuery(queryName), parameters);
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
        return prepare(entityManager().createNamedQuery(queryName), parameters);
    }
    
}
