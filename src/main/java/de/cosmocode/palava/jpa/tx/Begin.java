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

package de.cosmocode.palava.jpa.tx;

import java.util.Map;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import de.cosmocode.palava.ipc.IpcCall;
import de.cosmocode.palava.ipc.IpcCommand;
import de.cosmocode.palava.ipc.IpcCommand.Description;
import de.cosmocode.palava.ipc.IpcCommand.Throw;
import de.cosmocode.palava.ipc.IpcCommandExecutionException;

/**
 * See below.
 *
 * @author Willi Schoenborn
 */
@Description("Begins a persistence transaction.")
@Throw(name = IllegalStateException.class, description = "If there is already an active transaction")
@Singleton
public final class Begin implements IpcCommand {

    private static final Logger LOG = LoggerFactory.getLogger(Begin.class);

    private final Provider<EntityManager> provider;
    
    @Inject
    public Begin(Provider<EntityManager> provider) {
        this.provider = Preconditions.checkNotNull(provider, "Provider");
    }
    
    @Override
    public void execute(IpcCall call, Map<String, Object> result) throws IpcCommandExecutionException {
        LOG.trace("Beginning transaction");
        provider.get().getTransaction().begin();
    }

}
