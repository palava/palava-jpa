package de.cosmocode.palava.jpa;

/**
 * Constant holder class for persistence config keys.
 *
 * @author Willi Schoenborn
 */
public final class PersistenceConfig {

    public static final String PREFIX = "persistence.";
    
    public static final String UNIT_NAME = PREFIX + "unitName";
    
    public static final String PROPERTIES = PREFIX + "properties";
    
    private PersistenceConfig() {
        
    }

}
