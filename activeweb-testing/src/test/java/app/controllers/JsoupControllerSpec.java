package app.controllers;

import org.javalite.activeweb.ControllerSpec;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Igor Polevoy on 11/14/15.
 */
public class JsoupControllerSpec extends ControllerSpec {

    @BeforeEach
    public void before(){setTemplateLocation("src/test/views");}

    @Test
    public void shouldFindContentOfElement(){
        request().get("index");
        a(text("div[class=\"main\"]")).shouldBeEqual("hello");
    }

    @Test
    public void shouldCountElements(){
        request().get("index");
        a(count("li")).shouldBeEqual(4);
    }
}
