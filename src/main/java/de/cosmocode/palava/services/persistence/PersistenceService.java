package de.cosmocode.palava.services.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.google.inject.Provider;

import de.cosmocode.palava.core.service.Service;

/**
 * A {@link Service} adaption of the {@link EntityManagerFactory} interface.
 *
 * @author Willi Schoenborn
 */
public interface PersistenceService extends EntityManagerFactory, Service, Provider<EntityManager> {
    
}
