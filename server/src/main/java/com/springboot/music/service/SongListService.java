package com.springboot.music.service;

import com.springboot.music.domain.SongList;

import java.util.List;

public interface SongListService {

    boolean deleteByPrimaryKey(Integer id);

    boolean insert(SongList record);

    boolean insertSelective(SongList record);

    SongList selectByPrimaryKey(Integer id);

    boolean updateByPrimaryKeySelective(SongList record);

    boolean updateByPrimaryKeyWithBLOBs(SongList record);

    boolean updateByPrimaryKey(SongList record);

    boolean updateSongListMsg(SongList record);

    boolean updateSongListImg(SongList record);

    boolean deleteSongList(Integer id);

    List<SongList> allSongList();

    List<SongList> likeTitle(String title);

    List<SongList> likeStyle(String style);

    List<SongList> songListOfTitle(String title);
}
