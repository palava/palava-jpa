package de.cosmocode.palava.jpa;

import java.lang.annotation.Annotation;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.google.common.base.Preconditions;
import com.google.inject.Binder;
import com.google.inject.Key;
import com.google.inject.Module;
import com.google.inject.Singleton;

import de.cosmocode.palava.scope.UnitOfWork;

/**
 * A variation of the {@link JpaModule} which allows binding using an
 * annotation.
 *
 * @since 3.1
 * @author Willi Schoenborn
 */
final class AnnotatedJpaModule implements Module {

    private final Class<? extends Annotation> annotation;
    
    public AnnotatedJpaModule(Class<? extends Annotation> annotation) {
        this.annotation = Preconditions.checkNotNull(annotation, "Annotation");
    }

    @Override
    public void configure(Binder binder) {
        binder.bind(PersistenceService.class).annotatedWith(annotation).
            to(DefaultPersistenceService.class).in(Singleton.class);
        binder.bind(EntityManagerFactory.class).annotatedWith(annotation).
            to(PersistenceService.class).in(Singleton.class);
        binder.bind(EntityManager.class).annotatedWith(annotation).
            toProvider(Key.get(PersistenceService.class, annotation)).in(UnitOfWork.class);
        
    }

}
