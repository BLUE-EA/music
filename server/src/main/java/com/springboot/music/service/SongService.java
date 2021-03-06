package com.springboot.music.service;

import com.springboot.music.domain.Song;

import java.util.List;

public interface SongService {

    boolean deleteByPrimaryKey(Integer id);

    boolean insert(Song record);

    boolean insertSelective(Song record);

    Song selectByPrimaryKey(Integer id);

    boolean updateByPrimaryKeySelective(Song record);

    boolean updateByPrimaryKeyWithBLOBs(Song record);

    boolean updateByPrimaryKey(Song record);

    boolean updateSongMsg(Song record);

    boolean updateSongUrl(Song record);

    boolean updateSongPic(Song record);

    boolean deleteSong(Integer id);

    List<Song> allSong();

    List<Song> songOfSingerId(Integer singerId);

    List<Song> songOfId(Integer id);

    List<Song> songOfSingerName(String name);

    List<Song> songOfName(String name);

    List<Song> likeSongOfName(String name);
}
