package com.frostwolf.dao;

import com.frostwolf.bean.User;

import java.util.List;

public interface IUserDAO {

    List<User> findAll();

}
