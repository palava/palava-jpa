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
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */

package de.cosmocode.palava.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Query;

import com.google.common.collect.ForwardingObject;

import de.cosmocode.patterns.Decorator;

/**
 * Abstract {@link Decorator} for {@link EntityManager}s.
 *
 * @author Willi Schoenborn
 */
public abstract class ForwardingEntityManager extends ForwardingObject implements EntityManager {

    @Override
    protected abstract EntityManager delegate();
    
    @Override
    public void clear() {
        delegate().clear();
    }

    @Override
    public void close() {
        delegate().close();
    }

    @Override
    public boolean contains(Object entity) {
        return delegate().contains(entity);
    }

    @Override
    public Query createNamedQuery(String name) {
        return delegate().createNamedQuery(name);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Query createNativeQuery(String sqlString, Class resultClass) {
        return delegate().createNativeQuery(sqlString, resultClass);
    }

    @Override
    public Query createNativeQuery(String sqlString, String resultSetMapping) {
        return delegate().createNativeQuery(sqlString, resultSetMapping);
    }

    @Override
    public Query createNativeQuery(String sqlString) {
        return delegate().createNativeQuery(sqlString);
    }

    @Override
    public Query createQuery(String qlString) {
        return delegate().createQuery(qlString);
    }

    @Override
    public <T> T find(Class<T> entityClass, Object primaryKey) {
        return delegate().find(entityClass, primaryKey);
    }

    @Override
    public void flush() {
        delegate().flush();
    }

    @Override
    public Object getDelegate() {
        return delegate().getDelegate();
    }

    @Override
    public FlushModeType getFlushMode() {
        return delegate().getFlushMode();
    }

    @Override
    public <T> T getReference(Class<T> entityClass, Object primaryKey) {
        return delegate().getReference(entityClass, primaryKey);
    }

    @Override
    public EntityTransaction getTransaction() {
        return delegate().getTransaction();
    }

    @Override
    public boolean isOpen() {
        return delegate().isOpen();
    }

    @Override
    public void joinTransaction() {
        delegate().joinTransaction();
    }

    @Override
    public void lock(Object entity, LockModeType lockMode) {
        delegate().lock(entity, lockMode);
    }

    @Override
    public <T> T merge(T entity) {
        return delegate().merge(entity);
    }

    @Override
    public void persist(Object entity) {
        delegate().persist(entity);
    }

    @Override
    public void refresh(Object entity) {
        delegate().refresh(entity);
    }

    @Override
    public void remove(Object entity) {
        delegate().remove(entity);
    }

    @Override
    public void setFlushMode(FlushModeType flushMode) {
        delegate().setFlushMode(flushMode);
    }
    
}
