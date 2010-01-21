package de.cosmocode.palava.services.persistence;

import javax.persistence.EntityManager;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provider;
import com.google.inject.servlet.RequestScoped;

/**
 * {@link Module} which binds the {@link PersistenceService} interface
 * to its default implementation and registers as a {@linkplain RequestScoped request scoped}
 * {@link Provider} for {@link EntityManager}s.
 *
 * @author Willi Schoenborn
 */
public final class PersistenceModule implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(PersistenceService.class).to(DefaultPersistenceService.class);
        binder.bind(EntityManager.class).toProvider(PersistenceService.class).in(RequestScoped.class);
    }

}
