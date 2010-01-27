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

package de.cosmocode.palava.services.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Query;

import com.google.common.base.Preconditions;

import de.cosmocode.palava.bridge.scope.Destroyable;
import de.cosmocode.patterns.Decorator;

/**
 * A {@link Decorator} for an {@link EntityManager} which adds {@link Destroyable}
 * behaviour.
 *
 * @author Willi Schoenborn
 */
@Decorator(EntityManager.class)
public final class DestroyableEntityManager implements EntityManager, Destroyable {
    
    private final EntityManager manager;

    public DestroyableEntityManager(EntityManager manager) {
        this.manager = Preconditions.checkNotNull(manager, "Manager");
    }
    
    @Override
    public void clear() {
        manager.clear();
    }

    @Override
    public void close() {
        manager.close();
    }

    @Override
    public boolean contains(Object entity) {
        return manager.contains(entity);
    }

    @Override
    public Query createNamedQuery(String name) {
        return manager.createNamedQuery(name);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Query createNativeQuery(String sqlString, Class resultClass) {
        return manager.createNativeQuery(sqlString, resultClass);
    }

    @Override
    public Query createNativeQuery(String sqlString, String resultSetMapping) {
        return manager.createNativeQuery(sqlString, resultSetMapping);
    }

    @Override
    public Query createNativeQuery(String sqlString) {
        return manager.createNativeQuery(sqlString);
    }

    @Override
    public Query createQuery(String qlString) {
        return manager.createQuery(qlString);
    }

    @Override
    public <T> T find(Class<T> entityClass, Object primaryKey) {
        return manager.find(entityClass, primaryKey);
    }

    @Override
    public void flush() {
        manager.flush();
    }

    @Override
    public Object getDelegate() {
        return manager.getDelegate();
    }

    @Override
    public FlushModeType getFlushMode() {
        return manager.getFlushMode();
    }

    @Override
    public <T> T getReference(Class<T> entityClass, Object primaryKey) {
        return manager.getReference(entityClass, primaryKey);
    }

    @Override
    public EntityTransaction getTransaction() {
        return manager.getTransaction();
    }

    @Override
    public boolean isOpen() {
        return manager.isOpen();
    }

    @Override
    public void joinTransaction() {
        manager.joinTransaction();
    }

    @Override
    public void lock(Object entity, LockModeType lockMode) {
        manager.lock(entity, lockMode);
    }

    @Override
    public <T> T merge(T entity) {
        return manager.merge(entity);
    }

    @Override
    public void persist(Object entity) {
        manager.persist(entity);
    }

    @Override
    public void refresh(Object entity) {
        manager.refresh(entity);
    }

    @Override
    public void remove(Object entity) {
        manager.remove(entity);
    }

    @Override
    public void setFlushMode(FlushModeType flushMode) {
        manager.setFlushMode(flushMode);
    }
    
    @Override
    public void destroy() {
        if (manager.isOpen()) manager.close();
    }
    
}
