<template>
  <div class="home">
    <swiper class="swiper"/>
    <div class="newSongList clearFixed">
      <div class="newSongListTitle fl">歌手推荐</div>
      <a style="cursor: pointer;" @click="goPage('/Singer', '歌手')" class="more fr">更多&nbsp;&gt;</a>
      <div class="songList fl">
        <ul class="clearFixed">
          <li v-for="(item,index) in singerList" :key="index" @click="goSingerInfo(item)">
            <img :src="attachImageUrl(item.pic)" alt="">
          </li>
        </ul>
      </div>
    </div>
    <div class="newSongList clearFixed">
      <div class="newSongListTitle fl">歌单推荐</div>
      <a style="cursor: pointer;" @click="goPage('/songList?keywords=欧美', '歌单')" class="more fr">更多&nbsp;&gt;</a>
      <div class="songList fl">
        <ul class="clearFixed">
          <li v-for="(item,index) in songList" :key="index" @click="goSongListInfo(item)">
            <img :src="attachImageUrl(item.pic)">
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
  import swiper from '../components/Swiper'
  import {getSongList, getAllSinger} from '../api/index'
  import mixins from '../mixins/index'

  export default {
    name: "Home",
    mixins: [mixins],
    components: {
      swiper,
    },
    data() {
      return {
        songList: [], // 歌单列表
        singerList: [] // 歌手列表
      }
    },
    created() {
      this.getSongList();
      this.getSingerList()
      console.log(this.songList);
    },
    methods: {
      goPage(path, name) {
        this.$store.commit('setActiveName', name)
        this.$router.push({path: path})
      },
      getSongList() {
        getSongList().then(res => {
          this.songList = res.sort().slice(0, 10)
        })
          .catch(err => {
            console.log(err)
          })
      },
      getSingerList() {
        getAllSinger().then(res => {
          this.singerList = res.sort().slice(0, 10)
        })
          .catch(err => {
            console.log(err)
          })
      },
      attachImageUrl(srcUrl) {
        return srcUrl ? this.$store.state.configure.HOST + srcUrl || '../assets/img/user.jpg' : ''
      },
    }
  }
</script>

<style scoped>
  body {
    background-color: #e6e6e6;
  }

  * {
    margin: 0;
    padding: 0;
  }

  p, h1, h2, h3, h4, h5, h6, dl, dt, dd, ol, ul, li {
    margin: 0px;
    padding: 0px;
  }

  .fl {
    float: left;
  }

  .fr {
    float: right;
  }

  li {
    list-style: none;
  }

  a {
    text-decoration: none;
  }

  a:hover {
    text-decoration: none
  }

  .clearFixed:after {
    content: "";
    display: block;
    clear: both;
  }

  input {
    border: none
  }

  .swiper {
    margin-top: 70px;
  }

  .newSongList {
    margin: 50px auto;
    width: 1080px;
    height: 420px;
  }

  .newSongList .newSongListTitle {
    font-size: 30px;
    font-weight: 900;

  }

  .newSongList .more {
    font-family: "微软雅黑 Light";
    color: #999;
    font-size: 14px;
    line-height: 32px;
  }

  .newSongList ul li {
    width: 164px;
    height: 164px;
    float: left;
    margin-top: 40px;
    margin-left: 25px;
    margin-right: 25px;
    cursor: pointer;
  }

  .newSongList ul li img {
    display: block;
    width:100%;
    height: 164px;
    object-fit: cover;
  }
</style>
