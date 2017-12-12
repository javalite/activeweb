package org.javalite.activeweb.annotations;

import org.javalite.activeweb.AppSpec;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Add annotation to the {@link AppSpec} decedents to prevent it from managing transactions.
 * Normally, a transaction is opened on a start of and rolled back at the end of test.
 * This prevents leaking of data in the database across tests. Adding this annotation
 * to a class that inherits from {@link AppSpec} will disable this feature. Use with caution.
 *
 * @author igor on 12/9/17.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface NoRollback {}

