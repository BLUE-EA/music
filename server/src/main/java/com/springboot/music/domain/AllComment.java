package com.springboot.music.domain;

import java.util.List;

public class AllComment {

    private Comment fatherComment;

    private List<ChildComment> childComment;

    public void setFatherComment(Comment fatherComment){
        this.fatherComment = fatherComment;
    }

    public void setChildComment(List<ChildComment> childComment){
        this.childComment = childComment;
    }

    public Comment getFatherComment(){
        return this.fatherComment;
    }

    public List<ChildComment> getChildComment(){
        return this.childComment;
    }
}
