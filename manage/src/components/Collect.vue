<template>
  <div class="table">
    {{id}}
    <div class="crumbs">
      <el-breadcrumb style="margin: 10px;font-size: 20px;font-family: 'Times New Roman';" separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-tickets"></i> 收藏信息
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <div class="handle-box">
        <el-button type="danger" size="mini" class="handle-del mr10" @click="delAll">批量删除</el-button>
        <el-input v-model="select_word" style="float: right;margin-right: 10%;" size="mini" placeholder="筛选关键词"
                  class="handle-input mr10"></el-input>
      </div>
      <el-table
        :data="data"
        size="mini"
        border
        style="width: 100%"
        height="700px"
        ref="multipleTable"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="50"></el-table-column>
        <el-table-column prop="name" label="歌手-歌曲"></el-table-column>
        <el-table-column label="操作" width="85">
          <template slot-scope="scope">
            <el-button size="mini" type="danger" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination">
        <el-pagination
          @current-change="handleCurrentChange"
          background
          layout="total, prev, pager, next"
          :current-page="currentPage"
          :page-size="pageSize"
          :total="tableData.length">
        </el-pagination>
      </div>
    </div>

    <!-- 删除提示框 -->
    <el-dialog title="提示" :visible.sync="delVisible" width="300px" center>
      <div class="del-dialog-cnt" align="center">删除不可恢复，是否确定删除？</div>
      <span slot="footer" class="dialog-footer">
        <el-button size="mini" @click="delVisible = false">取 消</el-button>
        <el-button type="primary" size="mini" @click="deleteRow">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  import {mixin} from '../mixins'
  import {getCollectionOfUser, deleteCollection, getSongOfId} from '../api/index'

  export default {
    name: 'Collect',
    mixins: [mixin],
    props: ['id'],
    data() {
      return {
        tableData: [], // 记录歌曲，用于显示
        tempDate: [], // 记录歌曲，用于搜索时能临时记录一份歌曲列表
        tempId: [], // 记录列表中歌曲的id
        multipleSelection: [], // 记录要删除的歌曲
        delVisible: false, // 显示删除框
        select_word: '', // 记录输入框输入的内容
        idx: -1, // 记录当前要删除的歌曲
        currentPage: 1,
        pageSize: 16,
      }
    },
    computed: {
      data() {
        return this.tableData.slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize)
      }
    },
    watch: {
      select_word: function () {
        if (this.select_word === '') {
          this.tableData = this.tempDate
        } else {
          this.tableData = []
          for (let item of this.tempDate) {
            if (item.name.includes(this.select_word)) {
              this.tableData.push(item)
            }
          }
        }
      }
    },
    mounted() {
      this.getData()
    },
    methods: {
      // 通过用户id获取用户收藏的歌曲id
      getData() {
        getCollectionOfUser(this.$route.query.id)
          .then(res => {
            this.tableData = []
            for (let item of res) {
              this.getSongList(item.songId)
            }
          })
          .catch(err => {
            console.log(err)
          })
      },
      // 获取当前页
      handleCurrentChange(val) {
        this.currentPage = val
      },
      // 通过歌曲ID获取歌曲
      getSongList(id) {
        getSongOfId(id)
          .then(res => {
            this.tableData.push(res[0])
            this.tempDate.push(res[0])
          })
          .catch(err => {
            console.log(err)
          })
      },
      // 删除一首歌曲
      deleteRow() {
        deleteCollection(this.$route.query.id, this.idx.id)
          .then(res => {
            if (res) {
              this.getData()
              this.notify('删除成功', 'success')
            } else {
              this.notify('删除失败', 'error')
            }
          })
          .catch(err => {
            console.log(err)
          })
        this.delVisible = false
      }
    }
  }
</script>

<style scoped>
  .table {
    height: 100%;
    /*padding: 0 0px 0;*/
    /*background-color: whitesmoke;*/
    overflow: scroll;
    bottom: 0;
    right: 0;
  }

  .handle-box {
    margin-bottom: 20px;
    margin-left: 20px;
    margin-top: 20px;
  }

  .handle-input {
    width: 300px;
    display: inline-block;
  }

  ::-webkit-scrollbar {
    width: 5px;
    height: 5px;
  }

  ::-webkit-scrollbar-thumb {
    background-color: #bfbebe;
    border-radius: 3px;
  }

  .pagination {
    margin-top: 15px;
    display: flex;
    justify-content: center;
  }
</style>
