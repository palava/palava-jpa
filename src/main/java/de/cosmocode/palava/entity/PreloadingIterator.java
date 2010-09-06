package de.cosmocode.palava.entity;

import java.util.Iterator;
import java.util.NoSuchElementException;

import javax.persistence.TypedQuery;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterators;
import com.google.common.collect.UnmodifiableIterator;

/**
 * A preloading iterator which uses a configurable batch size.
 *
 * @author Willi Schoenborn
 * @param <T> generic entity type
 */
final class PreloadingIterator<T> extends UnmodifiableIterator<T> {

    private final int batchSize;
    private final TypedQuery<T> query;
    
    private int nextIndex;
    
    private Iterator<T> current = Iterators.emptyIterator();
    private Iterator<T> next;

    public PreloadingIterator(TypedQuery<T> query, int batchSize) {
        this.query = Preconditions.checkNotNull(query, "Query").setMaxResults(batchSize);
        this.batchSize = batchSize;
        preload();
    }
    
    private void preload() {
        query.setFirstResult(nextIndex);
        next = query.getResultList().iterator();
        nextIndex += batchSize;
    }

    @Override
    public boolean hasNext() {
        return current.hasNext() || next.hasNext();
    }

    @Override
    public T next() {
        if (current.hasNext()) {
            return current.next();
        } else if (next.hasNext()) {
            current = next;
            preload();
            return current.next();
        } else {
            throw new NoSuchElementException();
        }
    }
    
}
