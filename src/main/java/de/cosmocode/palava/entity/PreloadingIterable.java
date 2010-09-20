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
