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

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import de.cosmocode.palava.ipc.IpcConnectionScoped;
import de.cosmocode.palava.scope.UnitOfWork;

/**
 * {@link Module} which binds the {@link PersistenceService} interface
 * to its default implementation and registers as a {@link Provider} for {@link EntityManager}s.
 *
 * @author Willi Schoenborn
 */
public final class PersistenceModule implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(PersistenceService.class).to(DefaultPersistenceService.class).in(Singleton.class);
        binder.bind(EntityManagerFactory.class).to(PersistenceService.class).in(Singleton.class);
    }
    
    /**
     * Provides an {@link IpcConnectionScoped} {@link EntityManager} annotated with {@link UnitOfWork}.
     * 
     * @param service the required {@link PersistenceService} which produces {@link EntityManager}.
     * @return a {@link DestroyableEntityManager}
     */
    @Provides
    @UnitOfWork
    EntityManager getEntityManager(PersistenceService service) {
        return new DestroyableEntityManager(service.createEntityManager()); 
    }

}
