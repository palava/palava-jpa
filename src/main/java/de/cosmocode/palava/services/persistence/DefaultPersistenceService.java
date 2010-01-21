package de.cosmocode.palava.services.persistence;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import de.cosmocode.palava.core.service.lifecycle.Disposable;

/**
 * Default implementation of the {@link PersistenceService} interface.
 *
 * @author Willi Schoenborn
 */
@Singleton
final class DefaultPersistenceService implements PersistenceService, Disposable {

    private static final Logger log = LoggerFactory.getLogger(DefaultPersistenceService.class);

    private final EntityManagerFactory factory;
    
    @Inject
    public DefaultPersistenceService(@Named("persistence.unitName") String unitName) {
        this.factory = Persistence.createEntityManagerFactory(unitName);
        log.info("Created {}", factory);
    }
    
    @Override
    public EntityManager get() {
        return createEntityManager();
    }

    @Override
    public EntityManager createEntityManager() {
        return factory.createEntityManager();
    }

    @Override
    @SuppressWarnings("unchecked")
    public EntityManager createEntityManager(Map map) {
        return factory.createEntityManager(map);
    }

    @Override
    public boolean isOpen() {
        return factory.isOpen();
    }

    @Override
    public void close() {
        log.info("Closing {}", factory);
        factory.close();
    }
    
    @Override
    public void dispose() {
        close();
    }

}
