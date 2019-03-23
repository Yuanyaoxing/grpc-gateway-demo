package com.frostwolf.dao.impl;

import com.frostwolf.bean.User;
import com.frostwolf.dao.IUserDAO;
import com.frostwolf.util.SessionFactoryUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class UserDAOImpl implements IUserDAO {

    @Override
    public List<User> findAll() {
        SqlSession session = null;
        List<User> users = null;

        try {
            session = SessionFactoryUtil.getSession();
            IUserDAO userDAO = session.getMapper(IUserDAO.class);
            users = userDAO.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(null != session) {
                session.close();
            }
        }

        return users;
    }
}
