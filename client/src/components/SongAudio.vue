<template>
  <div class="SongAudio">
    <audio id="player" ref="player" controls="controls" preload="true" :src="attachSongUrl(url)" @canplay="startPlay" @ended="ended" @timeupdate="timeupdate"></audio>
  </div>
</template>

<script>
import { mapGetters } from 'vuex';
import mixins from '../mixins/index';

export default {
  name: 'SongAudio',
  mixins: [mixins],
  created(){
    // this.$refs.player.volume = this.volume / 100;
  },
  computed: {
    ...mapGetters([
      'id', // 音乐id
      'url', // 音乐链接
      'listOfSongs', // 存放的音乐
      'isPlay', // 播放状态
      'volume', // 音量
      'curTime', // 当前音乐的播放位置
      'changeTime', // 指定播放时刻
      'autoNext' // 用于触发自动播放下一首
    ])
  },
  destroyed() {
    window.removeEventListener('beforeunload', e => this.beforeunloadFn(e))
  },
  watch: {
    // 监听播放还是暂停
    isPlay() {
      this.togglePlay()
    },
    // 跳到指定时刻播放
    changeTime () {
      let player = this.$refs.player;
      player.currentTime = this.changeTime;
    },
    //监控音量变化
    volume () {
      this.$refs.player.volume = this.volume / 100;
    }
  },
  methods: {
    // 开始/暂停
    togglePlay () {
      let player = this.$refs.player;
      if (this.isPlay) {
        player.play()
      } else {
        player.pause()
      }
    },
    beforeunloadFn(e){
      console.log('重载audio');
      this.$refs.player.load();
    },
    // 获取歌曲链接后准备播放
    startPlay () {
      let player = this.$refs.player;
      this.$store.commit('setDuration', player.duration);
      //  开始播放
      player.play();
      this.$store.commit('setIsPlay', true);
    },
    // 音乐播放时记录音乐的播放位置
    timeupdate () {
      let player = this.$refs.player
      this.$store.commit('setCurTime', player.currentTime)
    },
    // 音乐播放结束时触发
    ended () {
      this.$store.commit('setIsPlay', false);
      this.$store.commit('setCurTime', 0);
      this.$store.commit('setAutoNext', !this.autoNext);
    }
  }
}
</script>

<style>
#player {
  display: none;
}
</style>
