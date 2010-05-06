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

package de.cosmocode.palava.model.base;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * Abstract base implementation of the {@link EntityBase} interface.
 * 
 * <p>
 *  This implementation provides equals/hashCode-methods which
 *  use the return value of {@link EntityBase#getId()} for equality checks.
 * </p>
 * 
 * <p>
 *   The {@link AbstractEntity} class is annotated with the {@link MappedSuperclass}
 *   annotation and serves as a base implementation for all concrete entities.
 * </p>
 * 
 * <p>
 *   The fields of this implementation are all private to support proper encapsulation.
 *   All accessor-methods (getter/setter) are non-final an can be overriden by sub-classes.
 *   The setter-methods in this implementation do not validate or modify the given
 *   inputs.
 * </p>
 * 
 * <p>
 *   This implementation provides a meaningful toString-method which includes
 *   the simple class name, the identifier and the current version.
 * </p>
 * 
 * <p>
 *   The {@link EntityBase#setCreated()} and {@link EntityBase#setModified()} are annotated
 *   with the {@link PrePersist}- and {@link PreUpdate}-annotation.
 * </p>
 * 
 * @author Willi Schoenborn
 */
@MappedSuperclass
public abstract class AbstractEntity implements EntityBase {

    @Version
    private int version;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    @Column(name = "modified_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedAt;
    
    @Column(name = "deleted_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;
    
    @Override
    public Date getCreatedAt() {
        return createdAt;
    }
    
    @Override
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    
    @Override
    @PrePersist
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
    @PreUpdate
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

    /**
     * <p>
     *   This implementation produces a string containing <strong>the simple class name of this entity,
     *   it's identifier and the current version</strong>.
     * </p>
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format("%s [id=%s, version=%s]", getClass().getSimpleName(), getId(), version);
    }
    
}
