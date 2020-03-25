package ejb.session.stateless;

import entity.Admin;
import java.util.List;
import javax.ejb.Local;
import util.exception.AdminNotFoundException;
import util.exception.CreateNewAdminException;

/**
 *
 * @author zhixuan
 */
@Local
public interface AdminSessionBeanLocal {
    public Long createNewAdmin(Admin newAdmin) throws CreateNewAdminException;
    public List<Admin> retrieveAllAdmins();
    public Admin retrieveAdminByAdminId(Long adminId) throws AdminNotFoundException;
    public Admin retrieveAdminByAdminName(String adminName) throws AdminNotFoundException;
    public void updateAdmin(Admin admin) throws AdminNotFoundException;
    public void deleteAdmin(Long adminId) throws AdminNotFoundException;
}
