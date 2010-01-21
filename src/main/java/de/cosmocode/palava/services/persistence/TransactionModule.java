package de.cosmocode.palava.services.persistence;

import de.cosmocode.palava.core.call.filter.definition.Matchers;
import de.cosmocode.palava.core.command.Command;
import de.cosmocode.palava.core.inject.AbstractApplicationModule;

/**
 * Binds the default implementation of the {@link TransactionFilter} class
 * to all {@linkplain Command commands} annotated with {@link Transactional}.
 *
 * @author Willi Schoenborn
 */
public final class TransactionModule extends AbstractApplicationModule {

    @Override
    protected void configureApplication() {
        filter(Matchers.annotatedWith(Transactional.class)).through(DefaultTransactionFilter.class);
    }

}
