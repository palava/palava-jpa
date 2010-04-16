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

package de.cosmocode.palava.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.cosmocode.collections.Procedure;
import de.cosmocode.palava.jpa.Transactional;
import de.cosmocode.palava.model.base.Copyable;
import de.cosmocode.palava.model.base.EntityBase;

/**
 * Abstract base implementation of the {@link EntityService} interface.
 *
 * <p>
 *   <strong>Note</strong>: This implementation does not provide a meaningful
 *   {@link EntityService#delete(EntityBase)} method. Decisions about deleting
 *   or hiding entites are left to sub-classes. Create and update are marked
 *   with @Transactional to use or create transactions for database actions.
 * </p>
 *
 * @author Willi Schoenborn
 * @param <T> generic entity type
 */
public abstract class AbstractEntityService<T extends EntityBase> extends AbstractReadOnlyEntityService<T>
    implements EntityService<T> {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractEntityService.class);

    @Transactional
    @Override
    public T create(T entity) {
        LOG.debug("Creating {} in database", entity);
        entityManager().persist(entity);
        return entity;
    }

    @Transactional
    @Override
    public T update(T entity) {
        // we don't have to do anything here
        // @Transactional will commit the update in the near future
        return entity;
    }

    @Transactional
    @Override
    public void each(Procedure<? super T> procedure) {
        for (T entity : iterate()) {
            procedure.apply(entity);
        }
    }

    @Transactional
    @Override
    public void each(Procedure<? super T> procedure, int batchSize) {
        int i = 1;
        for (T entity : iterate(batchSize)) {
            procedure.apply(entity);
            // flush every 'batchSize' elements
            if (i++ % batchSize == 0) {
                entityManager().flush();
                // TODO clear?
            }
        }
    }

    @Transactional
    @Override
    public <C extends Copyable<T>> T createCopy(C entity) {
        return create(entity.copy());
    }

}
