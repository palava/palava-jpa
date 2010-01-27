/**
 * palava - a java-php-bridge
 * Copyright (C) 2007-2010  CosmoCode GmbH
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

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
import com.google.inject.servlet.RequestScoped;

import de.cosmocode.palava.core.lifecycle.Disposable;

/**
 * Default implementation of the {@link PersistenceService} interface.
 * 
 * TODO consider using Map-configuration
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
    @RequestScoped
    public EntityManager get() {
        return createEntityManager();
    }

    @Override
    public EntityManager createEntityManager() {
        return new DestroyableEntityManager(factory.createEntityManager());
    }

    @Override
    @SuppressWarnings("unchecked")
    public EntityManager createEntityManager(Map map) {
        return new DestroyableEntityManager(factory.createEntityManager(map));
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
