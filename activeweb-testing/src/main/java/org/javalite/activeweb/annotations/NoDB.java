package org.javalite.activeweb.annotations;

import org.javalite.activejdbc.Model;
import org.javalite.activeweb.AppSpec;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Add annotation to the {@link AppSpec} decedents to avoid opening a DB connection for tests.
 *
 * @author igor on 12/9/17.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface NoDB {}

