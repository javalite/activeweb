package org.javalite.activeweb.annotations;

import org.javalite.activeweb.AppSpec;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * By default {@link AppSpec} does not include filters into processing of requests.
 * If this annotation is added to a spec that overrides {@link AppSpec}, then all filters except
 * {@link org.javalite.activeweb.controller_filters.DBConnectionFilter} will be included
 * in processing of a controller during a test.
 *
 * @author igor on 12/16/17.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface IncludeFilters {}

