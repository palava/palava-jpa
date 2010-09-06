package de.cosmocode.palava.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

/**
 * An abstract {@link EntityService} which provides a {@link #delete(Object)} method.
 *
 * @since 3.3
 * @author Willi Schoenborn
 * @param <T> generic entity type
 */
public abstract class DeletingEntityService<T> extends AbstractEntityService<T> {

    private static final Logger LOG = LoggerFactory.getLogger(DeletingEntityService.class);

    @Override
    public void delete(T entity) {
        Preconditions.checkNotNull(entity, "Entity");
        LOG.debug("Deleting {}", entity);
        entityManager().remove(entity);
    }
    
}
