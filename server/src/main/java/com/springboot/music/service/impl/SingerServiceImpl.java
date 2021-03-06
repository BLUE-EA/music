package com.springboot.music.service.impl;

import com.springboot.music.dao.SingerDao;
import com.springboot.music.domain.Singer;
import com.springboot.music.service.SingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SingerServiceImpl implements SingerService {

    @Autowired
    private SingerDao singerDao;

    @Override
    public boolean deleteByPrimaryKey(Integer id) {
        return singerDao.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public boolean insert(Singer record) {
        return singerDao.insert(record) > 0;
    }

    @Override
    public boolean insertSelective(Singer record) {
        return singerDao.insertSelective(record) > 0;
    }

    @Override
    public Singer selectByPrimaryKey(Integer id) {
        return singerDao.selectByPrimaryKey(id);
    }

    @Override
    public boolean updateByPrimaryKeySelective(Singer record) {
        return singerDao.updateByPrimaryKeySelective(record) > 0;
    }

    @Override
    public boolean updateByPrimaryKey(Singer record) {
        return singerDao.updateByPrimaryKey(record) > 0;
    }

    @Override
    public boolean updateSingerPic(Singer record) {
        return singerDao.updateSingerPic(record) > 0;
    }

    @Override
    public List<Singer> allSinger() {
        return singerDao.allSinger();
    }

    @Override
    public List<Singer> singerOfName(String name) {
        return null;
    }

    @Override
    public List<Singer> singerOfSex(Integer sex) {
        return null;
    }
}
