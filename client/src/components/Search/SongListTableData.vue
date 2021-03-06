<template>
  <div class="newSongList">
    <ul class="clearFixed">
      <li v-for="(item,index) in songListTableData" :key="index" @click="goSongListInfo(item)">
        <img :src="attachImageUrl(item.pic)">
        <span>{{item.title}}</span>
      </li>
    </ul>
  </div>
</template>

<script>
  import mixins from '../../mixins/index';
  import {mapGetters} from 'vuex';

  export default {
    name: "SongListTableData",
    mixins: [mixins],
    data() {
      return {}
    },
    computed: {
      ...mapGetters([
        'songListTableData',     //监控缓存中的个单列表有变化
      ])
    },
    mounted() {
      if(this.$route.path === '/Search')
        this.getSongList()
      else
        this.getSongListByType()
    },
    watch: {
      $route() {
        if(this.$route.path === '/Search')
          this.getSongList()
        else
          this.getSongListByType()
      },
    },
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

  .newSongList ul li {
    width: 164px;
    height: 164px;
    float: left;
    margin: 20px 25px 40px;
    cursor: pointer;
    font-size: 12px;
  }

  .newSongList ul li img {
    display: block;
    width: 100%;
    object-fit: cover;
  }
</style>
