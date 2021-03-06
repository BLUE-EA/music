<template>
  <div>
    <div class="detail main">
      <div class="picture fl"><img :src=attachImageUrl(song.pic) height="350" width="350" alt=""></div>
      <div class="info fr">
        <ul>
          <li><span class="title">{{getSongName(song.name)}}</span></li>
          <li><a href="">{{getSingerName(song.name)}}</a></li>
          <li>专辑：{{song.introduction}}</li>
        </ul>
        <div class="operation">
          <el-button type="danger" @click="toPlay(song)"><span class="el-icon-video-play"></span>&nbsp;播放歌曲</el-button>
          <el-button @click="doCollect()" style="width: 100px">
            <span v-if="InCollect()">
              <span class="el-icon-star-on"></span>&nbsp;已收藏
            </span>
            <span v-else>
              <span class="el-icon-star-off"></span>&nbsp;收藏
            </span>
          </el-button>
          <el-button @click="download()"><span class="el-icon-download"></span>&nbsp;下载</el-button>
          <el-button @click="goToComment()"><span class="el-icon-s-comment"></span>&nbsp;评论</el-button>
        </div>
      </div>
    </div>
    <div class="lyric main">
      <div class="head">歌词</div>
      <div class="content">
        <div class="box" :class="{'limit':isLimit}">
          <p v-for="songContent in parseLyric(song.lyric)">{{songContent[1]}}</p>
        </div>
        <div @click="changeStatus" style="cursor:pointer;">
          <a role="button" v-if="status=='shrink'">展开</a>
          <a role="button" v-if="status=='spread'">收缩</a>
        </div>
      </div>

    </div>
    <Comment id="comment"/>
  </div>
</template>

<script>
  import {getSongOfId, download, deleteCollect, setCollection} from '../api/index';
  import mixins from '../mixins/index';
  import {mapGetters} from 'vuex';
  import Comment from '../components/Comment'

  export default {
    name: "SongInfo",
    mixins: [mixins],
    components: {
      Comment,
    },
    computed: {
      ...mapGetters([
        'loginIn',   //监控登录状态
        'userObject', //监控登录用户的信息
        'userCollect',
      ])
    },
    data() {
      return {
        songContents: [],
        status: 'shrink',
        isLimit: true,
        songId: null,
        song: {
          name: '',
          pic: '',
          introduction: '',
        },
      }
    },
    created() {
      this.songId = this.$route.query.id
      console.log(this.songId);
      this.getSongInfo();
    },
    methods: {
      goToComment() {
        const returnEle = document.querySelector("#comment");
        if (returnEle) {
          returnEle.scrollIntoView({
            behavior: "smooth"
          });
        }
      },
      getSongInfo() {
        console.log('进入获取');
        getSongOfId(this.songId).then(res => {
          if (res !== null) {
            //获得歌曲信息成功
            this.song = res[0];
          } else {
            this.$message({
              message: '获取失败',
              type: 'error',
            })
          }
        })
      },
      changeStatus() {
        this.isLimit = !this.isLimit;
        if (this.status === 'shrink')
          this.status = 'spread';
        else
          this.status = 'shrink';
      },
      // 下载
      download() {
        console.log('下载');
        download(this.song.url)
          .then(res => {
            console.log(this.song.url);
            let content = res.data
            let eleLink = document.createElement('a')
            eleLink.download = `${this.getSongName(this.song.name)}.mp3`
            eleLink.style.display = 'none'
            // 字符内容转变成blob地址
            let blob = new Blob([content])
            eleLink.href = URL.createObjectURL(blob)
            // 触发点击
            document.body.appendChild(eleLink)
            eleLink.click()
            // 然后移除
            document.body.removeChild(eleLink)
          })
          .catch(err => {
            console.log(err)
          })
      },

      //判断是否收藏了
      InCollect() {
        if (this.song.id === null)
          return false;
        for (let i = 0; i < this.userCollect.length; i++) {
          if (this.song.id === this.userCollect[i].id) {
            return true;
          }
        }
        return false;
      },
      //收藏
      doCollect() {
        if (this.loginIn) {
          if (!this.InCollect()) {
            // 收藏
            //已经登录,setCollection添加歌曲到收藏数据表
            let params = new URLSearchParams();
            params.append('userId', this.userObject.id);
            params.append('type', '0'); //0代表收藏歌曲
            params.append('songId', this.song.id);
            setCollection(params).then(res => {
              if (res.code === 1) {
                this.$message({
                  message: res.msg,
                  type: 'success',
                })
              } else {
                this.$message({
                  message: res.msg,
                  type: 'error',
                })
              }
            })
            //更新收藏列表
            this.$store.commit('addUserCollect', this.song)
          } else {
            //取消收藏
            if (this.loginIn) {
              //找到这首歌在collect表里的id
              let userId = this.userObject.id;
              let songId = this.song.id;
              let params = new URLSearchParams();
              params.append('userId', userId);
              params.append('songId', songId);
              deleteCollect(params).then(res => {
                if (res) {
                  this.$message({
                    message: '取消收藏成功',
                    type: 'success'
                  })
                  this.$store.commit('deleteUserCollect', this.song)
                } else {
                  this.$message({
                    message: '取消收藏失败',
                    type: 'error'
                  })
                }
              })

            }
          }
        } else {
          this.$message({
            message: '你还未登录，请先登录',
            type: 'warning'
          })

        }
      }

    },
  }
</script>

<style scoped>
  li {
    list-style: none;
  }

  a {
    text-decoration: none;
  }

  a:hover {
    text-decoration: none
  }

  .fl {
    float: left;
  }

  .fr {
    float: right;
  }

  .main {
    width: 1150px;
    margin: 60px auto 100px;
  }

  .detail {
    height: 500px;
  }

  .detail .picture {
    width: 350px;
    height: 350px;
    margin-top: 50px;
  }

  .detail .picture img {
    size: 350px 350px;
  }

  .detail .info {
    width: 700px;
    height: 350px;
    margin-left: 100px;
    margin-top: 50px;
    padding-top: 50px;
  }

  .detail .info ul li {
    margin-top: 20px;
  }

  .detail .info ul li .title {
    font-size: 30px;
    font-width: 900;
  }

  .detail .info ul li a {
    color: black;
  }

  .detail .info ul li a:hover {
    color: pink;
  }

  .detail .info .operation {
    margin-top: 50px;
  }

  .head {
    font-size: 30px;
    font-weight: 900;
    margin-left: 20px;
  }

  .lyric .content {
    margin-left: 50px;
    padding-top: 40px;
    padding-bottom: 40px;
  }

  .lyric .content .limit {
    max-height: 390px;
    overflow: hidden;
  }

  .lyric .content .box p {
    font-size: 14px;
    color: #000;
    line-height: 28px;
    /*margin-bottom: 40px;*/
  }

  .lyric .content a {
    color: #2aabd2;
    font-size: 14px;
    line-height: 5px;
  }
</style>
