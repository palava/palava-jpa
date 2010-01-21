package de.cosmocode.palava.services.persistence;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.cosmocode.palava.core.request.HttpRequest;
import de.cosmocode.palava.core.request.HttpRequestFilter;

/**
 * An {@link HttpRequestFilter} which closes the current persistence
 * context, if there is one.
 *
 * @author Willi Schoenborn
 */
public abstract class PersistenceFilter implements HttpRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(PersistenceFilter.class);

    /**
     * Gets the underlying {@link EntityManager} instance.
     * 
     * @return an EntityManager
     */
    protected abstract EntityManager getEntityManager();
    
    @Override
    public void before(HttpRequest request) {
        // do nothing
    }
    
    @Override
    public void after(HttpRequest request) {
        // TODO make sure we are not opening a session here!
        getEntityManager().close();
        log.debug("Closed persistence context");
    }
    
}
