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

import com.google.common.base.Preconditions;

import de.cosmocode.palava.scope.Destroyable;
import de.cosmocode.patterns.Decorator;

/**
 * A {@link Decorator} for an {@link EntityManager} which adds {@link Destroyable}
 * behaviour.
 *
 * @author Willi Schoenborn
 */
@Decorator(EntityManager.class)
public final class DestroyableEntityManager extends ForwardingEntityManager implements Destroyable {
    
    private final EntityManager manager;

    public DestroyableEntityManager(EntityManager manager) {
        this.manager = Preconditions.checkNotNull(manager, "Manager");
    }
    
    @Override
    protected EntityManager delegate() {
        return manager;
    }
    
    @Override
    public void close() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void destroy() {
        if (manager.isOpen()) manager.close();
    }
    
}
