package jsf.managedBean;

import ejb.session.stateless.CustomerSessionBeanLocal;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

@Named(value = "customerManagedBean")
@RequestScoped
public class CustomerManagedBean {

    @EJB(name = "CustomerSessionBeanLocal")
    private CustomerSessionBeanLocal customerSessionBeanLocal;

    
    public CustomerManagedBean() {
    }
    
}
