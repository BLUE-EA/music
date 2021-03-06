package com.springboot.music.service;

import com.springboot.music.domain.ListSong;

import java.util.List;

public interface ListSongService {

    boolean deleteByPrimaryKey(Integer id);

    boolean insert(ListSong record);

    boolean insertSelective(ListSong record);

    ListSong selectByPrimaryKey(Integer id);

    boolean updateByPrimaryKeySelective(ListSong record);

    boolean updateByPrimaryKey(ListSong record);

    boolean updateListSongMsg(ListSong record);

    boolean deleteListSong(Integer songId);

    List<ListSong> allListSong();

    List<ListSong> listSongOfSongId(Integer songListId);
}
