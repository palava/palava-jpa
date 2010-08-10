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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import de.cosmocode.collections.Procedure;
import de.cosmocode.palava.model.base.Copyable;

/**
 * A service which allows crud-operations on entites of a specific type.
 *
 * @author Willi Schoenborn
 * @param <T> the generic entity type
 */
public interface EntityService<T> extends ReadOnlyEntityService<T> {

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
     * Performs an operation on each element of type T.
     * 
     * @param procedure the command which will be called with each instance of T
     */
    void each(Procedure<? super T> procedure);
    
    /**
     * Performs an operation on each element of type T.
     * 
     * @param procedure the command which will be called with each instance of T
     * @param batchSize the number of iterations between each flush
     * @throws NullPointerException if procedure is null
     */
    void each(Procedure<? super T> procedure, int batchSize);
    
    /**
     * Performs an operation on each element of type T and call the given batch procedure
     * everytime the batch size is hit.
     * 
     * <p>
     *   Note: {@link Batch} supports several reusable {@link Procedure}s for the third parameter.
     * </p>
     * 
     * @since 2.3
     * @param procedure the command which will be called with each instance of T
     * @param batchSize the number of iterations between each flush
     * @param batchProcedure the procedure which is called every time the batch size is hit
     * @throws NullPointerException if procedure or batchProcedure is null
     */
    void each(Procedure<? super T> procedure, int batchSize, Procedure<? super EntityManager> batchProcedure);
    
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
