package diploma.controllers;

import java.util.List;
import diploma.DAL.UserDAL;
import diploma.model.User;


public class UserController 
{
    protected UserDAL userDal;
    public UserController() 
    {
        userDal = new UserDAL();
    }
    
    public List<User> getAllUser()
    {
        return userDal.selectAll();
    }
    
    public User getUserById(int id)
    {
        return userDal.selectById(id);
    }
    
    public User getLastId()
    {
        return userDal.selectLastId();
    }
    
     
    public int insertUser(User user)
    {   
        return userDal.insert(user);
    }
      
    public int updateUser(User user)
    {
        return userDal.update(user);
    }
    
    public User deleteUserById(int id)
    {
        return userDal.deleteById(id);
    }
}