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

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import com.google.common.collect.Iterators;
import com.google.common.collect.UnmodifiableIterator;
import com.google.inject.Provider;

import de.cosmocode.palava.jpa.Transactional;
import de.cosmocode.palava.model.base.EntityBase;

/**
 * Abstract skeleton implementation of the {@link ReadOnlyEntityService} interface.
 *
 * @author Willi Schoenborn
 * @param <T> the generic entity type
 */
public abstract class AbstractReadOnlyEntityService<T extends EntityBase> implements ReadOnlyEntityService<T> {

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

    @Transactional
    @Override
    public T get(long identifier) {
        return entityManager().find(entityClass(), identifier);
    }

    @Transactional
    @Override
    public T read(long identifier) {
        final T entity = get(identifier);
        if (entity == null) {
            throw new PersistenceException(String.format("No entity found for id #%s", identifier));
        } else {
            return entity;
        }
    }

    @Transactional
    @Override
    @SuppressWarnings("unchecked")
    public T read(Query query, Object... parameters) {
        return (T) prepare(query, parameters).getSingleResult();
    }

    @Transactional
    @Override
    public T read(String queryName, Object... parameters) {
        return read(entityManager().createNamedQuery(queryName), parameters);
    }

    @Transactional
    @Override
    public T reference(long identifier) {
        return entityManager().getReference(entityClass(), identifier);
    }

    @Transactional
    @Override
    @SuppressWarnings("unchecked")
    public List<T> list(Query query, Object... parameters) {
        return prepare(query, parameters).getResultList();
    }

    @Transactional
    @Override
    public List<T> list(String queryName, Object... parameters) {
        return list(entityManager().createNamedQuery(queryName), parameters);
    }

    @Transactional
    @Override
    public List<T> iterate() {
        final String query = String.format("from %s", entityClass().getSimpleName());
        return list(entityManager().createQuery(query));
    }

    @Transactional
    @Override
    public Iterable<T> iterate(final int batchSize) {
        return new Iterable<T>() {
            
            @Override
            public Iterator<T> iterator() {
                return new PreloadingIterator(batchSize);
            }
            
        };
    }
    
    /**
     * A preloading iterator which uses a configurable batch size.
     *
     * @author Willi Schoenborn
     */
    private final class PreloadingIterator extends UnmodifiableIterator<T> {

        private final int batchSize;
        private final Query query;
        
        private int nextIndex;
        
        private Iterator<T> current = Iterators.emptyIterator();
        private Iterator<T> next;

        public PreloadingIterator(int batchSize) {
            this.batchSize = batchSize;
            final String jpql = String.format("from {}", entityClass().getSimpleName());
            this.query = entityManager().createQuery(jpql).setMaxResults(batchSize);
            preload();
        }
        
        private void preload() {
            query.setFirstResult(nextIndex);
            next = list(query).iterator();
            // TODO make sure overflows *work* as expected 
            nextIndex += batchSize;
        }

        @Override
        public boolean hasNext() {
            return current.hasNext() || next.hasNext();
        }
       
        @Override
        public T next() {
            if (current.hasNext()) {
                return current.next();
            } else if (next.hasNext()) {
                current = next;
                preload();
                return current.next();
            } else {
                throw new NoSuchElementException();
            }
        }
        
    }

    @Transactional
    @Override
    @SuppressWarnings("unchecked")
    public <P> P projection(Query query, Object... parameters) {
        return (P) prepare(query, parameters).getSingleResult();
    }

    @Transactional
    @Override
    public <P> P projection(String queryName, Object... parameters) {
        return this.<P>projection(entityManager().createNamedQuery(queryName), parameters);
    }

    @Transactional
    @Override
    @SuppressWarnings("unchecked")
    public <P> P[] projections(Query query, Object... parameters) {
        return (P[]) prepare(query, parameters).getSingleResult();
    }

    @Transactional
    @Override
    public <P> P[] projections(String queryName, Object... parameters) {
        return this.<P>projections(entityManager().createNamedQuery(queryName), parameters);
    }

    @Transactional
    @Override
    @SuppressWarnings("unchecked")
    public <P> List<P> projectionList(Query query, Object... parameters) {
        return prepare(query, parameters).getResultList();
    }

    @Transactional
    @Override
    public <P> List<P> projectionList(String queryName, Object... parameters) {
        return projectionList(entityManager().createNamedQuery(queryName), parameters);
    }

    @Transactional
    @Override
    @SuppressWarnings("unchecked")
    public <P> List<P[]> projectionsList(Query query, Object... parameters) {
        return prepare(query, parameters).getResultList();
    }

    @Transactional
    @Override
    public <P> List<P[]> projectionsList(String queryName, Object... parameters) {
        return projectionsList(entityManager().createNamedQuery(queryName), parameters);
    }
    
    @Override
    public Query prepare(Query query, Object... parameters) {
        for (int i = 0; i < parameters.length; i++) {
            query.setParameter(i + 1, parameters[i]);
        }
        return query;
    }
    
    @Override
    public Query prepare(String queryName, Object... parameters) {
        return prepare(entityManager().createNamedQuery(queryName), parameters);
    }
    
}
