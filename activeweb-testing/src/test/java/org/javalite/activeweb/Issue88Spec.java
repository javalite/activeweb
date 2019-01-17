package org.javalite.activeweb;

import app.services.RedirectorModule;
import com.google.inject.Guice;
import org.javalite.activejdbc.DBException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Igor Polevoy: 3/5/12 11:19 AM
 */
public class Issue88Spec extends AppIntegrationSpec {

    @BeforeEach
    public void before() {
        setInjector(Guice.createInjector(new RedirectorModule()));
    }


    @Test(expected = DBException.class)
    public void shouldNotWrapDBException() {
        controller("db_exception").get("index");
    }
}
