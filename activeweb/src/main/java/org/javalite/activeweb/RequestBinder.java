package org.javalite.activeweb;

import static org.javalite.common.Util.bytes;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.javalite.activejdbc.Model;

import com.sun.org.apache.bcel.internal.classfile.ClassParser;
import com.sun.org.apache.bcel.internal.classfile.JavaClass;
import com.sun.org.apache.bcel.internal.classfile.LocalVariable;

public class RequestBinder {

	public static List<Object> bind(Object controller, String actionName) throws Exception {
        	//TODO check for FileUploadRequest
			String pathToClassFile = controller.getClass().getName().replace('.', '/') + ".class";
            byte[] classBytes = bytes(getResourceAsStream(pathToClassFile));
            final ByteArrayInputStream bis = new ByteArrayInputStream(classBytes);

        	ClassParser parser = new ClassParser(bis, controller.getClass().getName());
            JavaClass clazz = parser.parse();
            LocalVariable[] res = null;
            //find all local variables info
            for (com.sun.org.apache.bcel.internal.classfile.Method m : clazz.getMethods()) {
                if(m.getName().equals(actionName)){
                	res = m.getLocalVariableTable().getLocalVariableTable();
                	break;
                }
            }
            List<String> paramNames = new ArrayList<String>();
            for(LocalVariable var : res){
            	//First parameter of non-static methods is this, it should be filtered out. Controller should be compiled with debug info!
            	if(!var.getName().equals("this")){
            		paramNames.add(var.getName());
            	}
            }
            Method method = Context.getMethod();
            //If no params except this we can skip relatively time-consuming reflection
            if(res.length > 0){
            	Class<?>[] types = method.getParameterTypes();
                Map map = Context.getHttpRequest().getParameterMap();
                List<Object> p = new ArrayList<Object>();
                BeanUtilsBean utils = new BeanUtilsBean();
                for(int j = 0; j < types.length; j++){
                	Object par = types[j].newInstance();
                	if(par instanceof Model){
                		((Model)par).fromMap(map);
                		p.add(par);
                	}else 
                	if(types[j].equals(String.class)){
                		//For CookieControllerSpec
                		if(paramNames.size() >j){
                			par = map.get(paramNames.get(j));
                    		p.add(par);
                		}
                		
                	}else if(types[j].equals(Integer.class)){
                		par = map.get(paramNames.get(j));
                		if(par != null){
                			par = new Integer(par.toString());
                		}
                		p.add(par);
                	}else{
                		Object instance = types[j].newInstance();
                		utils.populate(instance, map);
                		p.add(instance);
                	}
                }
                return p;
            }
        return null;
	}
	
	private static InputStream getResourceAsStream(String name) {
        try {
        	//TODO add support for load controller classes from jars
        	DynamicClassLoader dc = new DynamicClassLoader(ControllerFactory.class.getClassLoader(),
                    Configuration.getTargetDir());
        	InputStream in1 = dc.getResourceAsStream(name);
        	return in1;
        } catch (Exception e) {
            return null;
        }
    }
}
