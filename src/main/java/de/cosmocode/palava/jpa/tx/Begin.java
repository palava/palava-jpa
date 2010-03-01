package de.cosmocode.palava.jpa.tx;

import java.util.Map;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Provider;

import de.cosmocode.palava.ipc.IpcCall;
import de.cosmocode.palava.ipc.IpcCommand;
import de.cosmocode.palava.ipc.IpcCommandExecutionException;
import de.cosmocode.palava.ipc.IpcCommand.Description;
import de.cosmocode.palava.ipc.IpcCommand.Throw;

/**
 * See below.
 *
 * @author Willi Schoenborn
 */
@Description("Begins a persistence transaction.")
@Throw(name = IllegalStateException.class, description = "If there is already an active transaction")
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
