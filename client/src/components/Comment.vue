<template>
  <div class="comment main">
    <div class="sendComment">
      <div class="head">用户评论</div>
      <div class="cut-off"></div>
      <div class="inputBorder">
        <textarea v-model="commentText" placeholder="快来评论鸭~"></textarea>
        <el-button @click="sendComment()" type="primary" style="float: right;margin-top: 15px;margin-right: 15px;">发 送
        </el-button>
      </div>
    </div>
    <div class="allComment">
      <div class="head">全部评论</div>
      <div class="cut-off"></div>
      <div class="commentBox" v-for="(item,index0) in allComment" :key="index0">
        <div class="header">
          <div class="leftImg"><span :style="{backgroundImage: 'url('+attachImageUrl(item.imgPic)+')'}"></span></div>
          <div class="nickName">{{item.username}}</div>
        </div>
        <div class="content">{{item.content}}</div>
        <div class="time">
          {{item.time}}
          <span class="comment-button" @click="getTargetInfo(0,index0,-1,item)">回复</span>
          <span class="comment-button" @click="commentDelete(item)" v-show="userObject.id===item.id">删除</span>
        </div>
        <div class="repeat-comment" v-show="repeatComment&&target.index===index0&&target.type===0">
          <textarea v-model="replyComment" :placeholder="'回复 '+target.to"></textarea>
          <button @click="sendChildComment()" class="btn">发 送</button>
        </div>
        <div class="chile-comment" v-for="(i,index1) in item.reply" :key="index1">
          <div class="header">
            <div class="leftImg"><span :style="{backgroundImage: 'url('+attachImageUrl(i.imgPic)+')'}"></span></div>
            <div class="nickName"><span>{{i.from}}</span> &nbsp;回复 &nbsp; <span>{{i.to}}</span></div>
          </div>
          <div class="content">{{i.content}}</div>
          <div class="time">
            {{i.time}}
            <span @click="getTargetInfo(1,index0,index1,i)" class="comment-button">回复</span>
            <span class="comment-button" @click="childCommentDelete(i)" v-show="userObject.id===i.fromId">删除</span>
          </div>
          <div class="repeat-comment"
               v-show="repeatComment&&target.index===index0&&target.type===1&&target.index1===index1">
            <textarea v-model="replyComment" :placeholder="'回复 '+target.to"></textarea>
            <button @click="sendChildComment()" class="btn">发 送</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import {
    getAllComment,
    getUserOfId,
    setComment,
    setChildComment,
    deleteComment,
    deleteChildComment
  } from '../api/index';
  import mixins from '../mixins/index';
  import {mapGetters} from 'vuex';

  export default {
    name: "Comment",
    mixins: [mixins],
    created() {
      this.getContent();
      this.allComment.splice(0, 1);
    },
    data() {
      return {
        repeatComment: false,
        allCommentTemp: [
          {
            fatherComment: {},
            childComment: {},
          }
        ],
        allComment: [
          {
            commentId: '',    //这句评论的id
            id: '',           //发这句评论用户的id
            username: '',     //发这句评论用户的username
            time: '',         //发送时间
            imgPic: '',       //头像
            content: '',      //内容
            reply: [          //子评论
              {
                commentId: '',//父评论的id
                replyId: '',  //子评论的id
                time: '',     //发送时间
                content: '',  //内容
                imgPic: '',   //头像
                fromId: '',   //发送的来源用户id
                from: '',     //发送的来源用户名
                toId: '',     //回复的对象id
                to: '',       //回复的对象名
              }
            ]
          }
        ],
        commentText: '',   //评论的内容
        replyComment: '',   //回复的内容
        target: {
          fatherId: '',
          toId: '',
          to: '',
          index: '',
          index1: '',
          type: '',
        },
      }
    },
    computed: {
      ...mapGetters([
        'loginIn',
        'userObject',
      ])
    },
    methods: {
      initData() {
        this.allCommentTemp = [
          {
            fatherComment: {},
            childComment: {},
          }
        ];
        this.allComment = [
          {
            username: '',
            time: '',
            imgPic: '',
            content: '',
            reply: [
              {
                time: '',
                content: '',
                imgPic: '',
                from: '',
                to: '',
              }
            ]
          }
        ];
      },
      // 获取回复目标的信息
      getTargetInfo(type, i, j, module) {
        this.target.index = i;
        this.target.type = type;  //0代表是回复父评论，否则回复子评论
        this.target.index1 = j;
        this.repeatComment = !this.repeatComment;
        this.target.fatherId = module.commentId;
        if (type === 0) {
          this.target.toId = module.id;
          this.target.to = module.username;
          console.log(module.id);
        } else {
          this.target.toId = module.fromId;
          this.target.to = module.from;
        }
        console.log(this.target);
      },
      getContent() {
        this.initData();
        //获取当前的页面的类型
        if (this.$route.path === '/SongInfo') {
          //表示这是歌曲评论，type为0
          getAllComment(0, this.$route.query.id).then(res => {
            //返回一个列表，列表元素是AllComment类型的，现在变成了json格式
            this.allCommentTemp = res;
            console.log(res);
            this.normalAll();
          }).catch(err => {
            console.log(err);
          })
        } else {
          //表示这是歌单的评论，type为1
          getAllComment(1, this.$route.query.keywords).then(res => {
            //返回一个列表，列表元素是AllComment类型的，现在变成了json格式
            this.allCommentTemp = res;
            console.log(res);
            this.normalAll();
          }).catch(err => {
            console.log(err);
          })
        }
      },
      //发送评论
      sendComment() {
        if (this.loginIn) {
          if (this.$route.path === '/SongInfo') {
            let params = new URLSearchParams();
            params.append('content', this.commentText);
            params.append('type', '0');
            params.append('userId', this.userObject.id);
            params.append('songId', this.$route.query.id);
            params.append('songListId', null);
            setComment(params).then(res => {
              if (res.code === 1) {
                this.$message({
                  message: res.msg,
                  type: 'success'
                })
                this.getContent();
                this.allComment.splice(0, 1);
                this.commentText = '';
              } else {
                this.$message({
                  message: res.msg,
                  type: 'error'
                })
              }
            }).catch(err => {
              console.log(err);
            })
          }else{
            console.log(this.$route.keywords);
            let params = new URLSearchParams();
            params.append('content', this.commentText);
            params.append('type', '1');
            params.append('userId', this.userObject.id);
            params.append('songId', null);
            params.append('songListId', this.$route.query.keywords);
            setComment(params).then(res => {
              if (res.code === 1) {
                this.$message({
                  message: res.msg,
                  type: 'success'
                })
                this.getContent();
                this.allComment.splice(0, 1);
                this.commentText = '';
              } else {
                this.$message({
                  message: res.msg,
                  type: 'error'
                })
              }
            }).catch(err => {
              console.log(err);
            })
          }
        } else {
          this.$message({
            message: '你还未登录',
            type: 'error',
          })
        }
      },
      //发送子评论
      sendChildComment() {
        if (this.loginIn) {
          let params = new URLSearchParams();
          params.append('content', this.replyComment);
          params.append('from', this.userObject.id);
          params.append('to', this.target.toId);
          params.append('fatherId', this.target.fatherId);
          setChildComment(params).then(res => {
            if (res.code === 1) {
              this.$message({
                message: res.msg,
                type: 'success'
              })
              this.getContent();
              this.allComment.splice(0, 1);
              this.replyComment = '';
              this.repeatComment = false
            } else {
              this.$message({
                message: res.msg,
                type: 'error'
              })
            }
          }).catch(err => {
            console.log(err);
          })
        } else {
          this.$message({
            message: '你还未登录',
            type: 'error',
          })
        }
      },
      //规范格式
      normalAll() {
        for (let i = 0; i < this.allCommentTemp.length; i++) {
          let tmp = {
            commentId: '',
            id: '',
            username: '',
            time: '',
            imgPic: '',
            content: '',
            reply: [],
          };
          tmp.commentId = this.allCommentTemp[i].fatherComment.id
          getUserOfId(this.allCommentTemp[i].fatherComment.userId).then(res => {
            tmp.id = res.id
            tmp.username = res.username;
            tmp.imgPic = res.avatar;
          }).catch(err => {
            console.log(err);
          })
          tmp.time = this.allCommentTemp[i].fatherComment.createTime;
          tmp.content = this.allCommentTemp[i].fatherComment.content;
          let reply = [];   //规范子评论的格式
          for (let j = 0; j < this.allCommentTemp[i].childComment.length; j++) {
            let temp = {
              commentId: '',
              replyId: '',
              time: '',
              content: '',
              imgPic: '',
              fromId: '',
              from: '',
              toId: '',
              to: '',
            };
            temp.commentId = this.allCommentTemp[i].fatherComment.id;
            temp.replyId = this.allCommentTemp[i].childComment[j].id
            getUserOfId(this.allCommentTemp[i].childComment[j].from).then(res => {
              temp.fromId = this.allCommentTemp[i].childComment[j].from;
              temp.from = res.username;
              temp.imgPic = res.avatar;
            }).catch(err => {
              console.log(err);
            })
            getUserOfId(this.allCommentTemp[i].childComment[j].to).then(res => {
              temp.toId = this.allCommentTemp[i].childComment[j].to;
              temp.to = res.username;
            }).catch(err => {
              console.log(err);
            })
            temp.time = this.allCommentTemp[i].childComment[j].createTime;
            temp.content = this.allCommentTemp[i].childComment[j].content;
            reply.push(temp);
          }
          tmp.reply = reply;
          this.allComment.push(tmp);
        }
      },
      //删除评论
      commentDelete(module) {
        deleteComment(module.commentId).then(res => {
          if (res) {
            this.$message({
              message: '删除成功',
              type: 'success',
            })
            this.getContent();
            this.allComment.splice(0, 1);
          } else {
            this.$message({
              message: '删除失败',
              type: 'error',
            })
          }
        }).catch(err => {
          console.log(err);
        })
      },
      //删除子评论
      childCommentDelete(module) {
        deleteChildComment(module.replyId).then(res => {
          if (res) {
            this.$message({
              message: '删除成功',
              type: 'success',
            })
            this.getContent();
            this.allComment.splice(0, 1);
          } else {
            this.$message({
              message: '删除失败',
              type: 'error',
            })
          }
        }).catch(err => {
          console.log(err);
        })
      }
    }
  }
</script>

<style scoped>
  .comment .head {
    font-weight: 900;
    font-size: 20px;
  }

  .comment .cut-off {
    height: 1px;
    width: 1110px;
    margin: 20px;
  }

  .comment .sendComment {
    padding-bottom: 25px;
    margin-bottom: 50px;
  }

  .comment .sendComment .inputBorder textarea {
    height: 150px;
    width: 100%;
    font-size: 16px;
    font-family: 黑体;
    padding: 10px;
    border-radius: 15px;
    border: none;
    outline: none;
  }

  .comment .allComment {
    padding-bottom: 50px;
  }

  .comment .allComment .commentBox {
    width: 1110px;
    padding-top: 5px;
    padding-left: 20px;
    padding-right: 20px;
  }

  .comment .commentBox .leftImg {
    float: left;
    height: 50px;
    width: 50px;
    border-radius: 25px;
  }

  .comment .commentBox .leftImg span {
    float: left;
    width: 50px;
    height: 50px;
    position: relative;
    /*background-image: url("img/imgHead.jpg");*/
    background-repeat: no-repeat;
    background-size: 50px 50px;
    border-radius: 25px;
  }

  .comment .commentBox .nickName {
    line-height: 50px;
    margin-left: 65px;
  }

  .comment .commentBox .nickName a {
    color: gray;
    font-size: 14px;
  }

  .comment .commentBox .content {
    margin-left: 65px;
  }

  .comment .commentBox .time {
    font-size: 12px;
    color: #807c79;
    margin-left: 65px;
    margin-top: 10px;
    margin-bottom: 10px;
  }

  .comment .commentBox .comment-button {
    padding-left: 20px;
    cursor: pointer;
    color: cornflowerblue;
  }

  .comment .commentBox .repeat-comment {
    transition: 3s;
  }

  .comment .commentBox .repeat-comment textarea {
    height: 50px;
    width: 90%;
    font-size: 16px;
    font-family: 黑体;
    padding: 10px;
    border-radius: 15px;
    border: none;
    outline: none;
  }

  .comment .commentBox .repeat-comment .btn {
    height: 70px;
    width: 50px;
    border: none;
    background-color: #409EFF;
    border-radius: 10px;
    float: right;
    margin-right: 20px;
    outline: none;
    cursor: pointer;
  }

  .comment .commentBox .chile-comment {
    padding-left: 50px;
  }
</style>
