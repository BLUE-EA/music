<template>
  <div class="box">
    <div class="background"></div>
    <div class="loginBox">
      <div class="title">Login</div>
      <div>
        <input type="text" ref="username" name="username" @blur="usernameBlur" v-model="Form.username" class="inputCase"
               placeholder="用户名">
        <input type="password" ref="password" name="password" @blur="usernameBlur" v-model="Form.password"
               class="inputCase" placeholder="密码">
        <div class="message">{{message}}</div>
        <input type="button" @click="submitForm" class="btnCase" value="登    录">
      </div>
    </div>
  </div>
</template>

<script>
  import {getLoginStatus} from '../api/index'

  export default {
    name: "Login",
    data() {
      return {
        message: '',
        Form: {
          username: 'admin',
          password: '123',
        },
      }
    },
    methods: {

      submitForm() {
        let params = new URLSearchParams();
        params.append('username', this.Form.username);
        params.append('password', this.Form.password);
        getLoginStatus(params)
          .then(res => {
            if (res.code === 1) {
              localStorage.setItem('username',this.Form.username);
              this.$router.push('/Info')
              this.$notify({
                title: '登陆成功',
                message: '欢迎来到音乐后台管理',
                type: 'success',
              })
            } else {
              this.message = '用户名或者密码错误';
            }
          })
          .catch(err => {
            console.log(err);
          })
      },

      usernameBlur() {
        if (this.Form.username === '' || this.Form.password === '')
          this.message = '请输入用户名或密码';
        else
          this.message = '';
      }
    }
  }
</script>

<style scoped>
  * {
    margin: 0;
    padding: 0;
  }

  p, h1, h2, h3, h4, h5, h6, dl, dt, dd, ol, ul, li {
    margin: 0px;
    padding: 0px;
  }

  .fl {
    float: left;
  }

  .fr {
    float: right;
  }

  li {
    list-style: none;
  }

  a {
    text-decoration: none;
  }

  a:hover {
    text-decoration: none
  }

  .clearFixed:after {
    content: "";
    display: block;
    clear: both;
  }

  .background {
    height: 400px;
    width: 100%;
    background-color: black;
  }

  .loginBox {
    width: 500px;
    height: 600px;
    margin: auto;
    margin-top: -200px;
    background-color: white;
    border: 2px #807c79 solid;
    border-radius: 10px;
  }

  .loginBox .title {
    font-size: 60px;
    font-weight: 900;
    margin-top: 40px;
    padding-left: 160px;
    margin-bottom: 50px;
  }

  .loginBox .inputCase {
    height: 40px;
    width: 350px;
    font-size: 16px;
    text-indent: 13px;
    margin: 30px 68px 10px;
    border-radius: 5px;
    border: 2px #000000 solid;
  }

  .loginBox .message {
    margin-left: 68px;
    height: 20px;
    width: 350px;
    color: red;
    font-size: 16px;
    padding-left: 20px;
  }

  .loginBox .btnCase {
    height: 60px;
    width: 350px;
    font-size: 25px;
    font-weight: bolder;
    color: white;
    text-indent: 13px;
    margin: 60px 68px 10px;
    border-radius: 10px;
    background-color: black;
  }
</style>
