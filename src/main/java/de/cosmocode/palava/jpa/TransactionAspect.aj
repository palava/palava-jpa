package de.cosmocode.palava.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

import org.aspectj.lang.annotation.SuppressAjWarnings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Provider;

import de.cosmocode.palava.ipc.IpcConnection;

public final aspect TransactionAspect issingleton() {

    private static final Logger LOG = LoggerFactory.getLogger(TransactionAspect.class);

    private Provider<EntityManager> currentManager;
    private Provider<IpcConnection> currentConnection;
    
    @Inject
    public void setProvider(Provider<EntityManager> manager, Provider<IpcConnection> connection) {
        this.currentManager = Preconditions.checkNotNull(manager, "Manager");
        this.currentConnection = Preconditions.checkNotNull(connection, "Connection");
    }
    
    pointcut createInjector(): call(Injector Guice.createInjector(..));
    
    @SuppressAjWarnings("adviceDidNotMatch")
    after() returning (Injector injector): createInjector() {
        LOG.trace("Injecting members on {}", this);
        if (currentManager == null) {
            injector.injectMembers(this);
        } else {
            throw new IllegalStateException("An injector has already been created");
        }
    }

    pointcut transactional(): execution(@Transactional * *.*(..));

    @SuppressAjWarnings("adviceDidNotMatch")
    Object around(): transactional() {
        final IpcConnection connection = currentConnection.get();
        if (connection == null) return proceed();
        final Key<EntityManager> key = Key.get(EntityManager.class);
        EntityManager manager = connection.get(key);
        if (manager == null) {
            manager = currentManager.get();
        }
        final EntityTransaction tx = manager.getTransaction();
        final boolean localTx = !tx.isActive();
        if (localTx) {
            LOG.trace("Beginning automatic transaction");
            tx.begin();
        } else {
            LOG.trace("Transaction already active");
        }
        
        final Object returnValue;
        
        try {
            returnValue = proceed();
        } catch (RuntimeException e) {
            LOG.error("Exception inside automatic transaction context", e);
            tx.rollback();
            throw e;
        }
        
        try {
            if (localTx) {
                tx.commit();
                LOG.trace("Committed automatic transaction");
            }
            return returnValue;
        } catch (PersistenceException e) {
            LOG.error("Commit in automatic transaction context failed", e);
            tx.rollback();
            throw e;
        }
    }
    
}
