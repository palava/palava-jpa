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
