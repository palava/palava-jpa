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
