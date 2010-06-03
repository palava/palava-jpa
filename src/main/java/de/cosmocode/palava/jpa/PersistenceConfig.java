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

package de.cosmocode.palava.jpa;

/**
 * Constant holder class for persistence config keys.
 *
 * @author Willi Schoenborn
 * @author Tobias Sarnowski
 */
public final class PersistenceConfig {

    public static final String PREFIX = "persistence.";
    
    public static final String UNIT_NAME = PREFIX + "unitName";

    public static final String FLUSH_MODE = PREFIX + "flushMode";
    
    public static final String PROPERTIES = PREFIX + "properties";
    
    private PersistenceConfig() {
        
    }

}
