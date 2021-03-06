<template>
  <div class="bg">
    <!--  歌词滚动  -->
    <div class="lyricBox">
      <div class="lyric-inner" :style="{transform: 'translateY('+LrcTranslateY+'px)'}">
        <p v-for="(lrcInfo, index) in songLrc" :line-id="index" :class="{on: index === activeIndex}">
          {{lrcInfo.content}}</p>
      </div>
    </div>
    <div class="modal" ref="modal" :style="{backgroundImage:'url('+picUrl+')'}"></div>
  </div>

</template>

<script>
  import {mapGetters} from 'vuex';

  export default {
    name: "Lyric",
    computed: {
      ...mapGetters([
        'curTime',
        'lyric',
        'picUrl',
      ])
    },
    created() {
      this.songLrc = this.list2Json(this.lyric);
      this.LrcInit();
      console.log(this.lyric);
    },
    data() {
      return {
        activeIndex: 0,  //歌词高亮的index
        LrcTranslateY: 0,
        songLrc: [],
      }
    },
    watch: {
      curTime() {
        this.LrcInit();
      },
      lyric() {
        this.songLrc = this.list2Json(this.lyric);
        this.LrcInit();
      },
    },
    methods: {
      list2Json(list) {
        let res = [];
        list.forEach(item => {
          res.push({
            ms: item[0] * 1000,
            content: item[1],
          })
        });
        return res;
      },
      LrcInit() {
        let timeStamp = this.curTime * 1000;   //获取当前的时间戳，并且转换为ms单位
        this.activeIndex = this.songLrc.findIndex((item, index) => {  //做判断是否更换高亮对象
          return item.ms < timeStamp && this.songLrc[index + 1] ? this.songLrc[index + 1].ms > timeStamp : true
        })
        this.LrcTranslateY = -79 * this.activeIndex;
      },
    },
  }
</script>

<style scoped>
  .bg {
    height: 100%;
    width: 100%;
    position: absolute;
    top: 0;
    background-color: rgb(41, 42, 43);
  }

  .lyricBox {
    position: absolute;
    color: white;
    text-align: center;
    font-size: 22px;
    line-height: 58px;
    width: 100%;
    top: 10%;
    bottom: 15%;
    /*background: black;*/
    overflow: hidden;
    z-index: +1;
    text-align: center;
    -webkit-mask-image: linear-gradient(to bottom, rgba(255, 255, 255, 0) 0, rgba(255, 255, 255, .6) 15%, rgba(255, 255, 255, 1) 25%, rgba(255, 255, 255, 1) 75%, rgba(255, 255, 255, .6) 85%, rgba(255, 255, 255, 0) 100%);
  }

  .lyricBox .lyric-inner {
    margin-top: 270px;
    transition: -webkit-transform 0.1s ease-out 0s;
  }

  .lyricBox .lyric-inner .on {
    color: #32B0DA;
  }

  .lyricBox p {
    display: block;
    margin-block-start: 1em;
    margin-block-end: 1em;
    margin-inline-start: 0px;
    margin-inline-end: 0px;
  }

  .modal {
    /*background-image: url("https://y.gtimg.cn/music/photo_new/T002R300x300M000004IJsk31VDoT8_1.jpg?max_age=2592000");*/
    display: block;
    background-repeat: no-repeat;
    background-size: cover;
    background-position: 50%;
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    opacity: .6;
    filter: blur(50px);
  }
</style>
