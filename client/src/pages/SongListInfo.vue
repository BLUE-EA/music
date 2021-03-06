<template>
  <div>
    <div class="detail main">
      <div class="picture fl"><img :src=attachImageUrl(songList.pic) height="350" width="350" alt=""></div>
      <div class="info fr">
        <ul>
          <li><span>{{songList.title}}</span></li>
          <li><span>简介：{{songList.introduction}}</span></li>
        </ul>
      </div>
    </div>
    <div class="songTable">
      <SongTableDate/>
    </div>
    <Comment/>
  </div>
</template>

<script>
  import mixins from '../mixins/index';
  import SongTableDate from '../components/Search/SongTableData';
  import Comment from '../components/Comment'

  export default {
    name: "SongListInfo",
    mixins: [mixins],
    created() {
      this.songList = this.$route.query.songList;
    },
    data() {
      return {
        songListInfo: null,
      }
    },
    components: {
      SongTableDate,
      Comment,
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
</style>
