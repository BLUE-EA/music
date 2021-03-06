import {
  getSongOfId,
  getListSongOfSongId,
  getLikeSongOfName,
  getSongListOfLikeTitle,
  getSongListOfStyle
} from '../api/index'

const mixin = {
  methods: {
    attachImageUrl(srcUrl) {
      return srcUrl ? this.$store.state.configure.HOST + srcUrl || '../assets/img/user.jpg' : ''
    },
    attachSongUrl(srcUrl) {
      return this.$store.state.configure.HOST + srcUrl
    },
    // 搜索音乐
    getSong() {
      if (!this.$route.query.keywords) {
        this.$message('输入的内容为空');
      } else {
        if (this.$route.path !== '/SongListInfo') { //表示这是通过查名字来获取歌曲列表
          getLikeSongOfName(this.$route.query.keywords).then(res => {
            this.$store.commit('setSongTableData', res)
          }).catch(err => {
            console.log(err);
          })
        } else { //表示这个是通过歌单来查询歌曲
          getListSongOfSongId(this.$route.query.keywords).then(res => {
            this.$store.commit('setSongTableData', []);
            for (let item of res) {
              this.getSongById(item.songId)
            }
          }).catch(err => {
            console.log(err);
          })
        }
      }

    },
    getSongById(id) {
      getSongOfId(id)
        .then(res => {
          this.$store.commit('addSongTableData', res[0])
        })
        .catch(err => {
          console.log(err);
        })
    },

    //搜索歌单
    getSongList() {
      if (!this.$route.query.keywords) {
        this.$message('输入的内容为空');
      } else {
        getSongListOfLikeTitle(this.$route.query.keywords).then(res => {
          this.$store.commit('setSongListTableData', res)
        }).catch(err => {
          console.log(err);
        })

      }
    },

    getSongListByType() {
      if (!this.$route.query.keywords) {
        this.$message('输入的内容为空');
      } else {
        getSongListOfStyle(this.$route.query.keywords).then(res => {
          this.$store.commit('setSongListTableData', res)
        }).catch(err => {
          console.log(err);
        })

      }
    },

    getSongName(name) {
      let arr = name.split('-');
      return arr[1];
    },
    getSingerName(name) {
      let arr = name.split('-');
      return arr[0];
    },

    // 播放
    toPlay(item) {
      this.$store.commit('setId', item.id)
      this.$store.commit('setUrl', item.url)
      this.$store.commit('setPicUrl', this.$store.state.configure.HOST + item.pic)
      this.$store.commit('setTitle', this.getSongName(item.name))
      this.$store.commit('setArtist', this.getSingerName(item.name))
      this.$store.commit('setLyric', this.parseLyric(item.lyric))
      this.$store.commit('setIsPlay', true)
      this.$store.commit('addListOfSongs', item)
      this.$store.commit('setPlayingSong', item)
      let i = this.$store.state.song.listOfSongs.indexOf(item);
      this.$store.commit('setListIndex', i)
      // if (this.loginIn) {
      //   this.$store.commit('setIsActive', false)
      //   getCollectionOfUser(this.userId)
      //     .then(res => {
      //       for (let item of res) {
      //         if (item.songId === id) {
      //           this.$store.commit('setIsActive', true)
      //           break
      //         }
      //       }
      //     })
      //     .catch(err => {
      //       console.log(err)
      //     })
      // }

    },

    // 解析歌词
    parseLyric(text) {
      if (text !== '' && text!==null && text!==undefined) {
        let lines = text.split('\n')
        let pattern = /\[\d{2}:\d{2}.(\d{3}|\d{2})\]/g
        let result = []

        // 对于歌词格式不对的特殊处理
        if (!(/\[.+\]/.test(text))) {
          return [[0, text]]
        }

        while (!pattern.test(lines[0])) {
          lines = lines.slice(1)
        }

        lines[lines.length - 1].length === 0 && lines.pop()
        for (let item of lines) {
          let time = item.match(pattern) // 存前面的时间段
          let value = item.replace(pattern, '') // 存歌词
          for (let item1 of time) {
            let t = item1.slice(1, -1).split(':')
            if (value !== '') {
              result.push([parseInt(t[0], 10) * 60 + parseFloat(t[1]), value])
            }
          }
        }
        result.sort(function (a, b) {
          return a[0] - b[0]
        })
        return result
      }
    },



    goSingerInfo(singer) {
      this.$router.push({path: '/SingerInfo', query: {singerId: singer.id, keywords: singer.name}})
    },
    goSongListInfo(songList) {
      this.$router.push({path: '/SongListInfo', query: {songList: songList, keywords: songList.id}})
    },

    attachBirth(val) {
      let birth = String(val).match(/[0-9-]+(?=\s)/)
      return Array.isArray(birth) ? birth[0] : birth
    },

    changeSex(value) {
      if (value === 0) {
        return '女'
      } else if (value === 1) {
        return '男'
      } else if (value === 2) {
        return '组合'
      } else if (value === 3) {
        return '不明'
      } else if (value === '男' || value === '女') {
        return value
      }
    },
  }
}

export default mixin
