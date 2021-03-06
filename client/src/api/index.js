/* eslint-disable*/
import axios from 'axios'
import {get, post} from './http'

// =======================> 用户 API
// 登录
export const loginIn = (params) => post(`user/login/status`, params)
// 注册
export const SignUp = (params) => post(`user/add`, params)
// 更新用户信息
export const updateUserMsg = (params) => post(`user/update`, params)
//用户更新信息，除了密码
export const updateUserInfo = (params) => post(`user/updateInfo`, params)
// 用户更新密码
export const updateUserPassword = (params) => post(`user/updatePassword`,params)
// 返回指定ID的用户
export const getUserOfId = (id) => get(`user/detail?id=${id}`)
// 修改密码
export const updatePassword = (params) => post(``, params)

// =======================> 歌单 API
// 获取全部歌单
export const getSongList = () => get('songList')
// 获取歌单类型
export const getSongListOfStyle = (style) => get(`songList/style/detail?style=${style}`)
// 返回标题包含文字的歌单
export const getSongListOfLikeTitle = (keywords) => get(`songList/likeTitle/detail?title=${keywords}`)
// 返回歌单里指定歌单ID的歌曲
export const getListSongOfSongId = (songListId) => get(`listSong/detail?songListId=${songListId}`)


// =======================> 歌手 API
// 返回所有歌手
export const getAllSinger = () => get(`singer`)
//返回单个歌手信息
export const getSingerById = (id) => get(`singer/detail?singerId=${id}`)
// 通过性别对歌手分类
export const getSingerOfSex = (sex) => get(`singer/sex/detail?sex=${sex}`)


// =======================> 收藏 API
// 返回的指定用户ID的收藏列表
export const getCollectionOfUser = (userId) => get(`collection/detail?userId=${userId}`)
// 添加收藏的歌曲 type: 0 代表歌曲， 1 代表歌单
export const setCollection = (params) => post(`collection/add`, params)
//删除收藏的歌曲
export const deleteCollect = (params) => get(`collection/delete`, params)


// =======================> 评论 API
// 添加评论
export const setComment = (params) => post(`comment/add`, params)
//添加子评论，即回复内容
export const setChildComment = (params) => post(`comment/addChild`, params)
// 点赞
export const setLike = (params) => post(`comment/like`, params)
// 返回所有评论
export const getAllComment = (type, id) => {
  let url = ''
  if (type === 1) {
    url = `comment/songList/detail?songListId=${id}`
  } else if (type === 0) {
    url = `comment/song/detail?songId=${id}`
  }
  return get(url)
}
// 删除评论
export const deleteComment = (id) => get(`comment/delete?id=${id}`)
//删除子评论
export const deleteChildComment = (id) => get(`comment/childDelete?id=${id}`)


// =======================> 歌曲 API
// 返回指定歌曲ID的歌曲
export const getSongOfId = (id) => get(`song/detail?id=${id}`)
// 返回指定歌手ID的歌曲
export const getSongOfSingerId = (id) => get(`song/singer/detail?singerId=${id}`)
// 返回指定歌手名的歌曲
export const getSongOfSingerName = (keywords) => get(`song/singerName/detail?name=${keywords}`)
//返回类似歌曲名的歌曲
export const getLikeSongOfName = (keywords) => get(`song/likeSongOfName?songName=${keywords}`)
// 下载音乐
export const download = (url) => axios({
  method: 'get',
  url: url,
  responseType: 'blob'
})
