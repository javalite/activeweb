package app.controllers.app_spec;

import org.javalite.activeweb.AppSpec;
import org.junit.Test;

/**
 * @author igor on 12/9/17.
 */
public class HelloControllerSpec extends AppSpec {

    @Test
    public void shouldExecuteControllerByName(){
        controller("/app_spec/hello").get("index");
        the(responseContent()).shouldBeEqual("hello");
    }

    @Test
    public void shouldInferControllerByName(){
        request().get("index");
        the(responseContent()).shouldBeEqual("hello");
    }
}
