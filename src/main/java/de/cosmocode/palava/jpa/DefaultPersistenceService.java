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

import java.util.Map;
import java.util.Properties;

import javax.persistence.Cache;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FlushModeType;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.metamodel.Metamodel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.name.Named;

import de.cosmocode.palava.core.lifecycle.Disposable;
import de.cosmocode.palava.core.lifecycle.Initializable;
import de.cosmocode.palava.core.lifecycle.LifecycleException;

/**
 * Default implementation of the {@link PersistenceService} interface.
 *
 * @author Willi Schoenborn
 * @author Tobias Sarnowski
 */
final class DefaultPersistenceService implements PersistenceService, Initializable, Disposable {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultPersistenceService.class);

    private final String unitName;

    private EntityManagerFactory factory;

    private Properties properties;
    
    private FlushModeType flushModeType;

    @Inject
    public DefaultPersistenceService(@Named(PersistenceConfig.UNIT_NAME) String unitName) {
        this.unitName = Preconditions.checkNotNull(unitName, "UnitName");
    }

    @Inject(optional = true)
    void setProperties(@Named(PersistenceConfig.PROPERTIES) Properties properties) {
        this.properties = Preconditions.checkNotNull(properties, "Properties");
    }

    @Inject(optional = true)
    void setFlushModeType(@Named(PersistenceConfig.FLUSH_MODE) FlushModeType flushModeType) {
        this.flushModeType = Preconditions.checkNotNull(flushModeType, "FlushModeType");
    }

    @Override
    public void initialize() throws LifecycleException {
        if (flushModeType == null) {
            LOG.info("No FlushMode configured, using default");
        } else {
            LOG.info("Configuring EntityManagers with FlushMode {}", flushModeType);
        }
        
        if (properties == null) {
            LOG.info("Creating entity manager factory");
            this.factory = Persistence.createEntityManagerFactory(unitName);
        } else {
            LOG.info("Creating entity manager factory using {}", properties);
            this.factory = Persistence.createEntityManagerFactory(unitName, properties);
        }
    }

    @Override
    public EntityManager createEntityManager() {
        final EntityManager entityManager = factory.createEntityManager();
        if (flushModeType != null) {
            LOG.trace("Setting FlushMode of {} to {}", entityManager, flushModeType.name());
            entityManager.setFlushMode(flushModeType);
        }
        return entityManager;
    }

    @Override
    public EntityManager createEntityManager(@SuppressWarnings("rawtypes") Map map) {
        final EntityManager entityManager = factory.createEntityManager(map);
        if (flushModeType != null) {
            LOG.trace("Setting FlushMode of {} to {}", entityManager, flushModeType.name());
            entityManager.setFlushMode(flushModeType);
        }
        return entityManager;
    }
    
    @Override
    public EntityManager get() {
        return new DestroyableEntityManager(createEntityManager());
    }

    @Override
    public CriteriaBuilder getCriteriaBuilder() {
        return factory.getCriteriaBuilder();
    }

    @Override
    public Metamodel getMetamodel() {
        return factory.getMetamodel();
    }

    @Override
    public Map<String, Object> getProperties() {
        return factory.getProperties();
    }

    @Override
    public Cache getCache() {
        return factory.getCache();
    }

    @Override
    public PersistenceUnitUtil getPersistenceUnitUtil() {
        return factory.getPersistenceUnitUtil();
    }

    @Override
    public boolean isOpen() {
        return factory.isOpen();
    }

    @Override
    public void close() {
        LOG.info("Closing {}", factory);
        factory.close();
    }

    @Override
    public void dispose() {
        close();
    }

}
