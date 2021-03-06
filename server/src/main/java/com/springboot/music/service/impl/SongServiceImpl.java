package com.springboot.music.service.impl;

import com.springboot.music.dao.SongDao;
import com.springboot.music.domain.Song;
import com.springboot.music.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongServiceImpl implements SongService {

    @Autowired
    private SongDao songDao;


    @Override
    public boolean deleteByPrimaryKey(Integer id) {
        return songDao.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public boolean insert(Song record) {
        return songDao.insert(record) > 0;
    }

    @Override
    public boolean insertSelective(Song record) {
        return songDao.insertSelective(record) > 0;
    }

    @Override
    public Song selectByPrimaryKey(Integer id) {
        return songDao.selectByPrimaryKey(id);
    }

    @Override
    public boolean updateByPrimaryKeySelective(Song record) {
        return songDao.updateByPrimaryKeySelective(record) > 0;
    }

    @Override
    public boolean updateByPrimaryKeyWithBLOBs(Song record) {
        return songDao.updateByPrimaryKeyWithBLOBs(record) > 0;
    }

    @Override
    public boolean updateByPrimaryKey(Song record) {
        return songDao.updateByPrimaryKey(record) > 0;
    }

    @Override
    public boolean updateSongMsg(Song record) {
        return songDao.updateSongMsg(record) > 0;
    }

    @Override
    public boolean updateSongUrl(Song record) {
        return songDao.updateSongUrl(record) > 0;
    }

    @Override
    public boolean updateSongPic(Song record) {
        return songDao.updateSongPic(record) > 0;
    }

    @Override
    public boolean deleteSong(Integer id) {
        return songDao.deleteSong(id) > 0;
    }

    @Override
    public List<Song> allSong() {
        return songDao.allSong();
    }

    @Override
    public List<Song> songOfSingerId(Integer singerId) {
        return songDao.songOfSingerId(singerId);
    }

    @Override
    public List<Song> songOfId(Integer id) {
        return songDao.songOfId(id);
    }

    @Override
    public List<Song> songOfSingerName(String name) {
        return songDao.songOfSingerName(name);
    }

    @Override
    public List<Song> songOfName(String name) {
        return songDao.songOfName(name);
    }

    @Override
    public List<Song> likeSongOfName(String name) {
        return songDao.likeSongOfName("%"+name+"%");
    }
}