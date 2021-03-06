const song = {
  state: {
    songTableData: [],      //搜索到的歌曲列表结果
    songListTableData: [],  //搜索到的歌单列表
    singerTableData: [],   //搜到的歌手
    isPlay: false,     //播放状态
    url: '',           //播放地址
    id: null,          //播放歌曲ID
    picUrl: '',        //播放歌曲的图片的url
    lyric: [],         //歌曲的歌词
    listIndex: null,   //播放歌曲在当前歌单所在的位置
    listOfSongs: [],   // 当前歌单列表
    title: '',         //  歌名
    artist: '',        //  歌手名
    duration: 0,       //  音乐时长
    curTime: 0,        // 当前音乐的播放位置
    changeTime: 0,     //  指定播放时刻
    autoNext: true,    // 用于触发自动播放下一首
    volume: 100,       //音量默认值
    playingSong: null, //正在播放的歌曲的全部信息

  },
  getters: {
    songTableData: state => {
      let songTableData = state.songTableData
      if (!songTableData) {
        songTableData = JSON.parse(window.sessionStorage.getItem('songTableData') || null)
      }
      return songTableData
    },
    songListTableData: state => state.songListTableData,
    singerTableData: state => state.singerTableData,
    isPlay: state => state.isPlay,
    playingSong: state => state.playingSong,
    id: state => {
      let id = state.id
      if (!id) {
        // id = JSON.parse(window.sessionStorage.getItem('id') || null)
      }
      return id
    },
    url: state => {
      let url = state.url
      if (!url) {
        // url = JSON.parse(window.sessionStorage.getItem('url') || null)
      }
      return url
    },
    duration: state => {
      let duration = state.duration
      if (!duration) {
        // duration = JSON.parse(window.sessionStorage.getItem('duration') || 0)
      }
      return duration
    },
    curTime: state => {
      let curTime = state.curTime
      if (!curTime) {
        curTime = JSON.parse(window.sessionStorage.getItem('curTime') || 0)
      }
      return curTime
    },
    changeTime: state => {
      let changeTime = state.changeTime
      if (!changeTime) {
        // changeTime = JSON.parse(window.sessionStorage.getItem('changeTime') || 0)
      }
      return changeTime
    },
    title: state => {
      let title = state.title
      if (!title) {
        // title = JSON.parse(window.sessionStorage.getItem('title') || null)
      }
      return title
    },
    artist: state => {
      let artist = state.artist
      if (!artist) {
        // artist = JSON.parse(window.sessionStorage.getItem('artist') || null)
      }
      return artist
    },
    picUrl: state => {
      let picUrl = state.picUrl
      if (!picUrl) {
        // picUrl = JSON.parse(window.sessionStorage.getItem('picUrl')) || 'http://localhost:8888/img/tubiao.jpg'
      }
      return picUrl
    },
    autoNext: state => {
      let autoNext = state.autoNext
      if (!autoNext) {
        autoNext = JSON.parse(window.sessionStorage.getItem('autoNext') || null)
      }
      return autoNext
    },
    lyric: state => {
      let lyric = state.lyric
      if (lyric.length === 0) {
        // lyric = JSON.parse(window.sessionStorage.getItem('lyric') || null)
      }
      return lyric
    },
    tempList: state => {
      let tempList = state.tempList
      if (JSON.stringify(tempList) === '{}') {
        tempList = JSON.parse(window.sessionStorage.getItem('tempList') || null)
      }
      return tempList
    },
    listOfSongs: state => {
      let listOfSongs = state.listOfSongs
      if (!listOfSongs.length) {
        // listOfSongs = JSON.parse(window.sessionStorage.getItem('listOfSongs') || null)
      }
      return listOfSongs
    },
    listIndex: state => {
      let listIndex = state.listIndex
      if (!listIndex) {
        // listIndex = JSON.parse(window.sessionStorage.getItem('listIndex') || null)
      }
      return listIndex
    },
    volume: state => {
      let volume = state.volume
      if (!volume) {
        volume = JSON.parse(window.sessionStorage.getItem('volume') || null)
      }
      return volume
    }
  },
  mutations: {
    setSongTableData: (state, songTableData) => {
      state.songTableData = songTableData
      window.sessionStorage.setItem('songTableData', JSON.stringify(songTableData))
    },
    addSongTableData: (state, song) => {
      state.songTableData.push(song)
    },
    setSongListTableData: (state, songListTableData) => {
      state.songListTableData = songListTableData
      window.sessionStorage.setItem('songListTableData', JSON.stringify(songListTableData))
    },
    setSingerTableData: (state, singerTableData) => {
      state.singerTableData = singerTableData
      window.sessionStorage.setItem('singerTableData', JSON.stringify(singerTableData))
    },
    setIsPlay: (state, isPlay) => {
      state.isPlay = isPlay
      window.sessionStorage.setItem('isPlay', JSON.stringify(isPlay))
    },
    setId: (state, id) => {
      state.id = id
      window.sessionStorage.setItem('id', JSON.stringify(id))
    },
    setUrl: (state, url) => {
      state.url = url
      window.sessionStorage.setItem('url', JSON.stringify(url))
    },
    setDuration: (state, duration) => {
      state.duration = duration
      window.sessionStorage.setItem('duration', JSON.stringify(duration))
    },
    setCurTime: (state, curTime) => {
      state.curTime = curTime
      window.sessionStorage.setItem('curTime', JSON.stringify(curTime))
    },
    setChangeTime: (state, changeTime) => {
      state.changeTime = changeTime
      window.sessionStorage.setItem('changeTime', JSON.stringify(changeTime))
    },
    setTitle: (state, title) => {
      state.title = title
      window.sessionStorage.setItem('title', JSON.stringify(title))
    },
    setArtist: (state, artist) => {
      state.artist = artist
      window.sessionStorage.setItem('artist', JSON.stringify(artist))
    },
    setPicUrl: (state, picUrl) => {
      state.picUrl = picUrl
      window.sessionStorage.setItem('picUrl', JSON.stringify(picUrl))
    },
    setAutoNext: (state, autoNext) => {
      state.autoNext = autoNext
      window.sessionStorage.setItem('autoNext', JSON.stringify(autoNext))
    },
    setLyric: (state, lyric) => {
      state.lyric = lyric
      window.sessionStorage.setItem('lyric', JSON.stringify(lyric))
    },
    setTempList: (state, tempList) => {
      state.tempList = tempList
      window.sessionStorage.setItem('tempList', JSON.stringify(tempList))
    },
    setListOfSongs: (state, listOfSongs) => {
      state.listOfSongs = listOfSongs
      window.sessionStorage.setItem('listOfSongs', JSON.stringify(listOfSongs))
    },
    addListOfSongs: (state, song) => {
      if (state.listOfSongs.indexOf(song) === -1) {
        state.listOfSongs.push(song);
        window.sessionStorage.setItem('listOfSongs', JSON.stringify(state.listOfSongs))
      }
    },
    setListIndex: (state, listIndex) => {
      state.listIndex = listIndex
      window.sessionStorage.setItem('listIndex', JSON.stringify(listIndex))
    },
    setVolume: (state, volume) => {
      state.volume = volume
      window.sessionStorage.setItem('volume', JSON.stringify(volume))
    },
    setPlayingSong: (state, playingSong) => {
      state.playingSong = playingSong
    },
  }
}

export default song
