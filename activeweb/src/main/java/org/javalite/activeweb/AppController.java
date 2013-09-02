/*
Copyright 2009-2010 Igor Polevoy 

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
package org.javalite.activeweb;

import org.javalite.activeweb.annotations.RESTful;
import org.javalite.common.Util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;


/**
 * Subclass this class to create application controllers. A controller is a main component of a web
 * application. Its main purpose in life is to process web requests. 
 *
 * @author Igor Polevoy
 */
public abstract class AppController extends HttpSupport {
    
    


    /**
     * Assigns value that will be passed into view.
     * 
     * @param name name of a value.
     * @param value value.
     */
    protected void assign(String name, Object value) {

        KeyWords.check(name);
        Context.getValues().put(name, value);
    }

    /**
     * Alias to {@link #assign(String, Object)}.
     *
     * @param name name of object to be passed to view
     * @param value object to be passed to view
     */
    protected void view(String name, Object value) {
        assign(name, value);
    }
    

    protected Map values() {
        return Context.getValues();
    }

    /**
     * Renders results with a template.
     * This method is called from within an action execution.
     *
     * This call must be the last call in the action. All subsequent calls to assign values, render or respond will generate
     * {@link IllegalStateException}.
     *
     * @param template - template name, can be "list"  - for a view whose name is different than the name of this action, or
     *             "/another_controller/any_view" - this is a reference to a view from another controller. The format of this
     * parameter should be either a single word or two words separated by slash: '/'. If this is a single word, than
     * it is assumed that template belongs to current controller, if there is a slash used as a separator, then the
     * first word is assumed to be a name of another controller.
     * @return instance of {@link RenderBuilder}, which is used to provide additional parameters.
     */
    protected RenderBuilder render(String template) {

        String targetTemplate = template.startsWith("/")? template: Router.getControllerPath(getClass())
                + "/" + template;

        return render(targetTemplate, values());
    }

    /**
     * Use this method in order to override a layout, status code, and content type.
     *
     * @return instance of {@link RenderBuilder}, which is used to provide additional parameters.
     */
    protected RenderBuilder render(){

        String template = Router.getControllerPath(getClass()) + "/" + Context.getRoute().getActionName();
        return super.render(template, values());
    }


    protected String servletPath() {
        return Context.getHttpRequest().getServletPath();
    }

    protected String queryString() {
        return Context.getHttpRequest().getQueryString();
    }

    protected InputStream getRequestInputStream() throws IOException {
        return Context.getHttpRequest().getInputStream();
    }

    /**
     * Alias to {@link #getRequestInputStream()}.
     * @return input stream to read data sent by client.
     * @throws IOException
     */
    protected InputStream getRequestStream() throws IOException {
        return Context.getHttpRequest().getInputStream();
    }

    /**
     * Reads entire request data as String. Do not use for large data sets to avoid
     * memory issues, instead use {@link #getRequestInputStream()}.
     *
     * @return data sent by client as string.
     * @throws IOException
     */
    protected String getRequestString() throws IOException {
        return Util.read(Context.getHttpRequest().getInputStream());
    }

    /**
     * Reads entire request data as byte array. Do not use for large data sets to avoid
     * memory issues.
     *
     * @return data sent by client as string.
     * @throws IOException
     */
    protected byte[] getRequestBytes() throws IOException {        
        return Util.bytes(Context.getHttpRequest().getInputStream());
    }


    /**
     * Returns a name for a default layout as provided in  <code>activeweb_defaults.properties</code> file.
     * Override this  method in a sub-class. Value expected is a fully qualified name of a layout template.
     * Example: <code>"/custom/custom_layout"</code>
     *
     * @return name of a layout for this controller and descendants if they do not override this method.
     */
    protected String getLayout(){
        return Configuration.getDefaultLayout();
    }

    /**
     * Returns hardcoded value "text/html". Override this method to set default content type to a different value across
     * all actions in controller and its subclasses. This is a convenient method for building REST webservices. You can set
     * this value once to "text/json", "text/xml" or whatever else you need.
     *
     * @return hardcoded value "text/html"
     */
    protected String getContentType(){
        return "text/html";
    }

    /**
     * Checks if the action supports an HTTP method, according to its configuration.
     *
     * @param actionMethodName name of action method.
     * @param httpMethod http method
     * @return true if supports, false if does not.
     */
    public boolean actionSupportsHttpMethod(String actionMethodName, HttpMethod httpMethod) {
        if (restful()) {
            return restfulActionSupportsHttpMethod(actionMethodName, httpMethod) ? true : standardActionSupportsHttpMethod(actionMethodName, httpMethod);
        } else {
            return standardActionSupportsHttpMethod(actionMethodName, httpMethod);
        }
    }

    protected boolean standardActionSupportsHttpMethod(String actionMethodName, HttpMethod httpMethod){
        try {
            Method method = getClass().getMethod(actionMethodName);
            Annotation[] annotations = method.getAnnotations();

            //default behavior: GET method!
            if (annotations.length == 0 && httpMethod.equals(HttpMethod.GET)) {
                return true;
            } else {
                for (Annotation annotation : annotations) {


                    if(HttpMethod.valueOf(annotation.annotationType().getSimpleName()).equals(httpMethod))
                        return true;
                }
                return false;
            }
        } catch (NoSuchMethodException e) {
            throw new ActionNotFoundException(e);
        }
    }

    private boolean restfulActionSupportsHttpMethod(String action, HttpMethod httpMethod) {
        if (action.equals("index") && httpMethod.equals(HttpMethod.GET)) {
            return true;
        } else if (action.equals("newForm") && httpMethod.equals(HttpMethod.GET)) {
            return true;
        } else if (action.equals("create") && httpMethod.equals(HttpMethod.POST)) {
            return true;
        } else if (action.equals("show") && httpMethod.equals(HttpMethod.GET)) {
            return true;
        } else if (action.equals("editForm") && httpMethod.equals(HttpMethod.GET)) {
            return true;
        } else if (action.equals("update") && httpMethod.equals(HttpMethod.PUT)) {
            return true;
        } else if (action.equals("destroy") && httpMethod.equals(HttpMethod.DELETE)) {
            return true;
        } else {
            logDebug("You might want to execute a non-restful action on a restful controller. It is recommended that you " +
                    "use the following methods on restful controllers: index, newForm, create, show, editForm, update, destroy");
            return false;
        }
    }



    /**
     * Returns true if this controller is configured to be {@link org.javalite.activeweb.annotations.RESTful}.
     * @return true if this controller is restful, false if not.
     */
    public boolean restful() {
        return getClass().getAnnotation(RESTful.class) != null;
    }

    public static <T extends AppController> boolean restful(Class<T> controllerClass){
        return controllerClass.getAnnotation(RESTful.class) != null;
    }
}
