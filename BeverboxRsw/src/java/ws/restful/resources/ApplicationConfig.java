package ws.restful.resources;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author vanes
 */
@javax.ws.rs.ApplicationPath("Resources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(ws.restful.resources.ArticleResource.class);
        resources.add(ws.restful.resources.BeverageResource.class);
        resources.add(ws.restful.resources.BoxResource.class);
        resources.add(ws.restful.resources.OptionResource.class);
        resources.add(ws.restful.resources.PromotionResource.class);
        resources.add(ws.restful.resources.ReviewResource.class);
        resources.add(ws.restful.resources.TransactionResource.class);
    }
    
}
