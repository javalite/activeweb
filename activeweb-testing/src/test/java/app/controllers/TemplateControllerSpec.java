package app.controllers;

import org.javalite.activeweb.ControllerSpec;
import org.junit.jupiter.api.BeforeEach;

/**
 * @author igor on 7/28/17.
 */
public class TemplateControllerSpec extends ControllerSpec {

    @BeforeEach
    public void before(){
        setTemplateLocation("src/test/views");
    }
}
