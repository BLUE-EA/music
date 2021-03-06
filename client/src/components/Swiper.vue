<template>
  <div class="swiper">
    <div class="block">
      <el-carousel height="380px" type="card">
        <el-carousel-item v-for="(item, index) in swiperList" :key="index">
          <img style="width: 100%" :src="item.picImg"/>
        </el-carousel-item>
      </el-carousel>
    </div>

  </div>
</template>

<script>
  export default {
    name: "Swiper",
    data() {
      return {
        swiperList: [
          {
            picImg: require('@/assets/img/swiper/img1.jpg')
          }, {
            picImg: require('@/assets/img/swiper/img2.jpg')
          }, {
            picImg: require('@/assets/img/swiper/img3.jpg')
          }, {
            picImg: require('@/assets/img/swiper/img4.jpg')
          }, {
            picImg: require('@/assets/img/swiper/img5.jpg')
          }, {
            picImg: require('@/assets/img/swiper/img6.jpg')
          }, {
            picImg: require('@/assets/img/swiper/img7.jpg')
          },
        ],
      }
    }
  }
</script>

<style scoped>
  .block {
    width: 69%;
    margin: 0 auto 0;
  }

  .el-carousel__item img {
    margin: 70px auto;
  }
</style>
