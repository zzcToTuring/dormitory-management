<!--通知管理 公告栏-->
<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search" v-model="oldName"></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button type="warning" @click="reset">重置</el-button>
    </div>
    <div style="margin: 10px 0">
      <el-upload action="http://localhost:8088/file/upload" :show-file-list="false" :on-success="handleFileUploadSuccess" style="display: inline-block">
        <el-button type="primary" :disabled="this.user.account!=='admin' " class="ml-5" >上传文件 <i class="el-icon-top"></i></el-button>
      </el-upload>
      <el-popconfirm
          class="ml-5"
          confirm-button-text='确定'
          cancel-button-text='我再想想'
          icon="el-icon-info"
          icon-color="red"
          title="您确定批量删除这些数据吗？"
          @confirm="delBatch"
      >
        <el-button type="danger" slot="reference" :disabled="this.user.account!=='admin'">批量删除 <i class="el-icon-remove-outline"></i></el-button>
      </el-popconfirm>
    </div>

    <el-table :data="tableData.slice((pageNum-1)*pageSize,pageNum*pageSize)" style="width: 100%" border stripe :header-cell-class-name="'headerBg'"  @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="45" align="center"></el-table-column>
      <el-table-column prop="id" label="编号" width="80" align="center"></el-table-column>
      <el-table-column prop="oldName" label="通知概要" align="center" ></el-table-column>
      <el-table-column prop="type" label="附件类型" width="80" align="center"></el-table-column>
      <el-table-column prop="size" label="附件大小(kb)" width="100" align="center"></el-table-column>
      <el-table-column label="附件下载" align="center" width="100">
        <template slot-scope="scope">
          <el-button type="primary" :disabled ="scope.row.enable === 0 ? false : true" @click="download(scope.row.url)">下载</el-button>
        </template>
      </el-table-column>
      <el-table-column prop="enable" label="状态" align="center" width="170">
        <template slot-scope="scope">
          <p v-if="scope.row.enable === 0">启用</p>
          <p v-if="scope.row.enable === 1">已禁用(请联系系统管理员)</p>
        </template>
      </el-table-column>
      <el-table-column v-if="show" label="操作"  width="200" align="center">
        <template slot-scope="scope">
          <el-popconfirm
              class="ml-5"
              confirm-button-text='确定'
              cancel-button-text='我再想想'
              icon="el-icon-info"
              icon-color="red"
              title="您确定删除吗？"
              @confirm="del(scope.row.id)"
          >
            <el-button type="warning" slot="reference">删除<i class="el-icon-remove-outline"></i></el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <div style="padding: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[5, 10, 15, 20]"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>

  </div>
</template>

<script>
export default {
  name: "File",
  data(){
    return{
      tableData:[],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      oldName:"",
      show:true,
      multipleSelection: [],
    }
  },
  methods: {
    load() {
      this.request.get("/file/getAllFile", {
        params: {
          oldName: this.oldName,
        }
      }).then(res => {
        console.log(res)
        this.tableData = res.data
        this.total = res.total

      })
    },
    del(id) {
      this.request.post("/file/upDelete/").then(res => {
        if (res.status == 201) {
          this.$message.success("删除成功")
          this.load()
        } else {
          this.$message.error(res.msg);
        }
      })
    },
    delBatch() {
      let ids = this.multipleSelection.map(v => v.id)  // [{}, {}, {}] => [1,2,3]
      this.request.post("/file/upDelete/batch/").then(res => {
        if (res) {
          this.$message.success("批量删除成功")
          this.load()
        } else {
          this.$message.error("批量删除失败")
        }
      })
    },
    changeEnable(document) {
      this.request.post("/file/update", document).then(res => {
        if (res.status == 201) {
          if (res.data.enable == 0){
            this.$message.success("启用操作成功")
          }else {
            this.$message.success("禁用操作成功")
          }
          this.load()
        }
      })
    },
    handleSelectionChange(val) {
      console.log(val)
      this.multipleSelection = val
    },
    reset() {
      this.oldName = ""
      this.load()
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.pageNum = 1;
      this.pageSize = val;
      this.load()
    },
    handleCurrentChange(val) {
      this.load()
    },
    handleFileUploadSuccess(res){
      console.log(res)
      if(res === null){
        this.$message.success("上传成功")
      }
      this.load()
    },
    download(url) {
      window.open(url)
    }
  }

}
</script>

<style scoped>

</style>
