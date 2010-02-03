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

import javax.persistence.EntityNotFoundException;
import javax.persistence.NonUniqueResultException;
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
    
    /**
     * Retrievs an entity from the database.
     * 
     * @param query the query being executed
     * @param parameters the parameters this query needs
     * @return the single entity found using the given query
     * @throws EntityNotFoundException if there is no matching entity
     * @throws NonUniqueResultException if there is more than one matching entity
     */
    T read(Query query, Object... parameters);

    /**
     * Retrievs an entity from the database.
     * 
     * @param queryName the name of the query being executed
     * @param parameters the parameters this query needs
     * @return the single entity found using the given query
     * @throws EntityNotFoundException if there is no matching entity
     * @throws NonUniqueResultException if there is more than one matching entity
     */
    T read(String queryName, Object... parameters);
    
    /**
     * Retrieves a reference to an entity without
     * actually loading it to allow managing relationships
     * without the need to query every relative.
     * 
     * @param identifier the entity's identifer
     * @return a reference to the entity with the given identifer
     * @throws EntityNotFoundException if there is no matching entity
     */
    T reference(long identifier);

    /**
     * Retrievs a list of entities from the database.
     * 
     * @param query the query being executed
     * @param parameters the parameters this query needs
     * @return the single entity found using the given query
     */
    List<T> list(Query query, Object... parameters);

    /**
     * Retrievs a list of entities from the database.
     * 
     * @param queryName the name of the query being executed
     * @param parameters the parameters this query needs
     * @return the single entity found using the given query
     */
    List<T> list(String queryName, Object... parameters);
    
    /**
     * Retrievs a list of all entities of the associated type from the database.
     * 
     * @return a list of all entites
     */
    List<T> all();
    
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
    
    /**
     * Retrieves a projection, a single column, single row value.
     * 
     * @param <P> the generic value type
     * @param query the query being executed
     * @param parameters the parameters this query needs
     * @return the value found by the given query
     */
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
