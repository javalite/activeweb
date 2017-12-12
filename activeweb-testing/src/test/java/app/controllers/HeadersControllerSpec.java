package app.controllers;

import org.javalite.activeweb.AppSpec;
import org.junit.Test;

/**
 * @author igor on 11/29/16.
 */
public class HeadersControllerSpec extends AppSpec {

    @Test
    public void shouldGtHeaders(){
        request().get("index");
        the(header("number")).shouldBeEqual("one");
        the(headerNames()).shouldContain("number");
    }
}
