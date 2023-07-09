<!--历史信息---迁出学生信息-->
<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入姓名" suffix-icon="el-icon-search" v-model="resName"></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button type="warning" @click="reset">重置</el-button>
    </div>

    <div style="margin: 10px 0">
      <el-popconfirm
          class="ml-5"
          confirm-button-text='确定'
          cancel-button-text='我再想想'
          icon="el-icon-info"
          icon-color="red"
          title="您确定批量清除这些数据吗？"
          @confirm="delBatch"
      >
        <el-button type="danger" slot="reference" :disabled="show" >批量清除<i class="el-icon-remove-outline"></i></el-button>
      </el-popconfirm>

      <el-popconfirm
          class="ml-5"
          confirm-button-text='确定'
          cancel-button-text='我再想想'
          icon="el-icon-info"
          icon-color="red"
          title="您确定批量恢复这些数据吗？"
          @confirm="recBatch"
      >
        <el-button type="success" slot="reference" :disabled="show">批量恢复 <i class="el-icon-finished"></i></el-button>
      </el-popconfirm>
    </div>

    <el-table :data="tableData.slice((pageNum-1)*pageSize,pageNum*pageSize)" style="width: 100%" border stripe :header-cell-class-name="'headerBg'"  @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="45" align="center"></el-table-column>
      <el-table-column prop="resId" label="学号" width="60" align="center"></el-table-column>
      <el-table-column prop="resName" label="姓名" width="70" align="center"></el-table-column>
      <el-table-column prop="resSex" label="性别" width="70" align="center">
        <template slot-scope="scope">
          <p v-if="scope.row.resSex === 0">女</p>
          <p v-else>男</p>
        </template>
      </el-table-column>
      <el-table-column prop="resPhone" label="电话" width="100" align="center"></el-table-column>
      <el-table-column prop="name" label="校区" width="90" align="center"></el-table-column>
      <el-table-column prop="house" label="楼栋" width="60" align="center"></el-table-column>
      <el-table-column prop="unit" label="宿舍号" width="80" align="center"></el-table-column>
      <el-table-column prop="residentCount" label="人数" width="60" align="center"></el-table-column>
      <el-table-column prop="resIntotime" label="时间" width="120" align="center"></el-table-column>
      <el-table-column prop="resState" label="学生标记" width="80" align="center">
        <template slot-scope="scope">
          <p v-if="scope.row.resState === 0">正常学生</p>
          <p v-if="scope.row.resState === 1">晚归</p>
          <p v-if="scope.row.resState === 2">请假</p>
          <p v-if="scope.row.resState === 3">短期外宿</p>
          <p v-if="scope.row.resState === 4">多次违纪</p>
          <p v-if="scope.row.resState === 5">其他情况</p>
          <p v-if="scope.row.resState === 6">考察期</p>
        </template>
      </el-table-column>
      <el-table-column prop="resRemarks" label="备注" width="200" align="center"></el-table-column>
      <el-table-column prop="resStatus" label="状态" width="70" align="center">
        <template slot-scope="scope">
          <p v-if="scope.row.resStatus === 1">已注销</p>
        </template>
      </el-table-column>
      <el-table-column label="操作"   align="center">
        <template slot-scope="scope">
          <el-popconfirm
              class="ml-5"
              confirm-button-text='确定'
              cancel-button-text='我再想想'
              icon="el-icon-info"
              icon-color="red"
              title="您确定恢复这条数据吗？"
              @confirm=" recovery(scope.row.resId)"
          >
            <el-button type="success" slot="reference" :disabled=show>恢复<i class="el-icon-finished"></i></el-button>
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
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>

  </div>
</template>
<script>

export default {
  name: "ResidentListDustbin",
  data() {
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      resName: "",
      house: "",
      resSex: "",
      resRemarks: "",
      resPhone: "",
      resStatus: "",
      unit: "",
      resId:"",
      resState: "",
      show:"",
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      multipleSelection: [],
    }
  },
  created() {
    if (this.user.account !== 'admin'){
      this.show =true
    }else {
      this.show =false
    }
    this.load()
  },
  methods: {
    load() {
      this.request.get("/dustbin/getResident", {
        params: {
          resName: this.resName,
        }
      }).then(res => {
        console.log(res)
        //this.tableData = res.data
        this.total = res.total
        this.tableData = this.sortBykey(res.data, 'resId')
      })
    },
    sortBykey(ary, key) {
      return ary.sort(function (a, b) {
        let x = a[key]
        let y = b[key]
        return ((x < y) ? -1 : (x > y) ? 1 : 0)
      })
    },
    delBatch() {
      let ids = this.multipleSelection.map(v => v.resId)  // [{}, {}, {}] => [1,2,3]
      this.request.post("/dustbin/deleteResident/batch", ids).then(res => {
        if (res.status == 200) {
          this.$message.success("批量清除成功")
          this.load()
        } else {
          this.$message.error("批量清除失败")
        }
      })
    },
    recovery(id){
      this.request.post("/dustbin/recResident/" + id).then(res => {
        if (res.status == 201) {
          this.$message.success("恢复成功")
          this.load()
        } else {
          this.$message.error(res.msg);
        }
      })
    },

    recBatch(){
      let ids = this.multipleSelection.map(v => v.resId)  // [{}, {}, {}] => [1,2,3]
      this.request.post("/dustbin/recResident/batch", ids).then(res => {
        if (res) {
          this.$message.success("批量恢复成功")
          this.load()
        } else {
          this.$message.error("批量恢复失败")
        }
      })
    },
    handleSelectionChange(val) {
      console.log(val)
      this.multipleSelection = val
    },
    reset() {
      this.resName = ""
      this.house = ""
      this.unit = ""
      this.load()
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.pageNum = 1;
      this.pageSize = val;
      this.load()
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.pageNum = val;
      this.load()
    },
    exp() {
      window.open("http://localhost:9090/api/user/export")
    },
    handleExcelImportSuccess() {
      this.$message.success("导入成功")
      this.load()
    }
  }
}
</script>


<style>
.headerBg {
  background: #eee!important;
}
</style>
