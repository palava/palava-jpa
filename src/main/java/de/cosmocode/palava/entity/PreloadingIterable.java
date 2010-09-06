package de.cosmocode.palava.entity;

import java.util.Iterator;

import javax.persistence.TypedQuery;

import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;

/**
 * Preloading {@link Iterable} implementation used by {@link AbstractReadOnlyEntityService#iterate(int)}.
 *
 * @since 3.3
 * @author Willi Schoenborn
 * @param <T> generic entity type
 */
final class PreloadingIterable<T> implements Iterable<T> {
    
    private final Supplier<TypedQuery<T>> supplier;
    private final int batchSize;
    
    public PreloadingIterable(Supplier<TypedQuery<T>> supplier, int batchSize) {
        this.supplier = Preconditions.checkNotNull(supplier, "Supplier");
        Preconditions.checkArgument(batchSize > 0, "Batch size must be positive");
        this.batchSize = batchSize;
    }
    
    @Override
    public Iterator<T> iterator() {
        return new PreloadingIterator<T>(supplier.get(), batchSize);
    }
    
}
