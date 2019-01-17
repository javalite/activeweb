package app.controllers;

import org.javalite.activeweb.IntegrationSpec;
import org.junit.jupiter.api.BeforeEach;

/**
 * @author igor on 7/28/17.
 */
public class TemplateIntegrationSpec extends IntegrationSpec{

    @BeforeEach
    public void before(){
        setTemplateLocation("src/test/views");
    }
}
