package org.javalite.activeweb;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Igor Polevoy on 6/21/15.
 */
public class IntegrateViewsSpec extends IntegrationSpec {

    @BeforeEach
    public void before(){
        setTemplateLocation("src/test/views");
    }

    @Test
    public void shouldRenderWithoutCallingIntegrateViews(){
        controller("student").get("index");
        a(responseContent()).shouldBeEqual("hello");
    }
}
