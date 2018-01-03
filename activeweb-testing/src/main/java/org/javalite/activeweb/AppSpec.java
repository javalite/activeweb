package org.javalite.activeweb;

import org.javalite.activeweb.annotations.NoDB;
import org.javalite.activeweb.annotations.NoRollback;
import org.javalite.common.Util;
import org.junit.After;

/**
 * Main class for testing of all components of an ActiveWeb project
 *
 * @author igor on 12/9/17.
 */
public class AppSpec extends AppIntegrationSpec {

    private String controllerPath;
    private boolean rollback;

    private boolean noRollback, noDB;

    public AppSpec() {
        Configuration.resetFilters();
        Configuration.setInjector(null);

        noDB = getClass().getAnnotation(NoDB.class) != null;
        noRollback = getClass().getAnnotation(NoRollback.class) != null;

        if(noRollback && noDB){
            throw new IllegalArgumentException("Cannot declare NoRollback and NoDB at the same time");
        }

        if(noDB){
            suppressDb();
        }

        if(noDB){
            rollback = Configuration.rollback();
            Configuration.setRollback(false);
        }
    }

    @After
    public void xxAfter(){
        //restore rollback as it was
        Configuration.setRollback(rollback);
    }

    /**
     * This method imitates a call to a controller, whose name is derived from the your spec name.
     * For example, if your spec name is <code>HelloControllerSpec</code>, this method will presume you want to invoke
     * a controller named <code>HelloController</code>
     *
     * Use this DSL-ish method to send requests to controllers from specs.
     * <strong>Attention</strong>: this method always returns a new object, please string methods one after another - fluent interfaces
     * approach.
     *
     * @return instance of <code>RequestBuilder</code> with convenience methods.
     */
    protected RequestBuilder request() {
        controllerPath  = getControllerPath();
        return new RequestBuilder(controllerPath, session());
    }


    /**
     * Returns a controller path - this includes packages if there are any after "app.controllers".
     *
     * @return   controller path
     */
    protected final String getControllerPath(){
        String controllerClassName = getControllerClassName();
        Class<? extends AppController> controllerClass;
        try{
            controllerClass = (Class<? extends AppController>) Class.forName(controllerClassName);
        }catch(Exception e){
            throw new SpecException("Failed to find a class for: " + controllerClassName, e);
        }
        return Router.getControllerPath(controllerClass);
    }

    protected final String getControllerClassName() {

        String packageName = getClass().getPackage().getName();
        if(!packageName.startsWith("app.controllers")){
            throw new SpecException("controller specs must be located in package 'app.controllers' or sub-packages");
        }

        if (!getClass().getSimpleName().endsWith("ControllerSpec"))
            throw new SpecException("If a method request() is used, the spec class  must be named: controller name + 'Spec', " +
                    "and because controllers have to have a suffix 'Controller'," +
                    " controller spec classes  must have a suffix: 'ControllerSpec'. Additionally, the spec needs to be in the same " +
                    "package with the tested controller. ");

        String temp = getClass().getName();//full name
        temp = temp.substring(16);
        if(temp.contains(".")){
            temp = temp.substring(0, temp.lastIndexOf("."));// this is sub-package
        }else{
            temp = "";
        }

        String specClassName = getClass().getSimpleName();
        String controllerName = specClassName.substring(0, specClassName.lastIndexOf("Spec"));

        return "app.controllers." + (Util.blank(temp)? "": temp + ".") + controllerName;
    }
}
