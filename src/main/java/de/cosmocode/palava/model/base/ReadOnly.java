package de.cosmocode.palava.model.base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.persistence.Entity;

/**
 * Marks an {@link Entity} as read only, therefore disallows
 * any operations which would alter the state of this entity.
 *
 * @author Willi Schoenborn
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ReadOnly {

}
