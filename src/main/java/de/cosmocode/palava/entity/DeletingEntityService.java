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

package de.cosmocode.palava.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

/**
 * An abstract {@link EntityService} which provides a {@link #delete(Object)} method.
 *
 * @since 3.3
 * @author Willi Schoenborn
 * @param <T> generic entity type
 */
public abstract class DeletingEntityService<T> extends AbstractEntityService<T> {

    private static final Logger LOG = LoggerFactory.getLogger(DeletingEntityService.class);

    @Override
    public void delete(T entity) {
        Preconditions.checkNotNull(entity, "Entity");
        LOG.debug("Deleting {}", entity);
        entityManager().remove(entity);
    }
    
}
