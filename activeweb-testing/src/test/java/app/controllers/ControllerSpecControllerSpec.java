/*
Copyright 2009-2016 Igor Polevoy

Licensed under the Apache License, Version 2.0 (the "License"); 
you may not use this file except in compliance with the License. 
You may obtain a copy of the License at 

http://www.apache.org/licenses/LICENSE-2.0 

Unless required by applicable law or agreed to in writing, software 
distributed under the License is distributed on an "AS IS" BASIS, 
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
See the License for the specific language governing permissions and 
limitations under the License. 
*/
package app.controllers;

import org.javalite.activeweb.Configuration;
import org.javalite.activeweb.ControllerException;
import org.javalite.activeweb.TemplateManager;
import org.junit.Test;

/**
 * @author Igor Polevoy
 */
public class ControllerSpecControllerSpec extends TemplateControllerSpec {

    @Test
    public void shouldAssignInformationOnPersonWithGET() {

        request().param("id", "2").get("index"); //<<========== making a request to the controller.

        a(assigns().get("name")).shouldEqual("John");
        a(assigns().get("last_name")).shouldEqual("Silverman");
        a(assigns().get("age")).shouldEqual(32);
        a(assigns().get("name")).shouldEqual("John");
        a(layout()).shouldEqual("/layouts/default_layout");
        a(statusCode()).shouldBeEqual(200);
        a(contentType()).shouldBeEqual("text/html");

    }

}