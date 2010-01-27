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

package de.cosmocode.palava.services.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.cosmocode.palava.bridge.Content;
import de.cosmocode.palava.bridge.call.Call;
import de.cosmocode.palava.bridge.call.filter.Filter;
import de.cosmocode.palava.bridge.call.filter.FilterChain;
import de.cosmocode.palava.bridge.call.filter.FilterException;
import de.cosmocode.palava.bridge.command.Commands;

/**
 * A {@link Filter} which handles transaction management for
 * specific jobs.
 *
 * @author Willi Schoenborn
 */
public abstract class TransactionFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(TransactionFilter.class);

    /**
     * Gets the underlying {@link EntityManager} instance for
     * the transaction.
     * 
     * @return an EntityManager
     */
    protected abstract EntityManager getEntityManager();
    
    @Override
    public Content filter(Call call, FilterChain chain) throws FilterException {
        final EntityManager manager = getEntityManager();
        final EntityTransaction tx = manager.getTransaction();
        
        log.debug("Starting transaction");
        tx.begin();
        
        final Content content;
        
        try {
            content = chain.filter(call);
        /*CHECKSTYLE:OFF*/
        } catch (RuntimeException e) {
        /*CHECKSTYLE:ON*/
            log.error("Execution failed, rolling back", e);
            tx.rollback();
            throw e;
        } catch (FilterException e) {
            log.error("Filtering failed, rolling back", e);
            tx.rollback();
            throw e;
        }
        
        try {
            assert tx.isActive() : "Transaction should be active";
            tx.commit();
            log.debug("Commit succeeded");
            return content;
        } catch (PersistenceException e) {
            log.error("Commit failed, rolling back", e);
            final Class<?> type = Commands.getClass(call.getCommand());
            final Transactional annotation = type.getAnnotation(Transactional.class);
            switch (annotation.strategy()) {
                case ROLLBACK: {
                    tx.rollback();
                    throw e;
                }
                case THROW: {
                    throw e;
                }
                default: {
                    throw new IllegalArgumentException("Unknown strategy " + annotation.strategy());
                }
            }
        }
    }

}
