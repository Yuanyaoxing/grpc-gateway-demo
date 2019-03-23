package com.frostwolf.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class SessionFactoryUtil {
    //单例的对象实例
    private static SqlSessionFactory sessionFactory;

    private SessionFactoryUtil() {}

    public static synchronized SqlSession getSession() {
        try {
            InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
            if(null == sessionFactory) {
                sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sessionFactory.openSession();
    }

}
