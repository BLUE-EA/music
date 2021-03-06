<template>
  <div>
    <div class="type">
      <div class="typeChoose">
        <ul class="clearFixed">
          <li><span>类型:</span></li>
          <li v-for="item in label">
            <a :class="{active: activeLabel===item.name}" @click="getTypeOfSongList(item)">{{item.name}}</a>
          </li>
        </ul>
      </div>
    </div>
    <div class="songList">
      <SongListTableData/>
    </div>
  </div>
</template>

<script>
  import SongListTableData from '../components/Search/SongListTableData';
  import mixins from '../mixins/index';

  export default {
    name: "SongList",
    mixins: [mixins],
    data() {
      return {
        label:[
          {name:'欧美', path:'/songList'},
          {name:'轻音乐', path:'/songList'},
          {name:'华语', path:'/songList'},
          {name:'日韩', path:'/songList'},
          {name:'粤语', path:'/songList'},
          {name:'纯音乐', path:'/songList'},
        ],
        activeLabel: '欧美',
      }
    },
    watch:{
      $router(){
        this.activeLabel = this.$route.query.keywords;
      },
    },
    components: {
      SongListTableData,
    },
    methods:{
      getTypeOfSongList(item){
        this.activeLabel = item.name
        this.$router.push({path: item.path, query: {keywords: item.name}})
      },
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

  .clearFixed:after {
    content: "";
    display: block;
    clear: both;
  }

  .type {
    width: 1150px;
    height: 80px;
    margin: 106px auto;
  }

  .type .typeTitle {
    font-size: 22px;
  }

  .type .typeChoose {
    width: 1150px;
    height: 90px;
    background-color: white;
    margin-top: 32px;
    padding-top: 8px;
    padding-left: 46px;
  }

  .type .typeChoose ul {
    margin-top: 28px;
  }

  .type .typeChoose ul li .active {
    color: #b8860b;
  }

  .type .typeChoose ul li {
    float: left;
  }

  .type .typeChoose ul li a {
    color: black;
    font-size: 14px;
    margin-left: 50px;
    cursor: pointer;
  }

  .songList{
    width: 1080px;
    margin: 0 auto;
  }
</style>
