/**
 * palava - a java-php-bridge
 * Copyright (C) 2007  CosmoCode GmbH
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

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Version;

/**
 * Abstract base implementation of the {@link EntityBase} interface.
 * 
 * TODO empty checks for strings (to null)
 * TODO validation checks for emails, websites
 * 
 * @author Willi Schoenborn
 */
public abstract class AbstractEntity implements EntityBase {

    @Id
    private long id;
    
    @Version
    private int version;

    @Column(name = "created_at")
    private Date createdAt;
    
    @Column(name = "modified_at")
    private Date modifiedAt;
    
    @Column(name = "deleted_at")
    private Date deletedAt;
    
    @Override
    public long getId() {
        return id;
    }
    
    @Override
    public Date getCreatedAt() {
        return createdAt;
    }
    
    @Override
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    @Override
    public void setCreated() {
        setCreatedAt(new Date());
    }
    
    @Override
    public Date getModifiedAt() {
        return modifiedAt;
    }
    
    @Override
    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
    
    @Override
    public void setModified() {
        setModifiedAt(new Date());
    }
    
    @Override
    public Date getDeletedAt() {
        return deletedAt;
    }
    
    @Override
    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }
    
    @Override
    public void setDeleted() {
        setDeletedAt(new Date());
    }
    
    @Override
    public boolean isDeleted() {
        return getDeletedAt() != null;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (getId() ^ (getId() >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof EntityBase)) {
            return false;
        }
        final EntityBase other = (EntityBase) obj;
        if (getId() != other.getId()) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("%s [id=%s, version=%s]", getClass().getSimpleName(), getId(), version);
    }
    
}
