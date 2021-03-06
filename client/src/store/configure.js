const configure = {
  state: {
    HOST: 'http://localhost:8888',
    activeName: '首页',         //当前选中菜单名
    keywords: '',
    loginIn: false,      //当前是否登录
    userObject: {
      id: '',
      avatar: '',
    },
    nickName: '',
  },
  getters: {
    userObject: state => {
      let userObject = state.userObject
      if (!userObject) {
        // userObject = JSON.parse(window.sessionStorage.getItem('userObject') || null)
      }
      return userObject
    },
    keywords: state => state.keywords,
    activeName: state => {
      let activeName = state.activeName
      if (!activeName) {
        activeName = JSON.parse(window.sessionStorage.getItem('activeName') || null)
      }
      return activeName
    },
    searchword: state => state.searchword,
    loginIn: state => {
      let loginIn = state.loginIn
      if (!loginIn) {
        // loginIn = JSON.parse(window.sessionStorage.getItem('loginIn') || null)
      }
      return loginIn
    },
  },
  mutations: {
    setUserObject: (state, userObject) => {
      state.userObject = userObject
      // window.userObject.setItem('userObject', JSON.stringify(userObject))
    },

    setActiveName: (state, activeName) => {
      state.activeName = activeName
    },

    setLoginIn: (state, loginIn) => {
      state.loginIn = loginIn
      window.sessionStorage.setItem('loginIn', JSON.stringify(loginIn))
    },

    setKeywords: (state, keywords) => {
      state.keywords = keywords
    }
  }
}

export default configure
