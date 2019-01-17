package org.javalite.activeweb;

import app.services.RedirectorModule;
import com.google.inject.Guice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Igor Polevoy: 9/30/12 12:18 AM
 */
public class IncludeExcludeControllerSpec extends AppIntegrationSpec {

    @BeforeEach
    public void before(){
        setTemplateLocation("src/test/views");
        //this is simply needed to satisfy bootstrapping of the app, unrelated to this test, but will break if removed.
        setInjector(Guice.createInjector(new RedirectorModule()));
    }

    @Test
    public void shouldIncludeControllersForGlobalFilter() {
        controller("include").get("index");
        a(responseContent()).shouldContain("I'm included!");
    }

    @Test
    public void shouldExcludeControllersForGlobalFilter() {
        controller("exclude").get("index");
        a(responseContent()).shouldNotContain("I'm included!");
    }


    @Test
    public void shouldExcludeControllerActionForGlobalFilter() {
        controller("exclude_action").get("include");
        a(val("the_message")).shouldContain("Whohoo!");

        controller("exclude_action").get("exclude");
        a(val("the_message")).shouldNotContain("Whohoo!");
    }
}
