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

import java.lang.annotation.Annotation;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import de.cosmocode.palava.core.inject.RebindModule;
import de.cosmocode.palava.scope.UnitOfWork;

/**
 * {@link Module} which binds the {@link PersistenceService} interface
 * to its default implementation and registers as a {@link Provider} for {@link EntityManager}s.
 *
 * @author Willi Schoenborn
 */
public final class JpaModule implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(PersistenceService.class).to(DefaultPersistenceService.class).in(Singleton.class);
        binder.bind(EntityManagerFactory.class).to(PersistenceService.class).in(Singleton.class);
        binder.bind(EntityManager.class).toProvider(PersistenceService.class).in(UnitOfWork.class);
    }
    
    /**
     * Creates a new {@link Module} which does the same as {@link JpaModule} but uses the
     * specified binding annotation.
     * 
     * @deprecated use {@link #annotatedWith(Class, String)}
     * @since 3.1
     * @param annotation the binding annotation
     * @return a new module
     * @throws NullPointerException if annotation is null
     */
    @Deprecated
    public static Module annotatedWith(Class<? extends Annotation> annotation) {
        return new AnnotatedJpaModule(annotation);
    }

    /**
     * Creates a new {@link Module} which does the same as {@link JpaModule} but uses the
     * specified binding annotation.
     * 
     * @since 3.2
     * @param annotation the binding annotation
     * @param prefix the config prefix
     * @return a new module
     * @throws NullPointerException if annotation is null
     */
    public static RebindModule annotatedWith(Class<? extends Annotation> annotation, String prefix) {
        return new AnnptatedRebindingJpaModule(annotation, prefix);
    }

}
