package com.springboot.music.service;

import com.springboot.music.domain.Collect;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CollectService {
    boolean deleteByPrimaryKey(Integer id);

    boolean insert(Collect record);

    boolean insertSelective(Collect record);

    Collect selectByPrimaryKey(Integer id);

    boolean updateByPrimaryKeySelective(Collect record);

    boolean updateByPrimaryKey(Collect record);

    boolean existSongId(@Param("userId") Integer userId, @Param("songId") Integer songId);

    boolean updateCollectMsg(Collect collect);

    boolean deleteCollect(@Param("userId") Integer userId, @Param("songId") Integer songId);

    List<Collect> allCollect();

    List<Collect> collectionOfUser(Integer userId);
}
