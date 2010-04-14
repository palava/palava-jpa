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

import javax.persistence.EntityManager;

import com.google.common.base.Preconditions;

import de.cosmocode.palava.scope.Destroyable;
import de.cosmocode.patterns.Decorator;

/**
 * A {@link Decorator} for an {@link EntityManager} which adds {@link Destroyable}
 * behaviour.
 *
 * @author Willi Schoenborn
 */
@Decorator(EntityManager.class)
public final class DestroyableEntityManager extends ForwardingEntityManager implements Destroyable {
    
    private final EntityManager manager;

    public DestroyableEntityManager(EntityManager manager) {
        this.manager = Preconditions.checkNotNull(manager, "Manager");
    }
    
    @Override
    protected EntityManager delegate() {
        return manager;
    }
    
    @Override
    public void close() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void destroy() {
        if (manager.isOpen()) manager.close();
    }
    
}
