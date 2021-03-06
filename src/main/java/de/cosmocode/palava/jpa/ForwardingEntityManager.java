/**
 * Copyright 2010 CosmoCode GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.cosmocode.palava.jpa;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.metamodel.Metamodel;

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
    public <T> T find(Class<T> entityClass, Object primaryKey, Map<String, Object> properties) {
        return delegate().find(entityClass, primaryKey, properties);
    }

    @Override
    public <T> T find(Class<T> entityClass, Object primaryKey, LockModeType lockMode) {
        return delegate().find(entityClass, primaryKey, lockMode);
    }

    @Override
    public <T> T find(Class<T> entityClass, Object primaryKey, LockModeType lockMode, Map<String, Object> properties) {
        return delegate().find(entityClass, primaryKey, lockMode, properties);
    }

    @Override
    public void lock(Object entity, LockModeType lockMode, Map<String, Object> properties) {
        delegate().lock(entity, lockMode, properties);
    }

    @Override
    public void refresh(Object entity, Map<String, Object> properties) {
        delegate().refresh(entity, properties);
    }

    @Override
    public void refresh(Object entity, LockModeType lockMode) {
        delegate().refresh(entity, lockMode);
    }

    @Override
    public void refresh(Object entity, LockModeType lockMode, Map<String, Object> properties) {
        delegate().refresh(entity, lockMode, properties);
    }

    @Override
    public void detach(Object entity) {
        delegate().detach(entity);
    }

    @Override
    public LockModeType getLockMode(Object entity) {
        return delegate().getLockMode(entity);
    }

    @Override
    public void setProperty(String propertyName, Object value) {
        delegate().setProperty(propertyName, value);
    }

    @Override
    public Map<String, Object> getProperties() {
        return delegate().getProperties();
    }

    @Override
    public <T> TypedQuery<T> createQuery(CriteriaQuery<T> criteriaQuery) {
        return delegate().createQuery(criteriaQuery);
    }

    @Override
    public <T> TypedQuery<T> createQuery(String qlString, Class<T> resultClass) {
        return delegate().createQuery(qlString, resultClass);
    }

    @Override
    public <T> TypedQuery<T> createNamedQuery(String name, Class<T> resultClass) {
        return delegate().createNamedQuery(name, resultClass);
    }

    @Override
    public <T> T unwrap(Class<T> cls) {
        return delegate().unwrap(cls);
    }

    @Override
    public EntityManagerFactory getEntityManagerFactory() {
        return delegate().getEntityManagerFactory();
    }

    @Override
    public CriteriaBuilder getCriteriaBuilder() {
        return delegate().getCriteriaBuilder();
    }

    @Override
    public Metamodel getMetamodel() {
        return delegate().getMetamodel();
    }

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
    public Query createNativeQuery(String sqlString, @SuppressWarnings("rawtypes") Class resultClass) {
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
