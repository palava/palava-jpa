package de.cosmocode.palava.jpa;

/**
 * Defines the different transaction modes when
 * encountering a method annotated with {@link Transactional}.
 *
 * @since 3.3
 * @author Willi Schoenborn
 */
enum TransactionMode {

    NOT_SUPPORTED,
    
    SUPPORTS,
    
    REQUIRED,
    
    REQUIRES_NEW,
    
    MANDATORY,
    
    NEVER;
    
}
