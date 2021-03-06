<template>
  <div class="audioplayer">
    <div class="ap-status">
      <!-- 播放列表 -->
      <div class="ap-playlist" :class="{'ap-playlist-hide': !listShow}">
        <!-- 列表头部 -->
        <div class="ap-playlist-header">
          <div class="ap-playlist-header-name">
            <span class="ap-playlist-header-title">播放列表</span>
            <span class="ap-playlist-header-amount">(共{{getListLength()}}首)</span>
          </div>
          <div class="ap-playlist-header-artist">歌手</div>
          <div class="ap-playlist-header-close" style="cursor: pointer" @click="handleList">×</div>
        </div>
        <!-- 列表内容 -->
        <div class="ap-playlist-body ps">
          <ol>
            <li class="ap-playlist-line"
                v-for="(item,index) in listOfSongs"
                @click="toPlay(item, index)"
                :key="index"
                :class="{'ap-playlist-line-active': item.id===id}"
            >
              <div class="ap-playlist-line-name">{{getSongName(item.name)}}-{{item.introduction}}</div>
              <div class="ap-playlist-line-artist">{{getSingerName(item.name)}}</div>
            </li>
          </ol>
        </div>
      </div>
      <!-- 控制器综合，包含控制器，音量，进度条等 -->
      <div class="ap-controller">
        <div class="ap-controller-left">
          <!-- 封面 -->
          <div class="ap-cover" style="cursor: pointer" @click="toPage('/Lyric')"
               :style="{backgroundImage: 'url('+picUrl+')'}">
            <!---->
          </div>
          <!-- 上一首 -->
          <div class="ap-button ap-skipback-button" @click="lastSong()">
            <!--@click="skipBack"-->
            <svg version="1.1" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 32 32">
              <path
                d="M20.229 24.343l-10.286-6.171c-0.914-0.571-1.486-1.371-1.486-2.171s0.571-1.714 1.486-2.171l10.286-6.171c0.914-0.571 1.829-0.686 2.629-0.229 0.686 0.457 1.143 1.257 1.143 2.286v12.571c0 1.029-0.457 1.943-1.143 2.286-0.343 0.229-0.686 0.229-1.029 0.229-0.457 0.114-1.029-0.114-1.6-0.457zM21.143 9.143l-10.286 6.171c-0.343 0.229-0.571 0.457-0.571 0.686s0.229 0.571 0.571 0.686l10.286 6.171c0.457 0.229 0.686 0.229 0.914 0.229 0.229-0.114 0.229-0.457 0.229-0.8v-12.571c0-0.457-0.114-0.686-0.229-0.8 0 0-0.114 0-0.229 0-0.229-0.114-0.457 0-0.686 0.229z"></path>
              <path
                d="M8.229 7.771v16.571c0 0.457 0.343 0.914 0.914 0.914v0c0.457 0 0.914-0.343 0.914-0.914v-16.571c0-0.457-0.343-0.914-0.914-0.914v0c-0.457 0-0.914 0.343-0.914 0.914z"></path>
            </svg>
          </div>
          <!-- 播放与暂停 -->
          <div class="ap-button ap-play-button" @click="togglePlay()">
            <!--@click="play"-->
            <svg v-if="!isPlay" version="1.1" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 32 32">
              <path
                d="M22.756 16.711l-8.8 5.422c-0.444 0.267-0.978 0.089-1.244-0.267-0.089-0.178-0.089-0.267-0.089-0.444v-10.844c0-0.533 0.356-0.889 0.889-0.889 0.178 0 0.356 0.089 0.444 0.089l8.8 5.422c0.444 0.267 0.533 0.8 0.267 1.244-0.089 0.089-0.178 0.178-0.267 0.267z"></path>
            </svg>
            <svg v-else version="1.1" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 32 32">
              <path
                d="M11.556 8.889v0c0.711 0 1.333 0.622 1.333 1.333v11.556c0 0.711-0.622 1.333-1.333 1.333v0c-0.711 0-1.333-0.622-1.333-1.333v-11.556c0-0.711 0.622-1.333 1.333-1.333z"></path>
              <path
                d="M21.333 8.889v0c0.711 0 1.333 0.622 1.333 1.333v11.556c0 0.711-0.622 1.333-1.333 1.333v0c-0.711 0-1.333-0.622-1.333-1.333v-11.556c0-0.711 0.622-1.333 1.333-1.333z"></path>
            </svg>
          </div>
          <!-- 下一首 -->
          <div class="ap-button ap-skipforward-button" @click="nextSong()">
            <!--@click="skipForward"-->
            <svg version="1.1" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 32 32">
              <path
                d="M10.514 24.914c-0.343 0-0.686-0.114-1.029-0.229-0.686-0.457-1.143-1.257-1.143-2.286v-12.571c0-1.029 0.457-1.943 1.143-2.286 0.686-0.457 1.714-0.343 2.629 0.229l10.286 6.171c0.914 0.571 1.486 1.371 1.486 2.171s-0.571 1.714-1.486 2.171l-10.286 6.171c-0.571 0.229-1.143 0.457-1.6 0.457zM10.514 8.8c-0.114 0-0.114 0-0.229 0-0.229 0.114-0.229 0.457-0.229 0.8v12.571c0 0.457 0.114 0.686 0.229 0.8s0.457 0.114 0.914-0.229l10.286-6.171c0.343-0.229 0.571-0.457 0.571-0.686s-0.229-0.457-0.571-0.686l-10.286-6.171c-0.343-0.114-0.571-0.229-0.686-0.229z"></path>
              <path
                d="M23.086 6.857v0c-0.457 0-0.914 0.343-0.914 0.914v16.571c0 0.457 0.343 0.914 0.914 0.914v0c0.457 0 0.914-0.343 0.914-0.914v-16.571c0-0.571-0.343-0.914-0.914-0.914z"></path>
            </svg>
          </div>
        </div>
        <!--进度条/歌曲信息-->
        <div class="ap-controller-center">
          <!--歌曲信息-->
          <div class="ap-song-msg">
            <span class="ap-name">{{title}}</span>
            <span class="ap-artist">-{{artist}}</span>
            <span class="ap-time">
              <span class="ap-current-time">{{nowTime}}</span>
              <span> / </span>
            <span class="ap-total-time">{{songTime}}</span>
            </span>
          </div>
          <!--进度条-->
          <div class="ap-progress-line" @mousemove="mousemove" @mouseleave="mouseup" ref="progressLine">
            <div class="ap-play-track">
              <!--总进度-->
              <div class="ap-play-bar-wrap" ref="progress" @click="updatemove">
                <!--缓存进度-->
                <!--<div class="ap-buffer-bar"></div>-->
                <!--当前进度-->
                <div ref="curProgress" class="ap-progress-bar" :style="{width: curLength+'%'}"></div>
              </div>
              <!--进度按钮-->
              <div class="ap-progress-thumb" :style="{left: curLength+'%'}" @mousedown="mousedown" @mouseup="mouseup">
                <div class="ap-progress-thumb-dot"></div>
              </div>
            </div>
          </div>
        </div>
        <div class="ap-controller-right">
          <!-- 竖线 -->
          <div class="ap-line">
            <div class="ap-line-draw"></div>
          </div>
          <!-- 音量 -->
          <div class="ap-button ap-volume-button">
            <svg version="1.1" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 32 32">
              <path
                d="M16.571 27.429c-0.457 0-0.8-0.114-1.143-0.457l-5.943-5.257h-2.743c-1.257 0-2.286-1.029-2.286-2.286v-5.829c0-1.257 1.029-2.286 2.286-2.286h2.743l5.943-5.257c0.686-0.571 1.829-0.571 2.4 0.114 0.229 0.343 0.457 0.686 0.457 1.143v18.286c0 1.029-0.8 1.829-1.714 1.829zM6.857 13.143c-0.343 0-0.571 0.229-0.571 0.571v5.829c0 0.343 0.229 0.571 0.571 0.571h3.429l6.4 5.714v-18.171l-6.4 5.6h-3.429z"></path>
              <path
                d="M22.154 21.797c1.714-1.45 2.703-3.532 2.703-5.797 0-2.247-0.864-4.207-2.449-5.66-0.349-0.32-0.891-0.296-1.211 0.053s-0.296 0.891 0.053 1.211c1.232 1.129 1.893 2.631 1.893 4.397 0 1.755-0.763 3.361-2.097 4.489-0.361 0.306-0.406 0.847-0.101 1.208s0.847 0.406 1.208 0.101z"></path>
              <path
                d="M25.355 24.539c2.517-2.139 3.959-5.253 3.959-8.653 0-3.22-1.389-6.329-3.697-8.509-0.344-0.325-0.887-0.31-1.212 0.035s-0.31 0.887 0.035 1.212c1.971 1.861 3.16 4.523 3.16 7.263 0 2.896-1.221 5.533-3.355 7.347-0.361 0.307-0.405 0.848-0.098 1.208s0.848 0.405 1.208 0.098z"></path>
            </svg>
          </div>
          <!-- 音量进度条 -->
          <div class="ap-volume-line" ref="volumeProgressLine" @mousemove="volumeMousemove" @mouseleave="volumeUp">
            <div class="ap-volume-track" ref="trackV">
              <div class="ap-volume-bar-wrap">
                <div class="ap-volume-bar" ref="volumeProgress" :style="{width: volumeCurLength+'%'}"></div>
              </div>
              <!--拖动的点-->
              <div class="ap-volume-thumb" :style="{left: volumeCurLength+'%'}" @mousedown="volumeDown"
                   @mouseup="volumeUp">
                <div class="ap-volume-thumb-dot"></div>
              </div>
            </div>
          </div>
          <!--我喜欢按钮-->
          <div class="ap-button" @click="doCollect()">
            <i v-if="InCollect()" class="el-icon-star-on"></i>
            <i v-else class="el-icon-star-off"></i>
          </div>
          <!--下载按钮-->
          <div class="ap-button" @click="download">
            <i class="el-icon-download"></i>
          </div>
          <!--列表按钮-->
          <div class="ap-button ap-playlist-button" @click="handleList">
            <div class="ap-playlist-button-bg">
              <svg version="1.1" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 32 32">
                <path
                  d="M5.371 6.857h9.943c0.457 0 0.8 0.343 0.8 0.8v0c0 0.457-0.343 0.8-0.8 0.8h-9.943c-0.457 0-0.8-0.343-0.8-0.8v0c0-0.457 0.343-0.8 0.8-0.8z"></path>
                <path
                  d="M5.371 11.086h7.429c0.457 0 0.914 0.343 0.914 0.8v0c0 0.457-0.343 0.8-0.8 0.8h-7.543c-0.457 0-0.8-0.343-0.8-0.8v0c0-0.457 0.343-0.8 0.8-0.8z"></path>
                <path
                  d="M5.371 15.2h5.829c0.457 0 0.8 0.343 0.8 0.8v0c0 0.457-0.343 0.8-0.8 0.8h-5.829c-0.457 0.114-0.8-0.343-0.8-0.8v0c0-0.343 0.343-0.8 0.8-0.8z"></path>
                <path
                  d="M20.571 6.857v0c0.457 0 0.8 0.343 0.8 0.8v14.057c0 0.457-0.343 0.8-0.8 0.8v0c-0.457 0-0.8-0.343-0.8-0.8v-14.057c0-0.457 0.457-0.8 0.8-0.8z"></path>
                <path
                  d="M20.914 8.457c0 0 0.114 0 0.229 0.114 0.229 0.114 0.571 0.229 0.914 0.343 0.914 0.457 1.943 1.257 2.743 2.286 0.343 0.343 0.686 0.8 0.914 1.257 0.229 0.343 0.8 0.457 1.143 0.229s0.457-0.8 0.229-1.143c-0.343-0.571-0.686-1.029-1.029-1.486-0.914-1.143-2.057-2.057-3.2-2.514-0.686-0.343-1.257-0.571-1.6-0.571-0.457-0.114-0.914 0.229-0.914 0.686-0.114 0.343 0.114 0.8 0.571 0.8z"></path>
                <path
                  d="M17.371 24.686c1.371 0 2.4-1.143 2.4-2.4s-1.143-2.4-2.4-2.4-2.514 1.029-2.514 2.4 1.143 2.4 2.514 2.4zM17.371 26.286c-2.286 0-4.114-1.829-4.114-4s1.829-4 4.114-4 4.114 1.829 4.114 4-1.829 4-4.114 4z"></path>
              </svg>
              <!--<span class="ap-playlist-button-amount">{{songList.length}}</span>-->
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import {mapGetters} from 'vuex';
  import mixins from '../mixins/index';
  import {download, setCollection, getCollectionOfUser, getSongOfId, deleteCollect} from '../api/index';

  export default {

    name: "PlayBar",
    mixins: [mixins],
    data() {
      return {
        nowTime: '00:00',
        songTime: '00:00',
        curLength: 0, // 进度条的位置
        volumeCurLength: 0,  //音量当前进度
        progressLength: 0, // 进度条总长度
        volumeLength: 0,    //音量总长度
        mouseStartX: 0,     //左右拖动，拖拽开始位置
        volumeStartX: 0,    //音量拖拽开始的位置
        tag: false,     //进度条当鼠标按下去开始变为true
        tag1: false,    //音量鼠标按下去变为true
        listShow: false,  //列表显示
      }
    },
    mounted() {
      this.progressLength = this.$refs.progress.getBoundingClientRect().width;
      this.volumeLength = this.$refs.volumeProgressLine.getBoundingClientRect().width;
      this.volumeCurLength = this.volume
    },
    watch: {
      curTime() {
        this.nowTime = this.formatSeconds(this.curTime)
        this.songTime = this.formatSeconds(this.duration)
        // 移动进度条
        this.curLength = (this.curTime / this.duration) * 100
        // 处理歌词位置及颜色
      },
    },
    computed: {
      ...mapGetters([
        'id',          //播放歌曲的id
        'url',         //播放歌曲的url，没有加域名端口
        'isPlay',      //播放状态
        'title',       //播放歌曲的歌名
        'artist',      //播放中歌曲的歌手
        'duration',    //总时间
        'curTime',     //当前播放的时间位置
        'picUrl',      //
        'volume',      //音量

        'listOfSongs', //临时歌曲列表，即此次的歌曲历史记录
        'listIndex',   //临时列表中的索引，用于定位临时列表中所播放的歌曲

        'loginIn',    //监控是否登录
        'userObject', //当前登录的用户信息
        'userCollect',//
        'playingSong',//正在播放的歌曲
      ])
    },
    methods: {
      toPage(path) {
        this.$router.push({path: path})
      },
      //关闭列表
      handleList() {
        this.listShow = !this.listShow;
      },
      //控制音乐播放暂停
      togglePlay() {
        // 做判断，将缓存的isPlay状态改变
        console.log(this.$store.state.song.id);
        console.log(this.$store.state.song.url);
        console.log(this.$store.state.song.title);
        if (this.isPlay) {
          this.$store.commit('setIsPlay', false);
        } else {
          this.$store.commit('setIsPlay', true);
        }
      },
      // 解析播放时间
      formatSeconds(value) {
        let theTime = value
        let theTime1 = '0'
        let theTime2 = '0'
        if (parseInt(theTime) > 60) {
          theTime1 = parseInt(theTime / 60) // 分
          theTime = parseInt(theTime % 60) // 秒
          // 是否超过一个小时
          if (theTime1 > 60) {
            theTime2 = parseInt(theTime1 / 60) // 小时
            theTime1 = 60 // 分
          }
        }
        // 多少秒
        if (parseInt(theTime) < 10) {
          var result = '0:0' + parseInt(theTime)
        } else {
          result = '0:' + parseInt(theTime)
        }
        // 多少分钟时
        if (theTime1 > 0) {
          if (parseInt(theTime) < 10) {
            result = '0' + parseInt(theTime)
          } else {
            result = parseInt(theTime)
          }
          result = parseInt(theTime1) + ':' + result
        }
        // 多少小时时
        if (theTime2 > 0) {
          if (parseInt(theTime) < 10) {
            result = '0' + parseInt(theTime)
          } else {
            result = parseInt(theTime)
          }
          result = parseInt(theTime2) + ':' + parseInt(theTime1) + ':' + result
        }
        return result
      },
      //进度条拖拽开始
      mousedown(e) {
        this.mouseStartX = e.clientX;
        this.tag = true;
      },
      //音量进度条拖拽开始，鼠标按下去的事件
      volumeDown(e) {
        this.volumeStartX = e.clientX;
        this.tag1 = true;
      },
      //拖拽结束
      mouseup() {
        this.tag = false;
      },
      volumeUp() {
        this.tag1 = false;
      },
      //拖拽中
      mousemove(e) {
        if (!this.duration) {
          return false;
        }
        if (this.tag) {
          let movementX = e.clientX - this.mouseStartX;   //点点移动的距离（像素距离）
          let curLength = this.$refs.curProgress.getBoundingClientRect().width;
          let newPercent = ((movementX + curLength) / this.progressLength) * 100;
          if (newPercent > 100) {
            newPercent = 100;
          }
          this.curLength = newPercent;
          //将点点移过去
          this.mouseStartX = e.clientX;
          this.changeTime(newPercent)
        }
      },
      volumeMousemove(e) {
        if (this.tag1) {
          let volumeMovementX = e.clientX - this.volumeStartX;   //点点移动的距离（像素距离）
          let volumeLength = this.$refs.volumeProgress.getBoundingClientRect().width;
          let newPercent = ((volumeMovementX + volumeLength) / this.volumeLength) * 100;
          if (newPercent > 100) {
            newPercent = 100;
          } else if (newPercent < 0) {
            newPercent = 0;
          }
          this.volumeCurLength = newPercent;
          //将点点移过去
          this.volumeStartX = e.clientX;
          this.changeVolume(newPercent);
        }
      },
      //更改歌曲进度
      changeTime(percent) {
        let newCurTime = (percent * 0.01) * this.duration;
        this.$store.commit('setChangeTime', newCurTime);
      },
      //更改音量进度
      changeVolume(percent) {
        this.$store.commit('setVolume', percent);
      },
      //点击进度条会改变进度
      updatemove(e) {
        if (!this.tag) {
          //进度条左侧坐标
          let curLengthX = this.$refs.progress.getBoundingClientRect().left;
          console.log(curLengthX);
          console.log(e.clientX);
          let newPercent = ((e.clientX - curLengthX) / this.progressLength) * 100;
          if (newPercent > 100) {
            newPercent = 100;
          } else if (newPercent < 0) {
            newPercent = 0;
          }
          this.curLength = newPercent;
          this.changeTime(newPercent);
        }
      },
      // 下载
      download() {
        download(this.url)
          .then(res => {
            let content = res.data
            let eleLink = document.createElement('a')
            eleLink.download = `${this.title}.mp3`
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
      //获取小列表的长度
      getListLength() {
        if (this.listOfSongs === null) {
          return 0;
        } else {
          return this.listOfSongs.length;
        }
      },
      //上一首
      lastSong() {
        let index = this.listIndex;
        if (index === 0) {
          index = 0;
          this.$message("到顶了");
          return;
        } else {
          index = index - 1;
        }
        console.log(index);
        this.toPlay(this.listOfSongs[index]);
      },
      //下一首
      nextSong() {
        let index = this.listIndex;
        if (index === this.listOfSongs.length - 1) {
          index = this.listOfSongs.length - 1;
          this.$message("到底了");
          return;
        } else {
          index = index + 1;
        }
        console.log(index);
        this.toPlay(this.listOfSongs[index]);
      },
      //判断是否收藏了
      InCollect() {
        if (this.id === null)
          return false;
        for (let i = 0; i < this.userCollect.length; i++) {
          if (this.id === this.userCollect[i].id) {
            return true;
          }
        }
        return false;
      },
      //收藏
      doCollect() {
        if (!this.InCollect()) {
          // 收藏
          if (this.loginIn) {
            //已经登录,setCollection添加歌曲到收藏数据表
            let params = new URLSearchParams();
            params.append('userId', this.userObject.id);
            params.append('type', '0'); //0代表收藏歌曲
            params.append('songId', this.id);
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
            this.$store.commit('addUserCollect', this.playingSong)
          } else {
            this.$message({
              message: '你还未登录，请先登录',
              type: 'warning'
            })
          }
        } else {
          //取消收藏
          if (this.loginIn) {
            //找到这首歌在collect表里的id
            let userId = this.userObject.id;
            let songId = this.id;
            let params = new URLSearchParams();
            params.append('userId', userId);
            params.append('songId', songId);
            deleteCollect(params).then(res => {
              if (res) {
                this.$message({
                  message: '取消收藏成功',
                  type: 'success'
                })
                this.$store.commit('deleteUserCollect', this.playingSong)
              } else {
                this.$message({
                  message: '取消收藏失败',
                  type: 'error'
                })
              }
            })

          }
        }
      }

    },

  }
</script>

<style scoped>
  /*播放列表*/
  .ps {
    /*overflow: hidden !important;*/
    overflow-y: scroll;
    overflow-anchor: none;
    -ms-overflow-style: none;
    touch-action: auto;
    -ms-touch-action: auto;
  }

  ol {
    padding: 0;
    margin: 0;
  }

  .audioplayer .ap-playlist {
    width: 620px;
    position: absolute;
    right: 0;
    bottom: 60px;
    background-color: #384245;
    border-radius: 6px 6px 0 0;
    transition: 0.3s;
    -webkit-transform-origin: 0 100%;
    transform-origin: 0 100%;
    color: rgba(255, 255, 255, 0.4);
    font-weight: lighter;
    z-index: 10;
  }

  .audioplayer .ap-playlist.ap-playlist-hide {
    -webkit-transform: translateY(500px);
    transform: translateY(500px);
  }

  .audioplayer .ap-playlist .ap-playlist-header {
    padding: 16px 20px 16px 20px;
    display: flex;
    cursor: default;
  }

  .audioplayer .ap-playlist .ap-playlist-header .ap-playlist-header-name {
    flex: 1;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
  }

  .audioplayer .ap-playlist-header .ap-playlist-header-close {
    font-size: 14px;
    margin-left: 20px;
  }

  .audioplayer .ap-playlist .ap-playlist-body {
    position: relative;
    background-color: #455155;
    height: 260px;
  }

  .audioplayer .ap-playlist .ap-playlist-line {
    display: flex;
    cursor: default;
    justify-content: space-between;
  }

  .audioplayer .ap-playlist .ap-playlist-line:hover {
    background: #425961;
    color: #50c9da;
  }

  .audioplayer .ap-playlist .ap-playlist-line.ap-playlist-line-active {
    background: #425961;
    color: #32B0DA;
  }

  .audioplayer .ap-playlist .ap-playlist-body li {
    padding: 10px 50px 10px 20px;
    transition: 0.2s;
    cursor: pointer;
    line-height: 21px;
  }

  ::-webkit-scrollbar-track-piece {
    background-color: #455155; /*滚动条的背景颜色*/
    -webkit-border-radius: 0; /*滚动条的圆角宽度*/
  }

  ::-webkit-scrollbar {
    width: 4px; /*滚动条的宽度*/
    height: 4px; /*滚动条的高度*/
  }

  ::-webkit-scrollbar-thumb:vertical { /*垂直滚动条的样式*/
    height: 50px;
    background-color: #384245;
    -webkit-border-radius: 4px;
    outline-offset: -2px;
  }

  ::-webkit-scrollbar-thumb:hover { /*滚动条的hover样式*/
    height: 50px;
    background-color: #9f9f9f;
    -webkit-border-radius: 4px;
  }

  /*.modal{*/
  /*    background-image: url("img/background.jpg");*/
  /*    z-index: 1;*/
  /*    background-color: black;*/
  /*    background-size: cover;*/
  /*    width: 100%;*/
  /*    height: 100%;*/
  /*    position: absolute;*/
  /*    !*opacity: 1.0;*!*/
  /*    !*-webkit-filter: blur(6px);*!*/
  /*    !*-moz-filter: blur(6px);*!*/
  /*    !*-ms-filter: blur(6px);*!*/
  /*    !*-o-filter: blur(6px);*!*/
  /*    !*filter: blur(6px);*!*/
  /*    overflow: hidden;*/
  /*    !*-webkit-mask-image: linear-gradient(to bottom,rgba(255,255,255,0) 0,rgba(255,255,255,.6) 15%,rgba(255,255,255,1) 25%,rgba(255,255,255,1) 75%,rgba(255,255,255,.6) 85%,rgba(255,255,255,0) 100%);*!*/
  /*}*/
  /*!*.modal:after{*!*/
  /*!*    background-image: url("img/background.jpg");*!*/
  /*!*    background-color: black;*!*/
  /*!*    background-attachment: fixed;*!*/
  /*!*    background-size: cover;*!*/
  /*!*    content: '';*!*/
  /*!*    position: absolute;*!*/
  /*!*    top: 0;*!*/
  /*!*    left: 0;*!*/
  /*!*    right: 0;*!*/
  /*!*    bottom: 0;*!*/
  /*!*    z-index: -1;*!*/
  /*!*    -webkit-filter: blur(2px);*!*/
  /*!*    -moz-filter: blur(2px);*!*/
  /*!*    -ms-filter: blur(2px);*!*/
  /*!*    -o-filter: blur(2px);*!*/
  /*!*    filter: blur(2px);*!*/
  /*!*    margin: -30px;*!*/
  /*}*/

  /*控制器综合*/
  .audioplayer {
    position: fixed; /* 生成绝对定位的元素，相对于浏览器窗口进行定位。 */
    bottom: 0;
    left: 0;
    width: 100%;
    height: 60px;
    white-space: nowrap; /* 规定段落中的文本不进行换行： */
    background-color: #384245;
    color: #737A7C;
    font-size: 14px;
    -webkit-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    user-select: none;
    z-index: 104; /* 第几层第几层，是显示谁在谁下面或上面 */
  }

  .audioplayer .ap-status {
    position: relative;
    margin: 0 auto;
    width: 1100px;
  }

  .audioplayer .ap-controller {
    display: flex;
    width: 100%;
    height: 100%;
    /*background-color: #384245;*/
    position: relative;
    z-index: 100;
  }

  .audioplayer .ap-button {
    cursor: pointer;
    transition: 0.2s;
  }

  .audioplayer .ap-controller .ap-controller-left > *,
  .audioplayer .ap-controller .ap-controller-right > * {
    display: inline-flex;
    align-items: center;
    justify-content: center;
    height: 100%;
    vertical-align: middle;
    z-index: 1;
  }

  svg:not(:root) {
    overflow: hidden;
  }

  .audioplayer svg:not(.ap-svg-exclude) {
    width: 28px;
    height: 28px;
    vertical-align: middle;
    transition: 0.2s;
  }

  /*.audioplayer .ap-progress-thumb svg {*/
  /*width: 21px;*/
  /*height: 19px;*/
  /*display: block;*/
  /*cursor: pointer;*/
  /*}*/

  .audioplayer .ap-progress-thumb {
    position: absolute;
    top: -4px;
    left: -6px;
    margin-left: -6px;
    cursor: pointer;
    z-index: 100;
  }

  .audioplayer .ap-progress-thumb .ap-progress-thumb-dot {
    width: 12px;
    height: 12px;
    border-radius: 50%;
    background: #32B0DA;
    /*-webkit-transform: scale(0);*/
    /*transform: scale(0);*/
    /*transition: 0.2s;*/
  }

  /*.audioplayer .ap-controller-center .ap-progress-line:hover .ap-progress-thumb-dot {*/
  /*transform: scale(1);*/
  /*}*/

  .audioplayer .ap-controller .ap-controller-center .ap-buffer-bar,
  .audioplayer .ap-controller .ap-controller-center .ap-progress-bar {
    position: absolute;
    -webkit-transform-origin: 0 0;
    transform-origin: 0 0;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    transition: transform 0.5s linear, -webkit-transform 0.5s linear;
  }

  /*left*/
  .audioplayer .ap-controller-left .ap-cover {
    width: 60px;
    height: 60px;
    background: #E7E7E7;
    -webkit-background-size: 100% 100%;
    background-size: 100% 100%;
    cursor: pointer;
  }

  .audioplayer .ap-controller-left .ap-button {
    padding: 0 8px;
  }

  .audioplayer .ap-controller-left .ap-skipback-button {
    margin-left: 16px;
  }

  .audioplayer .ap-controller-left .ap-skipforward-button {
    margin-right: 16px;
  }

  /*center*/
  .audioplayer .ap-controller-center {
    padding: 10px 0;
    display: flex;
    flex: 1;
    flex-direction: column;
    color: white;
  }

  .audioplayer .ap-controller-center .ap-song-msg {

  }

  .audioplayer .ap-controller-center .ap-song-msg {
    display: flex;
    flex: 1;
    align-items: center;
  }

  .audioplayer .ap-controller-center .ap-song-msg .ap-name {
    cursor: pointer;
    overflow: hidden;
    white-space: nowrap;
    -ms-text-overflow: ellipsis;
    text-overflow: ellipsis;
  }

  .audioplayer .ap-controller-center .ap-song-msg .ap-artist {
    font-size: 12px;
    margin-left: 6px;
    cursor: default;
    white-space: nowrap;
    -ms-text-overflow: ellipsis;
    text-overflow: ellipsis;
  }

  .audioplayer .ap-controller-center .ap-song-msg .ap-time {
    font-size: 12px;
    flex: 1;
    text-align: right;
    cursor: default;
    min-width: 110px;
  }

  .audioplayer .ap-controller-center .ap-song-msg .ap-time .ap-current-time {
    color: #fff;
  }

  .audioplayer .ap-controller-center .ap-progress-line {
    flex: 1;
    display: flex;
    align-items: center;
  }

  .audioplayer .ap-controller-center .ap-play-track {
    position: relative;
    width: 100%;
    height: 3px;
  }

  .audioplayer .ap-controller-center .ap-play-bar-wrap {
    position: absolute;
    top: 0;
    left: 0;
    bottom: 0;
    right: 0;
    border-radius: 1.5px;
    overflow: hidden;
    background: #909090;
    cursor: pointer;
  }

  .audioplayer .ap-controller-center .ap-buffer-bar {
    background: rgba(50, 176, 218, 0.47);
  }

  .audioplayer .ap-controller-center .ap-progress-bar {
    background: #32B0DA;;
  }

  .audioplayer .ap-controller .ap-controller-center .ap-progress-thumb {
    transition: -webkit-transform 0.5s linear;
    transition: transform 0.5s linear;
    transition: transform 0.5s linear, -webkit-transform 0.5s linear;
    position: absolute;
    top: -5px;
    left: -4px;
  }

  /*right*/
  .audioplayer .ap-controller-right {
    width: 360px;
    padding: 0 20px;
  }

  .audioplayer .ap-controller-right .ap-line {
    font-size: 24px;
    padding: 0 8px;
    cursor: default;
  }

  .audioplayer .ap-controller-right .ap-button {
    padding: 0 8px;
  }

  .audioplayer .ap-controller-right .ap-volume-line {
    width: 86px;
    margin-right: 8px;
    height: 28px;
  }

  .audioplayer .ap-controller-right .ap-line-draw {
    height: 24px;
    width: 1px;
    background: #979797;
  }

  .audioplayer .ap-controller-right .ap-volume-track {
    position: relative;
    width: 100%;
    height: 3px;
  }

  .audioplayer .ap-controller-right .ap-volume-bar-wrap {
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    border-radius: 1.5px;
    overflow: hidden;
    background: #909090;
  }

  .audioplayer .ap-controller-right .ap-volume-bar {
    position: absolute;
    -webkit-transform-origin: 0 0;
    transform-origin: 0 0;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    background: #32B0DA;
  }

  .audioplayer .ap-controller-right .ap-volume-thumb {
    position: absolute;
    top: -4px;
    left: -6px;
    margin-left: -6px;
    cursor: pointer;
  }

  .audioplayer .ap-controller-right .ap-volume-thumb-dot {
    width: 12px;
    height: 12px;
    border-radius: 50%;
    background: #32B0DA;
    -webkit-transform: scale(0);
    transform: scale(0);
    transition: 0.2s;
  }

  .audioplayer .ap-controller-right .ap-volume-line:hover .ap-volume-thumb-dot {
    transform: scale(1);
  }

  .audioplayer .ap-controller-right .ap-playlist-button-bg {
    /*background: #4A5355;*/
    height: 28px;
    border-radius: 14px;
    padding: 0 8px 0 6px;
  }

  .audioplayer .ap-controller-right .ap-playlist-button-amount {
    color: #fff;
    font-size: 12px;
    vertical-align: middle;
    transition: 0.2s;
  }

  .audioplayer .ap-controller-right i {
    font-size: 26px;
    color: black;
  }

  /*提示区*/
  .audioplayer .ap-toast {
    position: absolute;
    bottom: 72px;
    left: 50%;
    -webkit-transform: translate(-50%, 0);
    transform: translate(-50%, 0);
    padding: 10px 20px;
    background-color: rgba(0, 0, 0, 0.8);
    border-radius: 4px;
    color: #fff;
    opacity: 1;
    pointer-events: none;
    transition: 0.3s;
  }

  .audioplayer .ap-toast-hide {
    opacity: 0;
    -webkit-transform: translate(-50%, 12px);
    transform: translate(-50%, 12px);
  }
</style>
