package de.cosmocode.palava.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;

import de.cosmocode.palava.core.lifecycle.Initializable;
import de.cosmocode.palava.core.lifecycle.LifecycleException;
import de.cosmocode.palava.core.scope.Destroyable;

/**
 * A scoped aware {@link EntityManager} which has an optional scoped dependency
 * to a {@link EntityManager} which will be used if it's provided. A new manager will be created
 * otherwise.
 *
 * @author Willi Schoenborn
 */
final class ScopeAwareEntityManager implements EntityManager, Initializable, Destroyable {

    private static final Logger LOG = LoggerFactory.getLogger(ScopeAwareEntityManager.class);
    
    private final PersistenceService service;

    private EntityManager manager;
    
    private boolean scoped;
    
    @Inject
    public ScopeAwareEntityManager(PersistenceService service) {
        this.service = Preconditions.checkNotNull(service, "Service");
    }

    /**
     * Optional injection point for scoped entity manager.
     * 
     * @param manager the scoped manager
     */
    @Inject(optional = true)
    void setManager(@Scoped EntityManager manager) {
        LOG.trace("Injecting scoped entity manager");
        this.manager = Preconditions.checkNotNull(manager, "Manager");
        scoped = true;
    }
    
    @Override
    public void initialize() throws LifecycleException {
        if (scoped) {
            assert manager != null : "EntityManager should be set by now";
        } else {
            LOG.trace("No scoped entity manager injected. Creating my own...");
            manager = service.createEntityManager();
        }
    }

    @Override
    public EntityTransaction getTransaction() {
        if (scoped) {
            return manager.getTransaction();
        } else {
            LOG.trace("Running outside of scope, returning destroying transaction.");
            return new ForwardingTransaction(manager.getTransaction()) {
                
                @Override
                public void commit() {
                    try {
                        super.commit();
                    } finally {
                        destroy();
                    }
                }
                
                @Override
                public void rollback() {
                    try {
                        super.rollback();
                    } finally {
                        destroy();
                    }
                }
                
            };
        }
    }
    
    @Override
    public void destroy() {
        if (manager.isOpen()) manager.close();
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
    public Query createNativeQuery(String sqlString) {
        return manager.createNativeQuery(sqlString);
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
    
}
