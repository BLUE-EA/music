<template>
  <div>
    <div class="border">
      <Header></Header>
      <Aside></Aside>
    </div>

    <div class="detail">
      <router-view></router-view>
    </div>

  </div>
</template>

<script>
  import Header from './Header';
  import Aside from './Aside';

  export default {
    name: "Home",
    components: {
      Header,
      Aside,
    }
  }
</script>

<style scoped>
  body {
    margin: 0;
    padding: 0;
  }

  .detail {
    position: absolute;
    left: 200px;
    top: 70px;
    right: 0;
    bottom: 0;
  }
</style>
