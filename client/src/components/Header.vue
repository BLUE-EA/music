<template>
  <div>
    <div class="header clearFixed">
      <div class="top">
        <div class="logo fl">
          <div class="word">海之乐</div>
        </div>
        <div class="option fl">
          <ul class="ulList clearFixed">
            <li :class="{active: item.name === activeName}" v-for="item in navMsg" :key="navMsg.path"
                @click="goPage(item.path, item.name)">{{item.name}}
            </li>
          </ul>
        </div>
        <div class="search fl">
          <div class="searchBox clearFixed">
            <input class="searchCase fl" type="text" v-model="keywords" name="" id="" placeholder="歌曲/歌手"
                   @keyup.enter="goSearch()">
            <input class="searchBtn fl" type="button" @click="goSearch()" value="">
            <i class="searchLogo el-icon-search" @click="goSearch()"></i>
          </div>
        </div>
        <div id="Info">
          <div class="unLogin fr clearFixed" v-if="!loginIn">
            <a class="login fl" style="cursor: pointer;" @click="loginDialogVisible = true">登录</a>
            <div class="fl">|</div>
            <a class="register fl" style="cursor: pointer;" @click="registerDialogVisible = true">注册</a>
          </div>
          <div class="userInfo fr clearFixed" v-else>
            <div class="imgHead fl">
              <el-upload
                :action="uploadUrl(userObject.id)"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload"
              >
              <span :style="{backgroundImage:'url('+attachImageUrl(userObject.avatar)+')'}"></span>
              </el-upload>
            </div>
            <div class="nickname fl">
              <el-dropdown>
                      <span class="el-dropdown-link">
                        {{userObject.username}}<i class="el-icon-arrow-down el-icon--right"></i>
                      </span>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item @click.native="infoDialogVisible=true">个人信息</el-dropdown-item>
                  <el-dropdown-item @click.native="updatePasswordDialogVisible=true">修改密码</el-dropdown-item>
                  <el-dropdown-item @click.native="logOut()">退出</el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!--登录对话框-->
    <el-dialog title="Login" :visible.sync="loginDialogVisible" width="400px" center>
      <el-form class="demo-ruleForm" :model="loginForm" status-icon ref="loginForm" label-width="80px">
        <el-form-item label="用户名" size="mini">
          <el-input v-model="loginForm.username" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item label="密码" size="mini">
          <el-input type="password" v-model="loginForm.password" placeholder="请输入密码"></el-input>
        </el-form-item>
      </el-form>
      <span class="dialog-footer" slot="footer">
        <el-button size="mini" @click="goRegister">注  册</el-button>
        <el-button type="primary" size="mini" @click="handleLogin">确 定</el-button>
      </span>
    </el-dialog>

    <!--注册对话框-->
    <el-dialog title="Register" :visible.sync="registerDialogVisible" width="450px" center>
      <el-form :model="registerForm" status-icon :rules="rules" ref="registerForm" label-width="100px"
               class="demo-ruleForm">
        <el-form-item label="用户名" prop="username" size="mini">
          <el-input v-model="registerForm.username" placeholder="用户名"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password" size="mini">
          <el-input type="password" placeholder="密码" v-model="registerForm.password"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="passwordAgain" size="mini">
          <el-input type="passwordAgain" placeholder="密码" v-model="registerForm.passwordAgain"></el-input>
        </el-form-item>
        <el-form-item label="性别" size="mini">
          <el-radio-group v-model="registerForm.sex">
            <el-radio :label="0">女</el-radio>
            <el-radio :label="1">男</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="手机" prop="phoneNum" size="mini">
          <el-input placeholder="手机" v-model="registerForm.phoneNum"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email" size="mini">
          <el-input v-model="registerForm.email" placeholder="邮箱"></el-input>
        </el-form-item>
        <el-form-item label="生日" prop="birth" size="mini">
          <el-date-picker type="date" placeholder="选择日期" v-model="registerForm.birth"
                          style="width: 100%;"></el-date-picker>
        </el-form-item>
        <el-form-item label="签名" prop="introduction" size="mini">
          <el-input type="textarea" placeholder="签名" v-model="registerForm.introduction"></el-input>
        </el-form-item>
        <el-form-item label="地区" prop="location" size="mini">
          <el-select v-model="registerForm.location" placeholder="地区">
            <el-option v-for="item in cities" :key="item.value" :label="item.label" :value="item.value"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="registerDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="doRegister()">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog title="修改信息" :visible.sync="infoDialogVisible" width="400px" center>
      <el-form ref="updateInfo" :model="updateInfo" label-width="60px">
        <el-form-item label="用户名" size="mini">
          <el-input v-model="updateInfo.username" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="性别" size="mini">
          <el-radio-group v-model="updateInfo.sex">
            <el-radio :label="0">女</el-radio>
            <el-radio :label="1">男</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="手机" size="mini">
          <el-input v-model="updateInfo.phoneNum"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" size="mini">
          <el-input v-model="updateInfo.email"></el-input>
        </el-form-item>
        <el-form-item label="生日" prop="birth" size="mini">
          <el-date-picker type="date" placeholder="选择日期" v-model="updateInfo.birth"
                          style="width: 100%;"></el-date-picker>
        </el-form-item>
        <el-form-item label="签名" size="mini">
          <el-input v-model="updateInfo.introduction"></el-input>
        </el-form-item>
        <el-form-item label="地区" size="mini">
          <el-input v-model="updateInfo.location"></el-input>
        </el-form-item>

      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="doUpdateInfo()">修  改</el-button>
      </span>
    </el-dialog>

    <el-dialog title="修改密码" :visible.sync="updatePasswordDialogVisible" width="400px" center>
      <el-form class="demo-ruleForm" :model="updatePassword" status-icon ref="newPasswordForm" label-width="120px">
        <el-form-item label="请输入新密码：" size="mini">
          <el-input type="password" v-model="updatePassword.password" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item label="再输入一次：" size="mini">
          <el-input type="password" v-model="updatePassword.passwordAgain" placeholder="请输入用户名"></el-input>
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="doUpdatePassword()">修  改</el-button>
      </span>
    </el-dialog>

  </div>

</template>

<script>
  import {mapGetters} from 'vuex';
  import {getCollectionOfUser, loginIn, SignUp, updateUserPassword, updateUserInfo, getSongOfId, getUserOfId} from '../api/index';
  import mixins from '../mixins/index';

  export default {
    name: "Header",
    mixins: [mixins],
    data() {
      return {
        rules: {
          username: [
            {required: true, trigger: 'blur'}
          ],
          password: [
            {required: true, trigger: 'blur'}
          ],
          passwordAgain: [
            {required: true, trigger: 'blur'}
          ],
          sex: [
            {required: true, message: '请选择性别', trigger: 'change'}
          ],
          phoneNum: [
            {message: '请选择日期', trigger: 'blur'}
          ],
          email: [
            {message: '请输入邮箱地址', trigger: 'blur'},
            {type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change']}
          ],
          birth: [
            {required: true, type: 'date', message: '请选择日期', trigger: 'change'}
          ],
          introduction: [
            {message: '请输入介绍', trigger: 'blur'}
          ],
          location: [
            {message: '请输入地区', trigger: 'change'}
          ]
        },
        navMsg: [
          {
            name: '首页',
            path: '/'
          }, {
            name: '歌单',
            path: '/songList?keywords=欧美',
          }, {
            name: '歌手',
            path: '/Singer'
          }, {
            name: '我的音乐',
            path: '/MyMusic'
          }
        ],
        cities: [
          {
            value: '北京',
            label: '北京'
          }, {
            value: '天津',
            label: '天津'
          }, {
            value: '河北',
            label: '河北'
          }, {
            value: '山西',
            label: '山西'
          }, {
            value: '内蒙古',
            label: '内蒙古'
          }, {
            value: '辽宁',
            label: '辽宁'
          }, {
            value: '吉林',
            label: '吉林'
          }, {
            value: '黑龙江',
            label: '黑龙江'
          }, {
            value: '上海',
            label: '上海'
          }, {
            value: '江苏',
            label: '江苏'
          }, {
            value: '浙江',
            label: '浙江'
          }, {
            value: '安徽',
            label: '安徽'
          }, {
            value: '福建',
            label: '福建'
          }, {
            value: '江西',
            label: '江西'
          }, {
            value: '山东',
            label: '山东'
          }, {
            value: '河南',
            label: '河南'
          }, {
            value: '湖北',
            label: '湖北'
          }, {
            value: '湖南',
            label: '湖南'
          }, {
            value: '广东',
            label: '广东'
          }, {
            value: '广西',
            label: '广西'
          }, {
            value: '海南',
            label: '海南'
          }, {
            value: '重庆',
            label: '重庆'
          }, {
            value: '四川',
            label: '四川'
          }, {
            value: '贵州',
            label: '贵州'
          }, {
            value: '云南',
            label: '云南'
          }, {
            value: '西藏',
            label: '西藏'
          }, {
            value: '陕西',
            label: '陕西'
          }, {
            value: '甘肃',
            label: '甘肃'
          }, {
            value: '青海',
            label: '青海'
          }, {
            value: '宁夏',
            label: '宁夏'
          }, {
            value: '新疆',
            label: '新疆'
          }, {
            value: '香港',
            label: '香港'
          }, {
            value: '澳门',
            label: '澳门'
          }, {
            value: '台湾',
            label: '台湾'
          }],
        keywords: '',
        loginDialogVisible: false,
        loginForm: {
          username: '',
          password: '',
        },
        registerDialogVisible: false,
        registerForm: {
          username: '',
          password: '',
          passwordAgain: '',
          sex: '',
          phoneNum: '',
          email: '',
          location: '',
          introduction: '',
          birth: ''
        },
        infoDialogVisible: false,
        updateInfo: {
          id: '',
          username: '',
          avatar: '',
          sex: '',
          introduction: '',
          phoneNum: '',
          email: '',
          location: '',
          birth: '',
        },
        updatePasswordDialogVisible: false,
        updatePassword: {
          id: '',
          password: '',
          passwordAgain: '',
        },
        user: {},
      }
    },
    watch: {
      userObject() {
        console.log('发生了改变');
        this.updateInfo.id = this.userObject.id
        this.updateInfo.username = this.userObject.username;
        this.updateInfo.sex = this.userObject.sex;
        this.updateInfo.introduction = this.userObject.introduction;
        this.updateInfo.phoneNum = this.userObject.phoneNum;
        this.updateInfo.email = this.userObject.email;
        this.updateInfo.location = this.userObject.location;
        this.updateInfo.birth = this.userObject.birth;
        this.updatePassword.id = this.userObject.id;
      },
      $route() {
        let name;
        for (let i = 0; i < this.navMsg.length; i++) {
          if (this.navMsg[i].path.search(this.$route.path) !== -1) {
            name = this.navMsg[i].name;
            break;
          }
        }
        this.$store.commit('setActiveName', name)
      },
    },
    computed: {
      ...mapGetters([
        'activeName',    //全网页监控这个变量名
        'loginIn',
        'userObject',
        'userCollect',
      ]),
    },
    methods: {
      uploadUrl(id) {
        //返回后端的URL
        return `http://localhost:8888/user/avatar/update?id=${id}`
      },
      // 更新图片
      handleAvatarSuccess (res, file) {
        let _this = this
        if (res.code === 1) {
          _this.imageUrl = URL.createObjectURL(file.raw)
          this.reloadUser(this.userObject.id)
          _this.$message({
            message: '上传成功',
            type: 'success'
          })
        } else {
          _this.$message({
            message: '上传失败',
            type: 'error'
          })
        }
      },
      beforeAvatarUpload (file) {
        const isJPG = (file.type === 'image/jpeg') || (file.type === 'image/png')
        const isLt2M = file.size / 1024 / 1024 < 2
        console.log(isJPG && isLt2M);
        if (!isJPG) {
          this.$message.error('上传头像图片只能是 JPG 格式!')
        }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!')
        }
        return isJPG && isLt2M
      },
      reloadUser(id){
        getUserOfId(id).then(res => {
          this.$store.commit('setUserObject', res);
        })
      },
      doRegister() {
        console.log(this.registerForm.birth);
        if (this.registerForm.password === this.registerForm.passwordAgain) {
          let d = this.registerForm.birth

          let datetime = d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate()
          let params = new URLSearchParams()
          params.append('id', this.registerForm.username)
          params.append('username', this.registerForm.username)
          params.append('password', this.registerForm.password)
          params.append('sex', this.registerForm.sex)
          params.append('phone_num', this.registerForm.phoneNum)
          params.append('email', this.registerForm.email)
          params.append('birth', datetime)
          params.append('introduction', this.registerForm.introduction)
          params.append('location', this.registerForm.location)
          params.append('avatar', '/avatarImages/default.png')
          SignUp(params)
            .then(res => {
              if (res.code === 1) {
                console.log('插入成功');
                this.$message({
                  message: res.msg,
                  type: 'success'
                })
              } else {
                this.$message({
                  message: res.msg,
                  type: 'error',
                })
              }
            })
            .catch(err => {
              console.log(err)
            })
          this.registerDialogVisible = false
        } else {
          this.$message({
            message: '密码确认失败',
            type: 'error',
          })
        }
      },
      doUpdatePassword() {
        let params = new URLSearchParams()
        if (this.updatePassword.password === this.updatePassword.passwordAgain && this.updatePassword.password !== null) {
          params.append('id', this.updateInfo.id)
          params.append('password', this.updatePassword.password)
          updateUserPassword(params).then(res => {
            if (res.code === 1) {
              this.$message({
                message: res.msg,
                type: 'success'
              })
            } else {
              this.$message({
                message: res.msg,
                type: 'error'
              })
            }
          }).catch(err => {
            console.log(err)
          })
          this.updatePasswordDialogVisible = false
        } else {
          this.$message({
            message: '密码验证失败',
            type: 'error'
          })
        }
      },
      doUpdateInfo() {
        let d = new Date(this.updateInfo.birth)
        let datetime = d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate()
        let params = new URLSearchParams()
        params.append('id', this.updateInfo.id)
        params.append('username', this.updateInfo.username)
        params.append('sex', this.updateInfo.sex)
        params.append('phone_num', this.updateInfo.phoneNum)
        params.append('email', this.updateInfo.email)
        params.append('birth', datetime)
        params.append('introduction', this.updateInfo.introduction)
        params.append('location', this.updateInfo.location)
        updateUserInfo(params).then(res => {
          if (res.code === 1) {
            this.reloadUser(this.updateInfo.id);
            this.$message({
              message: res.msg,
              type: 'success'
            })
          } else {
            this.$message({
              message: res.msg,
              type: 'error'
            })
          }
        }).catch(err => {
          console.log(err)
        })
        this.infoDialogVisible = false
      },
      goPage(path, name) {
        if(path !== '/MyMusic'){
          this.$store.commit('setActiveName', name)
          this.$router.push({path: path})
        }else{
          if(this.loginIn){
            this.$store.commit('setActiveName', name)
            this.$router.push({path: path})
          }else{
            this.$message({
              message: '你还未登录',
              type: 'warning',
            })
          }
        }

      },
      goSearch() {
        this.$router.push({path: '/Search', query: {keywords: this.keywords}});
      },
      handleLogin() {
        let params = new URLSearchParams()
        params.append('username', this.loginForm.username)
        params.append('password', this.loginForm.password)
        loginIn(params).then(res => {
            if (res.code === 1) {
              this.loginDialogVisible = false
              this.$message({
                message: res.msg,
                type: 'success'
              });
              this.$store.commit('setLoginIn', true);
              this.$store.commit('setUserObject', res.userMsg[0]);
              //获取登录用户的收藏列表，初始化
              getCollectionOfUser(this.userObject.id).then(res => {
                let result = [];
                for(let i=0;i<res.length;i++){
                  getSongOfId(res[i].songId).then(songRes => {
                    result.push(songRes[0]);
                  })
                }
                this.$store.commit('setUserCollect', result);
              }).catch(err => {
                console.log(err);
              })
            } else {
              this.$message({
                message: res.msg,
                type: 'error',
              })
            }
          })
          .catch(failResponse => {
          })
      },
      goRegister() {
        this.loginDialogVisible = false;
        this.registerDialogVisible = true;
      },
      logOut() {
        console.log("退出");
        this.$store.commit('setLoginIn', false)
      },
    }
  }
</script>

<style scoped>

  /** {*/
  /*margin: 0;*/
  /*padding: 0;*/
  /*}*/

  p, h1, h2, h3, h4, h5, h6, dl, dt, dd, ol, ul, li {
    margin: 0px;
    padding: 0px;
  }

  .header .fl {
    float: left;
  }

  .header .fr {
    float: right;
  }

  .header li {
    list-style: none;
  }

  .header a {
    text-decoration: none;
  }

  .header a:hover {
    text-decoration: none
  }

  .header .clearFixed:after {
    content: "";
    display: block;
    clear: both;
  }

  .header input {
    border: none
  }

  body {
    background-color: #e6e6e6;
  }

  .header {
    height: 60px;
    background-color: black;
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    width: 100%;
    z-index: 100;
  }

  .top {
    width: 1348px;
    height: 60px;
    margin: 0px auto;
    /*background-color: gray;*/
  }

  .top .logo {
    width: 210px;
    height: 60px;
  }

  .top .logo .word {
    margin: 16px 71px 20px 52px;
    font-size: 24px;
    font-family: 华文仿宋;
    color: #807c79;
    font-weight: 900;
  }

  .top .option {
    width: 574px;
    height: 60px;
    line-height: 60px;
  }

  .top .option .ulList li {
    height: 60px;
    width: 82px;
    text-align: center;
    font-size: 14px;
    float: left;
    color: white;
    cursor: pointer;
  }

  .top .option .ulList .active {
    height: 60px;
    width: 82px;
    background-color: red;
  }

  .top .search {
    width: 282px;
    height: 60px;
  }

  .top .search .searchBox {
    /*width: 240px;*/
    height: 30px;
  }

  .top .search .searchBox .searchCase {
    width: 198px;
    height: 30px;
    text-indent: 13px;
    margin-left: 26px;
    margin-top: 14px;
    background-color: #dcdcdc;
    border-right: 2px solid #cccccc;
    border-bottom-left-radius: 5px;
    border-top-left-radius: 5px;
  }

  .header input::-webkit-input-placeholder,
  .header textarea::-webkit-input-placeholder {
    color: #aaaabb;
  }

  .header input:focus, textarea:focus {
    outline: none;
  }

  .top .search .searchBox .searchBtn {
    cursor: pointer;
    height: 30px;
    width: 40px;
    background-color: #dcdcdc;
    color: #333333;
    margin-left: -5px;
    margin-top: 14px;
    border-bottom-right-radius: 5px;
    border-top-right-radius: 5px;
  }

  .top .search .searchBox .searchLogo {
    margin-top: 22px;
    margin-left: -26px;
    color: #333333;
    z-index: 110;
    cursor: pointer;
  }

  .top .unLogin {
    width: 174px;
    height: 60px;
    line-height: 60px;
  }

  .top .unLogin a {
    color: white;
    margin: 0 15px;
  }

  .top .unLogin div {
    color: white;
  }

  .top .userInfo {
    width: 174px;
    height: 60px;
  }

  .top .userInfo .imgHead {
    width: 52px;
    height: 52px;
    border-radius: 26px;
    background-color: white;
    margin-top: 4px;
  }

  .top .userInfo .imgHead span {
    float: left;
    width: 52px;
    height: 52px;
    position: relative;
    background-repeat: no-repeat;
    background-size: 52px 52px;
    border-radius: 26px;
  }

  .top .userInfo .nickname {
    margin-left: 32px;
    padding-top: 21px;
    color: white;
    font-size: 14px;
    cursor: pointer;
  }

  .header .userInfo .nickname span {
    color: white;
  }
</style>
