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

/**
 * The only purpose of this class is to make sure {@link AbstractEntityService} implements
 * all required methods.
 */
final class NoopEntityService extends AbstractEntityService<String>{

    @Override
    protected EntityManager entityManager() {
        throw new UnsupportedOperationException();
    }

    @Override
    protected Class<String> entityClass() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(String entity) {
        throw new UnsupportedOperationException();
    }

}
