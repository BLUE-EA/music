package com.springboot.music.service.impl;

import com.springboot.music.dao.CollectDao;
import com.springboot.music.domain.Collect;
import com.springboot.music.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectServiceImpl implements CollectService {

    @Autowired
    private CollectDao collectDao;

    @Override
    public boolean deleteByPrimaryKey(Integer id) {
        return collectDao.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public boolean insert(Collect record) {
        return collectDao.insert(record) > 0;
    }

    @Override
    public boolean insertSelective(Collect record) {
        return collectDao.insertSelective(record) > 0;
    }

    @Override
    public Collect selectByPrimaryKey(Integer id) {
        return collectDao.selectByPrimaryKey(id);
    }

    @Override
    public boolean updateByPrimaryKeySelective(Collect record) {
        return collectDao.updateByPrimaryKeySelective(record) > 0;
    }

    @Override
    public boolean updateByPrimaryKey(Collect record) {
        return collectDao.updateByPrimaryKey(record) > 0;
    }

    @Override
    public boolean existSongId(Integer userId, Integer songId) {
        return collectDao.existSongId(userId, songId) > 0;
    }

    @Override
    public boolean updateCollectMsg(Collect collect) {
        return collectDao.updateCollectMsg(collect) > 0;
    }

    @Override
    public boolean deleteCollect(Integer userId, Integer songId) {
        return collectDao.deleteCollect(userId, songId) > 0;
    }

    @Override
    public List<Collect> allCollect() {
        return collectDao.allCollect();
    }

    @Override
    public List<Collect> collectionOfUser(Integer userId) {
        return collectDao.collectionOfUser(userId);
    }
}
