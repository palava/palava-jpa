package de.cosmocode.palava.jpa.tx;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import de.cosmocode.palava.ipc.IpcCall;
import de.cosmocode.palava.ipc.IpcCommand;
import de.cosmocode.palava.ipc.IpcCommandExecutionException;
import de.cosmocode.palava.ipc.IpcCommand.Description;
import de.cosmocode.palava.ipc.IpcCommand.Throw;
import de.cosmocode.palava.ipc.IpcCommand.Throws;

/**
 * See below.
 *
 * @author Willi Schoenborn
 */
@Description("Commits the current persistence transaction.")
@Throws({
    @Throw(name = IllegalStateException.class, description = "If the current transaction is not active"),
    @Throw(name = RollbackException.class, description = "If commit fails")
})
@Singleton
public final class Commit implements IpcCommand {

    private static final Logger LOG = LoggerFactory.getLogger(Commit.class);

    private final Provider<EntityManager> provider;
    
    @Inject
    public Commit(Provider<EntityManager> provider) {
        this.provider = Preconditions.checkNotNull(provider, "Provider");
    }

    @Override
    public void execute(IpcCall call, Map<String, Object> result) throws IpcCommandExecutionException {
        LOG.trace("Commiting transaction");
        provider.get().getTransaction().commit();
    }

}
