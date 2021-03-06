import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)

// const store =

export default new Vuex.Store({
  state: {
    HOST: 'http://localhost:8888',
    isPlay: false,     //播放状态
    url: '',           //播放地址
    id: ''             //播放歌曲ID
  },
  getters: {
    isPlay: state => state.isPlay,
    url: state => state.url,
    id: state => state.id
  },
  mutations: {
    setIsPlay: (state, isPlay) => { state.isPlay = isPlay },
    setUrl: (state, url) => { state.url = url },
    setId: (state, id) => { state.id = id }
  }
})
