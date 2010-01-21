package de.cosmocode.palava.services.persistence;

import javax.persistence.EntityManager;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

/**
 * Default {@link TransactionFilter} implementation which
 * uses the default binding of {@link EntityManager} without any annotations. 
 *
 * @author Willi Schoenborn
 */
@Singleton
final class DefaultTransactionFilter extends TransactionFilter {

    private final Provider<EntityManager> provider;
    
    @Inject
    public DefaultTransactionFilter(Provider<EntityManager> provider) {
        this.provider = Preconditions.checkNotNull(provider, "Provider");
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return provider.get();
    }

}
