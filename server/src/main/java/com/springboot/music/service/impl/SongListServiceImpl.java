package com.springboot.music.service.impl;

import com.springboot.music.dao.SongListDao;
import com.springboot.music.domain.SongList;
import com.springboot.music.service.SongListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongListServiceImpl implements SongListService {

    @Autowired
    private SongListDao songListDao;


    @Override
    public boolean deleteByPrimaryKey(Integer id) {
        return songListDao.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public boolean insert(SongList record) {
        return songListDao.insert(record) > 0;
    }

    @Override
    public boolean insertSelective(SongList record) {
        return songListDao.insertSelective(record) > 0;
    }

    @Override
    public SongList selectByPrimaryKey(Integer id) {
        return songListDao.selectByPrimaryKey(id);
    }

    @Override
    public boolean updateByPrimaryKeySelective(SongList record) {
        return songListDao.updateByPrimaryKeySelective(record) > 0;
    }

    @Override
    public boolean updateByPrimaryKeyWithBLOBs(SongList record) {
        return songListDao.updateByPrimaryKeyWithBLOBs(record) > 0;
    }

    @Override
    public boolean updateByPrimaryKey(SongList record) {
        return songListDao.updateByPrimaryKey(record) > 0;
    }

    @Override
    public boolean updateSongListMsg(SongList record) {
        return songListDao.updateSongListMsg(record) > 0;
    }

    @Override
    public boolean updateSongListImg(SongList record) {
        return songListDao.updateSongListImg(record) > 0;
    }

    @Override
    public boolean deleteSongList(Integer id) {
        return songListDao.deleteSongList(id) > 0;
    }

    @Override
    public List<SongList> allSongList() {
        return songListDao.allSongList();
    }

    @Override
    public List<SongList> likeTitle(String title) {
        return songListDao.likeTitle(title);
    }

    @Override
    public List<SongList> likeStyle(String style) {
        return songListDao.likeStyle(style);
    }

    @Override
    public List<SongList> songListOfTitle(String title) {
        return songListDao.songListOfTitle(title);
    }
}
