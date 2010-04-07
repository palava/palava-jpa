package de.cosmocode.palava.jpa;

import javax.persistence.EntityTransaction;

import com.google.common.base.Preconditions;

/**
 * Abstract decorator for {@link EntityTransaction}s.
 *
 * @author Willi Schoenborn
 */
abstract class ForwardingTransaction implements EntityTransaction {

    private final EntityTransaction tx;
    
    public ForwardingTransaction(EntityTransaction tx) {
        this.tx = Preconditions.checkNotNull(tx, "Tx");
    }
    
    @Override
    public void begin() {
        tx.begin();
    }

    @Override
    public void commit() {
        tx.commit();
    }

    @Override
    public boolean getRollbackOnly() {
        return tx.getRollbackOnly();
    }

    @Override
    public boolean isActive() {
        return tx.isActive();
    }

    @Override
    public void rollback() {
        tx.rollback();
    }

    @Override
    public void setRollbackOnly() {
        tx.setRollbackOnly();
    }
    
}
