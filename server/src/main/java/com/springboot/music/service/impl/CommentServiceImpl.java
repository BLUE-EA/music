package com.springboot.music.service.impl;

import com.springboot.music.dao.ChildCommentDao;
import com.springboot.music.dao.CommentDao;
import com.springboot.music.domain.AllComment;
import com.springboot.music.domain.ChildComment;
import com.springboot.music.domain.Comment;
import com.springboot.music.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private ChildCommentDao childCommentDao;

    @Override
    public boolean deleteByPrimaryKey(Integer id) {
        return commentDao.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public boolean insert(Comment record) {
        return commentDao.insert(record) > 0;
    }

    @Override
    public boolean addComment(Comment record) {
        return commentDao.insertSelective(record) > 0;
    }

    @Override
    public boolean insertSelective(Comment record) {
        return commentDao.insertSelective(record) > 0;
    }

    @Override
    public Comment selectByPrimaryKey(Integer id) {
        return commentDao.selectByPrimaryKey(id);
    }

    @Override
    public boolean updateByPrimaryKeySelective(Comment record) {
        return commentDao.updateByPrimaryKeySelective(record) > 0;
    }

    @Override
    public boolean updateByPrimaryKey(Comment record) {
        return commentDao.updateByPrimaryKey(record) > 0;
    }

    @Override
    public boolean updateCommentMsg(Comment record) {
        return commentDao.updateCommentMsg(record) > 0;
    }

    @Override
    public boolean deleteComment(Integer id) {
        return commentDao.deleteComment(id) > 0;
    }

    @Override
    public List<Comment> allComment() {
        return commentDao.allComment();
    }

    @Override
    public List<AllComment> commentOfSongId(Integer songId) {
        List<AllComment> res = new ArrayList<>();
        //获取父评论数组
        List<Comment> fatherCommentList = commentDao.commentOfSongId(songId);
        for (int i = 0; i < fatherCommentList.size(); i++) {
            int id = fatherCommentList.get(i).getId(); //获取到father_id可以去查chileComment里面的数据了
            AllComment temp = new AllComment();
            //查找子评论表
            temp.setFatherComment(fatherCommentList.get(i));
            temp.setChildComment(childCommentDao.selectByFatherId(id));
            res.add(temp);
        }
        return res;
    }

    @Override
    public List<AllComment> commentOfSongListId(Integer songListId) {
        List<AllComment> res = new ArrayList<>();
        //获取父评论数组
        List<Comment> fatherCommentList = commentDao.commentOfSongListId(songListId);
        for (int i = 0; i < fatherCommentList.size(); i++) {
            int id = fatherCommentList.get(i).getId(); //获取到father_id可以去查chileComment里面的数据了
            AllComment temp = new AllComment();
            //查找子评论表
            temp.setFatherComment(fatherCommentList.get(i));
            temp.setChildComment(childCommentDao.selectByFatherId(id));
            res.add(temp);
        }
        return res;
    }

    @Override
    public boolean addChildComment(ChildComment childComment) {
        return childCommentDao.insertSelective(childComment) > 0;
    }

    @Override
    public boolean deleteAllChildComment(Integer id) {
        return childCommentDao.deleteByFatherId(id) > 0;
    }

    @Override
    public boolean deleteChildComment(Integer id) {
        return childCommentDao.deleteByPrimaryKey(id) > 0;
    }
}
