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
import com.google.common.base.Predicate;
import com.google.common.collect.Ordering;

import de.cosmocode.json.JSONMapable;

/**
 * The {@link EntityBase} defines the most basic information almost every
 * entity class should provide.
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
    
    /**
     * Allows filtering entities which are deleted.
     */
    Predicate<EntityBase> IS_DELETED = new Predicate<EntityBase>() {
        
        @Override
        public boolean apply(EntityBase input) {
            return input.isDeleted();
        }
        
    };
    
    /**
     * Provide the identifier of this entity.
     * 
     * @return this entity's id
     */
    long getId();
    
    /**
     * Provide the date of creation of this entity.
     * 
     * @return this entity's creation date
     */
    Date getCreatedAt();
    
    /**
     * Sets the date of creation of this entity.
     * 
     * @param createdAt the new creation date
     */
    void setCreatedAt(Date createdAt);
    
    /**
     * Updates the date of creation of this entity.
     */
    void setCreated();
    
    /**
     * Provide the date of modification of this entity.
     * 
     * @return this entity's modification date
     */
    Date getModifiedAt();
    
    /**
     * Sets the date of modification of this entity.
     * 
     * @param modifiedAt the new modification date
     */
    void setModifiedAt(Date modifiedAt);
    
    /**
     * Updates the date of modificate of this entity.
     */
    void setModified();
    
    /**
     * Provide the date of deletion of this entity.
     * 
     * @return this entity's deletion date
     */
    Date getDeletedAt();
    
    /**
     * Sets the date of deletion of this entity.
     * 
     * @param deletedAt the new deletion date
     */
    void setDeletedAt(Date deletedAt);
    
    /**
     * Updates the date of deletion of this entity.
     */
    void setDeleted();
    
    /**
     * Check the deletion state of this entity.
     * 
     * @return true if the deletion date of this entity is not null
     */
    boolean isDeleted();
    
}
