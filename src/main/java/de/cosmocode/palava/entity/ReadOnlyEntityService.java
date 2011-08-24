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

import com.google.common.collect.UnmodifiableIterator;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * A service which allows readonly-operations on entites of a specific type.
 *
 * @author Willi Schoenborn
 * @param <T> the generic entity type
 * @since 2.0-SNAPSHOT
 */
public interface ReadOnlyEntityService<T> {

    /**
     * Retrieves an entity from the database.
     * 
     * @param identifier the entity's identifier
     * @return the entity associated with the given identifier
     *         or null if there is no such entity
     */
    T get(Object identifier);
    
    /**
     * Retrieves an entity from the database.
     * 
     * @param identifier the entity's identifier
     * @return the entity associated with the given identifier
     *         or null if there is no such entity
     * @throws PersistenceException if there is no entity with the given identifier
     */
    T read(Object identifier);
    
    /**
     * Retrievs an entity from the database.
     *
     * @deprecated use {@link #read(javax.persistence.TypedQuery, Object...)}
     * @param query the query being executed
     * @param parameters the parameters the query needs
     * @return the single entity found using the given query
     * @throws NoResultException if there is no matching entity
     * @throws NonUniqueResultException if there is more than one matching entity
     */
    @Deprecated
    T read(Query query, Object... parameters);

    /**
     * Retrievs an entity from the database.
     *
     * @param query the query being executed
     * @param parameters the parameters the query needs
     * @return the single entity found using the given query
     * @throws NoResultException if there is no matching entity
     * @throws NonUniqueResultException if there is more than one matching entity
     */
    T read(TypedQuery<T> query, Object... parameters);

    /**
     * Retrievs an entity from the database.
     * 
     * @param queryName the name of the query being executed
     * @param parameters the parameters the query needs
     * @return the single entity found using the given query
     * @throws NoResultException if there is no matching entity
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
     * @deprecated use {@link #list(javax.persistence.TypedQuery, Object...)}
     * @param query the query being executed
     * @param parameters the parameters the query needs
     * @return the single entity found using the given query
     */
    @Deprecated
    List<T> list(Query query, Object... parameters);

    /**
     * Retrievs a list of entities from the database.
     *
     * @param query the query being executed
     * @param parameters the parameters the query needs
     * @return the single entity found using the given query
     */
    List<T> list(TypedQuery<T> query, Object... parameters);

    /**
     * Retrievs a list of entities from the database.
     * 
     * @param queryName the name of the query being executed
     * @param parameters the parameters the query needs
     * @return the single entity found using the given query
     */
    List<T> list(String queryName, Object... parameters);
    
    /**
     * Retrievs a list of all entities of the associated type from the database.
     * 
     * @return a list of all entites
     */
    List<T> iterate();

    /**
     * Iterates through all entities of type T using the specified batch size.
     * 
     * @param batchSize the amount of preloaded elements
     * @return an {@link Iterable} of {@link UnmodifiableIterator}s over all elements of T
     * @throws IllegalArgumentException if batchSize is not positive
     */
    Iterable<T> iterate(int batchSize);

    /**
     * Iterate s through all entities of Type T matching the given entity using the specified batch size.
     *
     * @param query the query being used to fetch entities
     * @param batchSize the amount of preloaded entities
     * @return an {@link Iterable} of {@link UnmodifiableIterator}s over all elements of T
     * @throws IllegalArgumentException if batchSize is not positive
     */
    Iterable<T> iterate(TypedQuery<T> query, int batchSize);
    
    /**
     * Retrieves a projection, a single column, single row value.
     *
     * @deprecated use {@link #projection(javax.persistence.TypedQuery, Object...)}
     * @param <P> the generic value type
     * @param query the query being executed
     * @param parameters the parameters the query needs
     * @return the value found by the given query
     */
    @Deprecated
    <P> P projection(Query query, Object... parameters);

    /**
     * Retrieves a projection, a single column, single row value.
     *
     * @param <P> the generic value type
     * @param query the query being executed
     * @param parameters the parameters the query needs
     * @return the value found by the given query
     */
    <P> P projection(TypedQuery<P> query, Object... parameters);
    
    /**
     * Retrieves a projection, a single column, single row value.
     * 
     * @param <P> the generic value type
     * @param queryName the name of the query being executed
     * @param parameters the parameters the query needs
     * @return the value found by the given query
     */
    <P> P projection(String queryName, Object... parameters);
    
    /**
     * Retrieves projections, multiple columns, single row values.
     * 
     * @param <P> the generic value type
     * @param query the query being executed
     * @param parameters the parameters the query need
     * @return the values found by the given query
     */
    <P> P[] projections(Query query, Object... parameters);

    /**
     * Retrieves projections, multiple columns, single row values.
     * 
     * @param <P> the generic value type
     * @param queryName the name of the query being executed
     * @param parameters the parameters the query need
     * @return the values found by the given query
     */
    <P> P[] projections(String queryName, Object... parameters);
    
    /**
     * Retrieves a list of projections, single column, multiple row values.
     *
     * @deprecated use {@link #projectionList(javax.persistence.TypedQuery, Object...)}
     * @param <P> the generic value type
     * @param query the query being executed
     * @param parameters the parameters the query needs
     * @return the values found by the given query
     */
    @Deprecated
    <P> List<P> projectionList(Query query, Object... parameters);

    /**
     * Retrieves a list of projections, single column, multiple row values.
     *
     * @param <P> the generic value type
     * @param query the query being executed
     * @param parameters the parameters the query needs
     * @return the values found by the given query
     */
    <P> List<P> projectionList(TypedQuery<P> query, Object... parameters);

    /**
     * Retrieves a list of projections, single column, multiple row values.
     * 
     * @param <P> the generic value type
     * @param queryName the name of the query being executed
     * @param parameters the parameters the query needs
     * @return the values found by the given query
     */
    <P> List<P> projectionList(String queryName, Object... parameters);
    
    /**
     * Retrieves a list of projections, multiple columns, multiple row values.
     * 
     * @param <P> the generic value type
     * @param query the query being executed
     * @param parameters the parameters the query needs
     * @return the values found by the given query
     */
    <P> List<P[]> projectionsList(Query query, Object... parameters);

    /**
     * Retrieves a list of projections, multiple columns, multiple row values.
     * 
     * @param <P> the generic value type
     * @param queryName the name of the query being executed
     * @param parameters the parameters the query needs
     * @return the values found by the given query
     */
    <P> List<P[]> projectionsList(String queryName, Object... parameters);
    
    /**
     * Prepares the specified query with the given parameters by calling
     * {@link Query#setParameter(int, Object)} for every parameter.
     * 
     * @param query the query being prepared
     * @param parameters the parameters the query requires
     * @return the specified query
     * @throws UnsupportedOperationException if the implementation does not support this feature
     */
    Query prepare(Query query, Object... parameters);

    /**
     * Prepares the specified query with the given parameters by calling
     * {@link Query#setParameter(int, Object)} for every parameter.
     *
     * @param <X> generic query parameter
     * @param query the query being prepared
     * @param parameters the parameters the query requires
     * @return the specified query
     */
    <X> TypedQuery<X> prepare(TypedQuery<X> query, Object... parameters);
    
    /**
     * Prepares the query associated with the specified name with the given parameters
     * by calling {@link Query#setParameter(int, Object)} for every parameter.
     * 
     * @param queryName the name of the query being prepared
     * @param parameters the parameters the query requires
     * @return the prepared query
     */
    TypedQuery<T> prepare(String queryName, Object... parameters);

}
