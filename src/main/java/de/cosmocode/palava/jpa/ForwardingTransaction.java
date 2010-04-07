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
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */

package de.cosmocode.palava.jpa;

import javax.persistence.EntityTransaction;

import com.google.common.base.Preconditions;

/**
 * Abstract decorator for {@link EntityTransaction}s.
 *
 * @author Willi Schoenborn
 */
abstract class ForwardingTransaction implements EntityTransaction {

    private final EntityTransaction tx;
    
    public ForwardingTransaction(EntityTransaction tx) {
        this.tx = Preconditions.checkNotNull(tx, "Tx");
    }
    
    @Override
    public void begin() {
        tx.begin();
    }

    @Override
    public void commit() {
        tx.commit();
    }

    @Override
    public boolean getRollbackOnly() {
        return tx.getRollbackOnly();
    }

    @Override
    public boolean isActive() {
        return tx.isActive();
    }

    @Override
    public void rollback() {
        tx.rollback();
    }

    @Override
    public void setRollbackOnly() {
        tx.setRollbackOnly();
    }
    
}
