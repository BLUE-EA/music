import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/pages/Home'
import Singer from '@/pages/Singer'
import SongList from '@/pages/SongList'
import MyMusic from '@/pages/MyMusic'
import Search from '@/pages/Search'
import SongInfo from '@/pages/SongInfo'
import SingerInfo from '@/pages/SingerInfo'
import SongListInfo from '@/pages/SongListInfo'
import Lyric from '@/pages/Lyric'

Vue.use(Router)


const originalPush = Router.prototype.push
Router.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}

const originalReplace = Router.prototype.replace
Router.prototype.replace = function replace(location) {
  return originalReplace.call(this, location).catch(err => err);
};

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home
    },
    {
      path: '/Singer',
      name: 'Singer',
      component: Singer
    },
    {
      path: '/SongList',
      name: 'SongList',
      component: SongList
    },
    {
      path: '/MyMusic',
      name: 'MyMusic',
      component: MyMusic
    },
    {
      path: '/Search',
      name: 'Search',
      component: Search
    },
    {
      path: '/SongInfo',
      name: 'SongInfo',
      component: SongInfo
    },
    {
      path: '/SingerInfo',
      name: 'SingerInfo',
      component: SingerInfo
    },
    {
      path: '/SongListInfo',
      name: 'SongListInfo',
      component: SongListInfo
    },
    {
      path: '/Lyric',
      name: 'Lyric',
      component: Lyric
    },
  ]
})
