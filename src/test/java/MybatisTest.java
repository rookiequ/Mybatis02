import com.user.dao.AccountDAO;
import com.user.dao.UserDAO;
import com.user.domain.Account;
import com.user.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * @author zzq
 */
public class MybatisTest {

    private InputStream inputStream;
    private SqlSession sqlSession;
    private UserDAO userDAO;
    private AccountDAO accountDAO;

    @Before
    public void init() throws IOException {
        //1.读取核心配置文件
        inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        //2.创建SqlSession工厂
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(inputStream);
        //3.使用工厂创建session对象
        sqlSession = sqlSessionFactory.openSession();
        //4.用session创建dao接口的代理对象
        userDAO = sqlSession.getMapper(UserDAO.class);
        accountDAO = sqlSession.getMapper(AccountDAO.class);

    }

    @After
    public void destroy() throws IOException {
        //6.释放资源
        sqlSession.close();
        inputStream.close();
    }


    @Test
    public void findAllTest(){
        List<User> userList = userDAO.findAll();
        for (User user : userList) {
            System.out.println(user.toString());
        }
    }


    @Test
    public void saveUserTest(){
        User user = new User();
        user.setUsername("刘邦昱");
        user.setBirthday(new Date());
        user.setSex("男");
        user.setAddress("四川");
        userDAO.saveUser(user);
        sqlSession.commit();
    }

    @Test
    public void deleteUserTest(){
        userDAO.deleteUser(51);
        sqlSession.commit();
    }

    @Test
    public void updateUserTest(){
        User user = new User();
        user.setId(53);
        user.setUsername("周子钦");
        user.setBirthday(new Date());
        user.setSex("男");
        user.setAddress("盐城");
        userDAO.updateUser(user);
        sqlSession.commit();
    }

    @Test
    public void findAccountWithUserTest(){
        List<Account> accountList = accountDAO.findAccountWithUser();
        for (Account account : accountList) {
            System.out.println(account.toString());
        }

    }

    @Test
    public void findUserWithAccountTest(){
        List<User> userList = userDAO.findUserWithAccount();
        for (User user : userList) {
            System.out.println(user.toString());
        }
    }

}
