import com.frostwolf.bean.User;
import com.frostwolf.dao.IUserDAO;
import com.frostwolf.dao.impl.UserDAOImpl;
import org.junit.Test;

import java.util.List;

public class MyBatisTest {

    @Test
    public void findAll() {
        IUserDAO userDAO = new UserDAOImpl();
        List<User> users = userDAO.findAll();
        System.out.println(users);
    }

}
