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

package de.cosmocode.palava.model.base;

import java.util.Date;

import com.google.common.base.Function;
import com.google.common.collect.Ordering;

import de.cosmocode.json.JSONMapable;

/**
 * 
 *
 * @author Willi Schoenborn
 */
public interface EntityBase extends JSONMapable {

    /**
     * Allows ascending ordering by id.
     */
    Ordering<EntityBase> ORDER_BY_ID = Ordering.natural().nullsLast().onResultOf(new Function<EntityBase, Long>() {
        
        @Override
        public Long apply(EntityBase from) {
            return from.getId();
        };
        
    });
    
    /**
     * Allows ordering by age, which will move the oldest entities to the top.
     */
    Ordering<EntityBase> ORDER_BY_AGE = Ordering.natural().nullsLast().onResultOf(new Function<EntityBase, Date>() {
        
        @Override
        public Date apply(EntityBase from) {
            return from.getCreatedAt();
        }
    
    });
    
    /**
     * Allows ordering by last modification, which will move the recently modified entities to the top.
     */
    Ordering<EntityBase> ORDER_BY_MODIFICATION = Ordering.natural().reverse().nullsLast().onResultOf(
        new Function<EntityBase, Date>() {
        
            @Override
            public Date apply(EntityBase from) {
                return from.getModifiedAt();
            }
            
        });
    
    long getId();
    
    Date getCreatedAt();
    
    void setCreatedAt(Date createdAt);
    
    void setCreated();
    
    Date getModifiedAt();
    
    void setModifiedAt(Date modifiedAt);
    
    void setModified();
    
    Date getDeletedAt();
    
    void setDeletedAt(Date deletedAt);
    
    void setDeleted();
    
    boolean isDeleted();
    
}
