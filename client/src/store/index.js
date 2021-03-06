import Vue from 'vue'
import Vuex from 'vuex'
import configure from './configure'
import user from './user'
import song from './song'

Vue.use(Vuex)

// const store =

export default new Vuex.Store({
  modules:{
    user,
    configure,
    song,
  }
})
