package app.controllers.app_spec;

import org.javalite.activeweb.AppSpec;
import org.javalite.activeweb.annotations.IncludeFilters;
import org.javalite.test.SystemStreamUtil;
import org.junit.Test;

/**
 * @author igor on 12/16/17.
 */
@IncludeFilters
public class IncludeFiltersSpec extends AppSpec {

    @Test
    public void shouldIncludeFilters(){
        SystemStreamUtil.replaceError();
        controller("/app_spec/hello").get("index");
        String out = SystemStreamUtil.getSystemErr();
        the(out).shouldContain("INFO org.javalite.activeweb.controller_filters.TimingFilter - Processed request in:");
        SystemStreamUtil.replaceError();
    }
}
