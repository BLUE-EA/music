<template>
  <div>
    <div class="detail main">
      <div class="picture fl"><img :src=attachImageUrl(userObject.avatar) height="350" width="350" alt=""></div>
      <div class="info fr">
        <ul>
          <li><span>{{userObject.username}}</span></li>
          <li><span>性别：{{changeSex(userObject.sex)}}</span></li>
          <li><span>生日：{{attachBirth(userObject.birth)}}</span></li>
          <li><span>家乡：{{userObject.location}}</span></li>
          <li><span>简介：{{userObject.introduction}}</span></li>
        </ul>
      </div>
    </div>
    <div class="songTable">
      <div class="listSong">
        <table class="songTable">
          <tr class="tableHead">
            <td class="gq">歌曲</td>
            <td>歌手</td>
            <td>专辑</td>
          </tr>
          <tr v-for="(item, index) in userCollect" :class="{playSong: item.id===id}" :key="index">
            <td class="clearFixed">
              <span style="cursor: pointer" @click="toSongInfo(item.id)">{{getSongName(item.name)}}</span>
              <a style="cursor: pointer;">
                <i :class="{playSong: item.id===id}" class="el-icon-download fr" @click="download(item)"></i>
              </a>
              <span style="cursor: pointer;">
            <i :class="{playSong: item.id===id}" v-if="id === item.id && isPlay" @click="Pause()"
               class="el-icon-video-pause fr"></i>
            <i :class="{playSong: item.id===id}" v-else @click="toPlay(item)" class="el-icon-video-play fr"></i>
          </span>
            </td>
            <td>{{getSingerName(item.name)}}</td>
            <td>{{item.introduction}}</td>
          </tr>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
  import mixins from '../mixins/index';
  import SongTableDate from '../components/Search/SongTableData';
  import {mapGetters} from 'vuex';
  import {download} from "../api";

  export default {
    name: "MyMusic",
    mixins: [mixins],
    components: {
      SongTableDate,
    },
    computed: {
      ...mapGetters([
        'userObject',
        'userCollect',

        'songTableData',
        'isPlay',
        'id',
        'listIndex',
      ])
    },
    data() {
      return {}
    },
    methods: {
      Pause() {
        this.$store.commit('setIsPlay', false)
      },
      toSongInfo(id) {
        this.$router.push({path: '/SongInfo', query: {id: id}})
      },
      // 下载
      download(item) {
        download(item.url)
          .then(res => {
            console.log(item.url);
            let content = res.data
            let eleLink = document.createElement('a')
            eleLink.download = `${this.getSongName(item.name)}.mp3`
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
    }
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
    margin: 60px auto 0;
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
    /*size: 350px 350px;*/
    display: block;
    width: 100%;
    height: 350px;
    object-fit: cover;
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

  .detail .info ul li a {
    color: black;
  }

  .detail .info ul li a:hover {
    color: pink;
  }

  .detail .info .operation {
    margin-top: 50px;
  }

  .songTable {
    width: 1080px;
    margin: 0 auto;
  }

  .fr {
    float: right;
  }

  .listSong {
    margin-left: 50px;
    margin-right: 50px;
    /*background-color: #807c79;*/
  }

  .listSong table .tableHead td {
    font-weight: 900;
    font-size: 20px;
    height: 50px;
    line-height: 50px;
    width: 500px;
    border-bottom: 1px #ddd solid;
    padding: 8px;
  }

  .listSong table .tableHead .gq {
    width: 1200px;
  }

  .listSong table td {
    font-size: 16px;
    height: 60px;
    line-height: 60px;
    border-bottom: 1px #ddd solid;
  }

  .listSong table .playSong {
    color: orangered;
  }

  .listSong table td i {
    color: #353535;
    font-size: 25px;
    line-height: 60px;
    margin-right: 30px;
  }

  .listSong table td span:hover {
    color: #909090;
  }
</style>
