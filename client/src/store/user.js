const user = {
  state: {
    userCollect: [],
  },
  getters: {
    userCollect: state => {
      let userCollect = state.userCollect
      if (!userCollect) {
        userCollect = JSON.parse(window.sessionStorage.getItem('userCollect') || null)
      }
      return userCollect
    },
  },
  mutations: {
    setUserCollect: (state, userCollect) => {
      state.userCollect = userCollect
      window.userCollect.setItem('userCollect', JSON.stringify(userCollect))
    },
    addUserCollect: (state, opSong) => {
      state.userCollect.push(opSong)
    },
    deleteUserCollect: (state, opSong) => {
      console.log('开始删除'+state.userCollect.indexOf(opSong));
      if(state.userCollect.indexOf(opSong)!==-1)
        delete state.userCollect.splice(state.userCollect.indexOf(opSong),1)
    },
  },
}

export default user
