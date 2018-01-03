package app.controllers.app_spec;

import org.javalite.activeweb.AppSpec;
import org.junit.Test;

/**
 * @author igor on 12/9/17.
 */
public class HelloControllerSpec extends AppSpec {

    @Test
    public void shouldExecuteControllerByName(){
        controller("/app_spec/hello").get("index"); // gets a controller argument
        the(responseContent()).shouldBeEqual("hello");
    }

    @Test
    public void shouldInferControllerByName(){ // gets a controller from name of spec
        request().get("index");
        the(responseContent()).shouldBeEqual("hello");
    }
}
