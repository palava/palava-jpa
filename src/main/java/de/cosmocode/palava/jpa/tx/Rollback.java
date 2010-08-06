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
import javax.persistence.PersistenceException;

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
import de.cosmocode.palava.ipc.IpcCommand.Throws;
import de.cosmocode.palava.ipc.IpcCommandExecutionException;

/**
 * See below.
 *
 * @author Willi Schoenborn
 */
@Description("Rolling back the current transaction")
@Throws({
    @Throw(name = IllegalStateException.class, description = "If the current transaction is not active"),
    @Throw(name = PersistenceException.class, description = "If rollback failed")
})
@Singleton
public final class Rollback implements IpcCommand {

    private static final Logger LOG = LoggerFactory.getLogger(Rollback.class);

    private final Provider<EntityManager> provider;
    
    @Inject
    public Rollback(Provider<EntityManager> provider) {
        this.provider = Preconditions.checkNotNull(provider, "Provider");
    }

    @Override
    public void execute(IpcCall call, Map<String, Object> result) throws IpcCommandExecutionException {
        LOG.trace("Rolling back transaction");
        provider.get().getTransaction().rollback();
    }

}
