package com.frostwolf.dao.impl;

import com.frostwolf.dao.IWeatherDAO;
import com.frostwolf.util.SessionFactoryUtil;
import org.apache.ibatis.session.SqlSession;


public class WeatherDAOImpl implements IWeatherDAO {
    @Override
    public String getWeatherByDate(String date) {
        SqlSession session = null;
        String weather = "";

        try {
            session = SessionFactoryUtil.getSession();
            IWeatherDAO weatherDAO = session.getMapper(IWeatherDAO.class);
            weather = weatherDAO.getWeatherByDate(date);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(null != session) {
                session.close();
            }
        }
        return weather;
    }
}
