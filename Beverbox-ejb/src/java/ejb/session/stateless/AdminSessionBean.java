package ejb.session.stateless;

import entity.Admin;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.AdminNotFoundException;
import util.exception.CreateNewAdminException;

/**
 *
 * @author zhixuan
 */
@Stateless
public class AdminSessionBean implements AdminSessionBeanLocal {

    @PersistenceContext(unitName = "Beverbox-ejbPU")
    private EntityManager em;
    
    @Override
    public Long createNewAdmin(Admin newAdmin){ 
        em.persist(newAdmin);
        em.flush();
            
        return newAdmin.getAdminId();
    }
    
    @Override
    public List<Admin> retrieveAllAdmins() {
        Query query = em.createQuery("SELECT a FROM Admin a ORDER BY a.adminName ASC");
        
        return query.getResultList();
    }
    
    @Override
    public Admin retrieveAdminByAdminId(Long adminId) throws AdminNotFoundException {
        Admin customer = em.find(Admin.class, adminId);
        
        if (customer != null) {
            return customer;
        } else {
            throw new AdminNotFoundException();
        }
    }
    
    @Override
    public Admin retrieveAdminByAdminName(String adminName) throws AdminNotFoundException {
        Query query = em.createQuery("SELECT c FROM Admin c WHERE c.customerName = :inAdminName");
        query.setParameter("inAdminName", adminName);
        try
        {
            return (Admin)query.getSingleResult();
        }
        catch(NoResultException | NonUniqueResultException ex)
        {
            throw new AdminNotFoundException("Admin with name " + adminName + " does not exist!");
        }
    }

    @Override
    public void updateAdmin(Admin admin) throws AdminNotFoundException {
        if(admin != null)
        {
            Admin adminToUpdate = retrieveAdminByAdminId(admin.getAdminId());

            adminToUpdate.setAdminName(admin.getAdminName());
            adminToUpdate.setAdminEmail(admin.getAdminEmail());
            adminToUpdate.setAdminPassword(admin.getAdminPassword());
        }
        else
        {
            throw new AdminNotFoundException("Admin ID not provided for admin to be updated");
        }
    }
    
    @Override
    public void deleteAdmin(Long adminId) throws AdminNotFoundException {
        Admin adminToDelete = retrieveAdminByAdminId(adminId);
        em.remove(adminToDelete);
    }
}
