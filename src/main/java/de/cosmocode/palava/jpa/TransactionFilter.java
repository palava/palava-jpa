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

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Provider;

import de.cosmocode.palava.ipc.IpcCall;
import de.cosmocode.palava.ipc.IpcCallFilter;
import de.cosmocode.palava.ipc.IpcCallFilterChain;
import de.cosmocode.palava.ipc.IpcCommand;
import de.cosmocode.palava.ipc.IpcCommandExecutionException;

/**
 * An {@link IpcCallFilter} which handles transaction management for
 * specific jobs.
 *
 * @author Willi Schoenborn
 */
final class TransactionFilter implements IpcCallFilter {

    private static final Logger LOG = LoggerFactory.getLogger(TransactionFilter.class);

    private final Provider<EntityManager> provider;
    
    @Inject
    public TransactionFilter(Provider<EntityManager> provider) {
        this.provider = Preconditions.checkNotNull(provider, "Provider");
    }
    
    @Override
    public Map<String, Object> filter(IpcCall call, IpcCommand command, IpcCallFilterChain chain)
        throws IpcCommandExecutionException {
        final EntityManager manager = provider.get();
        final EntityTransaction tx = manager.getTransaction();
        
        LOG.debug("Starting transaction");
        tx.begin();
        
        final Map<String, Object> result;
        
        try {
            result = chain.filter(call, command);
        /*CHECKSTYLE:OFF*/
        } catch (RuntimeException e) {
        /*CHECKSTYLE:ON*/
            LOG.error("Execution failed, rolling back", e);
            tx.rollback();
            throw e;
        } catch (IpcCommandExecutionException e) {
            LOG.error("Filtering failed, rolling back", e);
            tx.rollback();
            throw e;
        }
        
        try {
            assert tx.isActive() : "Transaction should be active";
            tx.commit();
            LOG.debug("Commit succeeded");
            return result;
        } catch (PersistenceException e) {
            LOG.error("Commit failed, rolling back", e);
            tx.rollback();
            throw e;
        }
    }

}
