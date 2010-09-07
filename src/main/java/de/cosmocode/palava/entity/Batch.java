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

import javax.persistence.EntityManager;

import com.google.gag.annotation.enforceable.Noop;

import de.cosmocode.collections.Procedure;

/**
 * A set of reusable {@link Procedure}s which can be applied to
 * {@link EntityService#each(Procedure, int, Procedure)}.
 *
 * @since 2.3
 * @author Willi Schoenborn
 */
public enum Batch implements Procedure<EntityManager> {

    /**
     * Performs no operation.
     */
    NOOP {

        @Noop
        @Override
        public void apply(EntityManager manager) {
            // do nothing
        }
        
    },
    
    /**
     * Calls {@link EntityManager#flush()}.
     */
    FLUSH {

        @Override
        public void apply(EntityManager manager) {
            manager.flush();
        }
        
    },
    
    /**
     * Calls {@link EntityManager#clear()}.
     */
    CLEAR {

        @Override
        public void apply(EntityManager manager) {
            manager.clear();
        }
        
    },
    
    /**
     * Calls {@link EntityManager#flush()} and {@link EntityManager#clear()} in
     * that order.
     */
    FLUSH_AND_CLEAR {

        @Override
        public void apply(EntityManager manager) {
            manager.flush();
            manager.clear();
        }
        
    };
    
}
