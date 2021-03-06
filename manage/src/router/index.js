import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      component: resolve => require(['../components/Login.vue'], resolve)
    },
    {
      path: '/Home',
      component: resolve => require(['../components/Home.vue'], resolve),
      children: [
        {
          path: '/Info',
          component: resolve => require(['../components/Info.vue'], resolve),
        },
        {
          path: '/songList',
          component: resolve => require(['../components/SongList.vue'], resolve),
        },
        {
          path: '/singer',
          component: resolve => require(['../components/Singer.vue'], resolve),
        },
        {
          path: '/song',
          component: resolve => require(['../components/Song.vue'], resolve),
        },
        {
          path: '/listSong',
          component: resolve => require(['../components/ListSong.vue'], resolve),
        },
        {
          path: '/Comment',
          component: resolve => require(['../components/Comment.vue'], resolve),
        },
        {
          path: '/consumer',
          component: resolve => require(['../components/Consumer.vue'], resolve),
        },
        {
          path: '/collect',
          component: resolve => require(['../components/Collect.vue'], resolve),
        },
      ]
    }
  ]
})
