package de.cosmocode.palava.jpa;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.google.inject.BindingAnnotation;

/**
 * A binding annotation which will be used to mark the scoped entity manager binding.
 *
 * @author Willi Schoenborn
 */
@Retention(RetentionPolicy.RUNTIME)
@BindingAnnotation
@interface Scoped {

}
