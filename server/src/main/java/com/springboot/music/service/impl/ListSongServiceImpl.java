package com.springboot.music.service.impl;

import com.springboot.music.dao.ListSongDao;
import com.springboot.music.domain.ListSong;
import com.springboot.music.service.ListSongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListSongServiceImpl implements ListSongService {

    @Autowired
    private ListSongDao listSongDao;


    @Override
    public boolean deleteByPrimaryKey(Integer id) {
        return listSongDao.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public boolean insert(ListSong record) {
        return listSongDao.insert(record) > 0;
    }

    @Override
    public boolean insertSelective(ListSong record) {
        return listSongDao.insertSelective(record) > 0;
    }

    @Override
    public ListSong selectByPrimaryKey(Integer id) {
        return listSongDao.selectByPrimaryKey(id);
    }

    @Override
    public boolean updateByPrimaryKeySelective(ListSong record) {
        return listSongDao.updateByPrimaryKeySelective(record) > 0;
    }

    @Override
    public boolean updateByPrimaryKey(ListSong record) {
        return listSongDao.updateByPrimaryKey(record) > 0;
    }

    @Override
    public boolean updateListSongMsg(ListSong record) {
        return listSongDao.updateListSongMsg(record) > 0;
    }

    @Override
    public boolean deleteListSong(Integer songId) {
        return listSongDao.deleteListSong(songId) > 0;
    }

    @Override
    public List<ListSong> allListSong() {
        return listSongDao.allListSong();
    }

    @Override
    public List<ListSong> listSongOfSongId(Integer songListId) {
        return listSongDao.listSongOfSongId(songListId);
    }
}
