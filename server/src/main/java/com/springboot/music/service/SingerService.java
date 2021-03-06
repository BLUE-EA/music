package com.springboot.music.service;

import com.springboot.music.domain.Singer;

import java.util.List;

public interface SingerService {
    boolean deleteByPrimaryKey(Integer id);

    boolean insert(Singer record);

    boolean insertSelective(Singer record);

    Singer selectByPrimaryKey(Integer id);

    boolean updateByPrimaryKeySelective(Singer record);

    boolean updateByPrimaryKey(Singer record);

    boolean updateSingerPic(Singer record);

    List<Singer> allSinger();

    List<Singer> singerOfName(String name);

    List<Singer> singerOfSex(Integer sex);
}
