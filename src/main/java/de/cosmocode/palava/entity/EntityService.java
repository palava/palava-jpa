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

import javax.persistence.PersistenceException;

import de.cosmocode.palava.model.base.Copyable;
import de.cosmocode.palava.model.base.EntityBase;

/**
 * A service which allows crud-operations on entites of a specific type.
 *
 * @author Willi Schoenborn
 * @param <T> the generic entity type
 */
public interface EntityService<T extends EntityBase> extends ReadOnlyEntityService<T> {

    /**
     * Saves an entity to the database.
     * 
     * @param entity the entity being peristed
     * @return the persisted entity
     * @throws IllegalStateException if underlying entitymanager is closed
     * @throws PersistenceException if the entity already exist or a transaction is required
     */
    T create(T entity);
    
    /**
     * Updates an entities state in the database.
     * 
     * @param entity the entity being updated
     * @return the updated entity
     */
    T update(T entity);
    
    /**
     * Deletes an entity.
     * 
     * <p>
     *   <strong>Note</strong>: Implementations are free to decide whether
     *   entites should be removed from the database or hidden using a custom
     *   flag.
     * </p>
     * 
     * @param entity the entity being deleted
     */
    void delete(T entity);
    
    /**
     * Creates a copy of the given entity and saves it to the database.
     * 
     * @param <C> the generic copyable entity type
     * @param entity the entity being copied
     * @return the persisted copy
     */
    <C extends Copyable<T>> T createCopy(C entity);
    
}
