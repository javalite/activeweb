package app.controllers;

import org.javalite.activeweb.AppController;

/**
 * @author Igor Polevoy on 11/29/15.
 */
public class ProjectsController extends AppController {

    public void newForm(){
        respond("newForm");
    }

    public void view(){
        respond("view");
    }
}
