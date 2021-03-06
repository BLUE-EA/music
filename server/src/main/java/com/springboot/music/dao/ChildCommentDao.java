package com.springboot.music.dao;

import com.springboot.music.domain.ChildComment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChildCommentDao {
    int deleteByPrimaryKey(Integer id);

    int insert(ChildComment record);

    int insertSelective(ChildComment record);

    ChildComment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChildComment record);

    int updateByPrimaryKey(ChildComment record);

    List<ChildComment> selectByFatherId(Integer fatherId);

    int deleteByFatherId(Integer id);
}