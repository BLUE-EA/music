package com.springboot.music.service.impl;

import com.springboot.music.dao.ConsumerDao;
import com.springboot.music.domain.Consumer;
import com.springboot.music.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsumerServiceImpl implements ConsumerService {

    @Autowired
    private ConsumerDao consumerDao;


    @Override
    public boolean deleteByPrimaryKey(Integer id) {
        return consumerDao.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public boolean insert(Consumer record) {
        return consumerDao.insertSelective(record) > 0;
    }

    @Override
    public boolean insertSelective(Consumer record) {
        return consumerDao.insertSelective(record) > 0;
    }

    @Override
    public Consumer selectByPrimaryKey(Integer id) {
        return consumerDao.selectByPrimaryKey(id);
    }

    @Override
    public boolean updateByPrimaryKeySelective(Consumer record) {
        return consumerDao.updateByPrimaryKeySelective(record) > 0;
    }

    @Override
    public boolean updateByPrimaryKey(Consumer record) {
        return consumerDao.updateByPrimaryKey(record) > 0;
    }

    @Override
    public boolean verifyPassword(String username, String password) {
        Consumer user = new Consumer();
        user.setUsername(username);
        user.setPassword(password);
        return consumerDao.verifyPassword(user) > 0;
    }

    @Override
    public boolean existUsername(String username) {
        return consumerDao.existUsername(username) > 0;
    }

    @Override
    public boolean addUser(Consumer consumer) {
        return consumerDao.insertSelective(consumer) > 0;
    }

    @Override
    public boolean updateUserMsg(Consumer record) {
        return consumerDao.updateUserMsg(record) > 0;
    }

    @Override
    public boolean updateUserAvatar(Consumer record) {
        return consumerDao.updateUserAvatar(record) > 0;
    }

    @Override
    public boolean deleteUser(Integer id) {
        return consumerDao.deleteUser(id) > 0;
    }

    @Override
    public List<Consumer> allUser() {
        return consumerDao.allUser();
    }

    @Override
    public List<Consumer> userOfId(Integer id) {
        return consumerDao.userOfId(id);
    }

    @Override
    public List<Consumer> loginStatus(String username) {
        return consumerDao.loginStatus(username);
    }
}
