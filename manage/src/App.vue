<template>
  <div id="app">
    <SongAudio />
    <router-view/>
  </div>
</template>

<script>
  import SongAudio from './components/SongAudio'
  // import 'assets/css/main.css'
export default {
  name: 'App',
  components: {
    SongAudio
  }
}
</script>

<style>
  body {
    margin: 0;
    padding: 0;
  }
</style>
