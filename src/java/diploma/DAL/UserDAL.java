
package diploma.DAL;

import java.util.List;
import diploma.model.User;
import org.apache.ibatis.session.SqlSession;

public class UserDAL extends BaseDAL {

    public UserDAL() {
        super();
    }
    public List<User> selectAll()
    {
        SqlSession session = sqlSessionFactory.openSession(); 
        List<User> users =  session.selectList("user.selectAll"); 
        session.close(); 
        return users;
    }
    
    public User selectById(int id)
    {
        SqlSession session = sqlSessionFactory.openSession(); 
        User user =  session.selectOne("user.selectById",id); 
        session.close(); 
        return user;
    }
    
    public User selectLastId()
    {
        SqlSession session = sqlSessionFactory.openSession(); 
        User user =  session.selectOne("user.selectLastId"); 
        session.close(); 
        return user;
    }
    
    public int insert(User user)
    {
        SqlSession session = sqlSessionFactory.openSession(); 
        int count = session.insert("user.insert",user);
        session.commit();
        session.close(); 
        return  count;
    }
     public int update(User user)
    {
        SqlSession session = sqlSessionFactory.openSession(); 
        int count = session.update("user.update",user);
        session.commit();
        session.close(); 
        return  count;
    }
     public User deleteById(int id)
    {
        SqlSession session = sqlSessionFactory.openSession(); 
        User user =  session.selectOne("user.deleteById",id);
        session.commit();
        session.close(); 
        return user;
    }
}