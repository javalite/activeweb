package app.controllers.app_spec;

import org.javalite.activeweb.AppController;

/**
 * @author igor on 12/9/17.
 */
public class HelloController extends AppController {
    public void index(){
        respond("hello");
    }
}
