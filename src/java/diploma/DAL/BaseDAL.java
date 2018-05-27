/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diploma.DAL;

import java.io.IOException;
import java.io.Reader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;


public class BaseDAL {
    
    protected static String config = "diploma/mybatis/config.xml";
    protected SqlSessionFactory sqlSessionFactory;
    public BaseDAL() {
        Reader reader = null;
        try {   
            reader = Resources.getResourceAsReader("diploma/mybatis/config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            System.out.println("create sqlSessionFactory  +");
        } catch (IOException ex) {
            Logger.getLogger(BaseDAL.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(BaseDAL.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }
    
}