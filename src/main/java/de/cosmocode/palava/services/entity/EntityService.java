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

package de.cosmocode.palava.services.entity;

import java.util.List;

import javax.persistence.Query;

import de.cosmocode.palava.core.Service;
import de.cosmocode.palava.model.base.Copyable;
import de.cosmocode.palava.model.base.EntityBase;

/**
 * A service which allows crud-operations on entites of a specific type.
 *
 * @author Willi Schoenborn
 * @param <T> the generic entity type
 */
public interface EntityService<T extends EntityBase> extends Service {

    /**
     * Saves an entity to the database.
     * 
     * @param entity the entity being peristed
     * @return the persisted entity
     */
    T create(T entity);
    
    /**
     * Retrieves an entity from the database.
     * 
     * @param identifier the entity's identifier
     * @return the entity associated with the given identifier
     *         or null if there is no such entity
     */
    T read(long identifier);
    
    T read(Query query, Object... parameters);
    
    T read(String queryName, Object... parameters);
    
    T reference(long identifier);
    
    List<T> list(Query query, Object... parameters);
    
    List<T> list(String queryName, Object... parameters);
    
    List<T> all();
    
    T update(T entity);
    
    void delete(T entity);
    
    <C extends Copyable<T>> T createCopy(C entity);
    
    <P> P projection(Query query, Object... parameters);
    
    <P> P projection(String queryName, Object... parameters);
    
    <P> P[] projections(Query query, Object... parameters);
    
    <P> P[] projections(String queryName, Object... parameters);
    
    <P> List<P> projectionList(Query query, Object... parameters);
    
    <P> List<P> projectionList(String queryName, Object... parameters);
    
    <P> List<P[]> projectionsList(Query query, Object... parameters);
    
    <P> List<P[]> projectionsList(String queryName, Object... parameters);
    
    Query prepare(String queryName, Object... parameters);
    
    Query prepare(Query query, Object... parameters);
    
}
