<template>
  <div class="newSongList">
    <ul class="clearFixed">
      <li v-for="(item,index) in songListTableData" :key="index" @click="goSingerInfo(item)">
        <img :src="attachImageUrl(item.pic)">
        <span>{{item.name}}</span>
      </li>
    </ul>
  </div>
</template>

<script>
  import mixins from '../mixins/index';
  import {getAllSinger} from '../api/index';

  export default {
    name: "Singer",
    mixins: [mixins],
    created(){
      getAllSinger().then(res => {
        this.songListTableData = res;
      }).catch(err => {
        console.log(err);
      })
    },
    data() {
      return {
        songListTableData: null,
      }
    },
    methods:{},
  }
</script>

<style scoped>

  li {
    list-style: none;
  }

  .clearFixed:after {
    content: "";
    display: block;
    clear: both;
  }

  .newSongList{
    width: 1080px;
    margin: 60px auto;
  }

  .newSongList ul li {
    width: 164px;
    height: 164px;
    float: left;
    margin: 20px 25px 40px;
    cursor: pointer;
    font-size: 14px;
    font-family: "Times New Roman";
  }

  .newSongList ul li img {
    display: block;
    width: 100%;
    height: 164px;
    object-fit: cover;
  }
</style>
