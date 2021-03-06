<template>
  <div id="app">
    <Header/>
    <router-view/>
    <SongAudio/>
    <PlayBar/>
    <div class="footer"></div>
  </div>
</template>

<script>
  import Header from './components/Header';
  import SongAudio from './components/SongAudio';
  import PlayBar from './components/PlayBar';

  export default {
    name: 'App',
    components: {
      Header,
      SongAudio,
      PlayBar,
    },
    created() {
      window.addEventListener('onload', e => this.onloadFn(e))
    },
    destroyed() {
      window.removeEventListener('beforeunload', e => this.beforeunloadFn(e))
    },
    methods: {
      beforeunloadFn(e) {
        // console.log('刷新或关闭')
        //将store的数据全部扔进cookie
      },
      onloadFn(e){

      },
    },
  }
</script>

<style>
  * {
    margin: 0;
    padding: 0;
  }

  p, h1, h2, h3, h4, h5, h6, dl, dt, dd, ol, ul, li {
    margin: 0px;
    padding: 0px;
  }

  body {
    background-color: #f5f5f5;
  }

  .footer {
    height: 60px;
  }
</style>
