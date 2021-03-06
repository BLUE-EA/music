<template>
  <div class="searchPage">
    <div class="nav">
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="歌曲" name="song"></el-tab-pane>
        <el-tab-pane label="歌单" name="songList"></el-tab-pane>
      </el-tabs>
    </div>
    <component :is="currentView"></component>
  </div>
</template>

<script>
  import songListTableData from '../components/Search/SongListTableData'
  import songTableData from '../components/Search/SongTableData'
  import mixin from '../mixins'

  export default {
    name: "Search",
    mixins:[mixin],
    data() {
      return {
        activeName: 'song',
        currentView: 'songTableData'
      }
    },
    components: {
      songTableData,
      songListTableData,
    },
    methods: {
      handleClick(tab, event) {
        this.activeName = tab.name;
        if (this.activeName === 'song') {
          this.currentView = songTableData;
        }else{
          this.currentView = songListTableData;
        }
      },

    },

  }
</script>

<style scoped>
  .searchPage {
    margin: 70px auto 60px;
    width: 1080px;
  }

  .nav {
    margin-top: 100px;
    margin-bottom: 20px;
    margin-left: 60px;
  }

  .searchPage .display {
    display: none;
  }
</style>
