package app.controllers;

import org.javalite.activeweb.AppSpec;
import org.junit.Before;

/**
 * @author igor on 7/28/17.
 */
public class TemplateControllerSpec extends AppSpec {

    @Before
    public void before(){
        setTemplateLocation("src/test/views");
    }
}
