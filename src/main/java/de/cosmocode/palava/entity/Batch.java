package de.cosmocode.palava.entity;

import javax.persistence.EntityManager;

import com.google.gag.annotation.enforceable.Noop;

import de.cosmocode.collections.Procedure;

/**
 * A set of reusable {@link Procedure}s which can be applied to
 * {@link EntityService#each(Procedure, int, Procedure)}.
 *
 * @since 2.3
 * @author Willi Schoenborn
 */
public enum Batch implements Procedure<EntityManager> {

    /**
     * Performs no operation.
     */
    NOOP {

        @Noop
        @Override
        public void apply(EntityManager manager) {
            // do nothing
        }
        
    },
    
    /**
     * Calls {@link EntityManager#flush()}.
     */
    FLUSH {

        @Override
        public void apply(EntityManager manager) {
            manager.flush();
        }
        
    },
    
    /**
     * Calls {@link EntityManager#clear()}.
     */
    CLEAR {

        @Override
        public void apply(EntityManager manager) {
            manager.clear();
        }
        
    },
    
    /**
     * Calls {@link EntityManager#flush()} and {@link EntityManager#clear()} in
     * that order.
     */
    FLUSH_AND_CLEAR {

        @Override
        public void apply(EntityManager manager) {
            manager.flush();
            manager.clear();
        }
        
    };
    
    @Override
    public abstract void apply(EntityManager input);
    
}
