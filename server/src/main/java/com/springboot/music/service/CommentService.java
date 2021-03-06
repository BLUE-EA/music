package com.springboot.music.service;

import com.springboot.music.domain.AllComment;
import com.springboot.music.domain.ChildComment;
import com.springboot.music.domain.Comment;

import java.util.List;

public interface CommentService {
    boolean deleteByPrimaryKey(Integer id);

    boolean insert(Comment record);

    boolean addComment(Comment comment);

    boolean insertSelective(Comment record);

    Comment selectByPrimaryKey(Integer id);

    boolean updateByPrimaryKeySelective(Comment record);

    boolean updateByPrimaryKey(Comment record);

    boolean updateCommentMsg(Comment record);

    boolean deleteComment(Integer id);

    List<Comment> allComment();

    List<AllComment> commentOfSongId(Integer songId);

    List<AllComment> commentOfSongListId(Integer songListId);

    boolean addChildComment(ChildComment childComment);

    boolean deleteAllChildComment(Integer fatherId);

    boolean deleteChildComment(Integer id);
}
