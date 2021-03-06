package com.springboot.music.service;

import com.springboot.music.domain.Consumer;

import java.util.List;

public interface ConsumerService {

    boolean deleteByPrimaryKey(Integer id);

    boolean insert(Consumer record);

    boolean insertSelective(Consumer record);

    Consumer selectByPrimaryKey(Integer id);

    boolean updateByPrimaryKeySelective(Consumer record);

    boolean updateByPrimaryKey(Consumer record);

    boolean verifyPassword(String username, String password);

    boolean existUsername(String username);

    boolean addUser(Consumer consumer);

    boolean updateUserMsg(Consumer record);

    boolean updateUserAvatar(Consumer record);

    boolean deleteUser(Integer id);

    List<Consumer> allUser();

    List<Consumer> userOfId(Integer id);

    List<Consumer> loginStatus(String username);
}
