<template>
  <div class="header">
    <span class="title">音乐后台管理</span>
    <div class="userInfo">
      <div class="imgHead">
        <span></span>
      </div>
      <div class="nickname">
        <el-dropdown @command="clickListener">
          <span class="el-dropdown-link">
            {{userName}}  <i class="el-icon-arrow-down el-icon--right"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <!--<el-dropdown-item command="adminInfo">个人信息</el-dropdown-item>-->
            <el-dropdown-item command="logout">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "Header",
    data() {
      return {}
    },
    computed: {
      userName() {
        return localStorage.getItem('username');
      }
    },
    methods: {
      clickListener(command) {
        if (command === 'logout') {
          localStorage.removeItem('username');
          this.$router.push('/');
        }
        if (command === 'adminInfo') {

        }

      }
    }
  }
</script>

<style scoped>

  .header {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    width: 100%;
    height: 70px;
    z-index: 100;
    background-color: #242526;
  }

  .title {
    font-size: 30px;
    color: white;
    margin-left: 150px;
    line-height: 70px;
    text-align: center;
  }

  .userInfo {
    width: 174px;
    height: 70px;
    float: right;
    margin-right: 100px;
  }

  .userInfo .imgHead {
    width: 52px;
    height: 52px;
    border-radius: 26px;
    background-color: white;
    margin-top: 9px;
    float: left;
  }

  .userInfo .imgHead span {
    float: left;
    width: 52px;
    height: 52px;
    position: relative;
    background-image: url("../assets/img/imgHead.jpg");
    background-repeat: no-repeat;
    background-size: 52px 52px;
    border-radius: 26px;
  }

  .userInfo .nickname {
    line-height: 70px;
    text-align: center;
    font-size: 14px;
  }

  .userInfo .nickname span {
    color: white;
    cursor: pointer;
  }
</style>
