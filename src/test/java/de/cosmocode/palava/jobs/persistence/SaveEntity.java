package de.cosmocode.palava.jobs.persistence;

import javax.persistence.EntityManager;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import de.cosmocode.palava.core.call.Call;
import de.cosmocode.palava.core.command.Command;
import de.cosmocode.palava.core.command.CommandException;
import de.cosmocode.palava.core.protocol.content.Content;
import de.cosmocode.palava.core.protocol.content.PhpContent;

@Singleton
public class SaveEntity implements Command {

    @Inject
    private Provider<EntityManager> provider;
    
    @Override
    public Content execute(Call call) throws CommandException {
        final EntityManager manager = provider.get();        
        
        final Object entity = null;
        manager.persist(entity);
        
        return PhpContent.OK;
    }

}
