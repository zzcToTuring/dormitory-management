<!--历史信息-来访登记-->
<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入访客姓名" suffix-icon="el-icon-search" v-model="visName"></el-input>
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
        <el-button type="danger" slot="reference" :disabled="!show">批量删除<i class="el-icon-remove-outline"></i></el-button>
      </el-popconfirm>

      <el-popconfirm
          class="ml-5"
          confirm-button-text='确定'
          cancel-button-text='我再想想'
          icon="el-icon-info"
          icon-color="red"
          title="您确定批量恢复这些数据吗？"
          @confirm="recoveryBatch"
      >
        <el-button type="success" slot="reference" :disabled="!show">批量恢复 <i class="el-icon-document-checked"></i></el-button>
      </el-popconfirm>
    </div>

    <el-table :data="tableData.slice((pageNum-1)*pageSize,pageNum*pageSize)" style="width: 100%" border stripe :header-cell-class-name="'headerBg'"  @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"></el-table-column>
      <el-table-column prop="visitorId" label="编号" width="50" align="center"></el-table-column>
      <el-table-column prop="visName" label="姓名" width="100" align="center"></el-table-column>
      <el-table-column prop="visPhone" label="电话" width="130" align="center"></el-table-column>
      <el-table-column prop="visAddress" label="访客地址" align="center"></el-table-column>
      <el-table-column prop="house" label="楼栋" width="70" align="center"></el-table-column>
      <el-table-column prop="unit" label="宿舍号" width="80" align="center"></el-table-column>
      <el-table-column prop="visRemark" label="原由" align="center"></el-table-column>
      <el-table-column prop="visTime" label="来访访时间"  width="100" align="center"></el-table-column>
      <el-table-column prop="visStatus" label="状态" width="100" align="center">
        <template slot-scope="scope">
          <p v-if="scope.row.visStatus === 1">已到访/取消</p>
        </template>
      </el-table-column>
      <el-table-column label="操作"  width="200" align="center">
        <template slot-scope="scope">
          <el-popconfirm
              class="ml-5"
              confirm-button-text='确定'
              cancel-button-text='我再想想'
              icon="el-icon-info"
              icon-color="red"
              title="您确定恢复这条信息吗？"
              @confirm="recovery(scope.row.visitorId)"
          >
            <el-button type="success" slot="reference" :disabled="!show">恢复<i class="el-icon-document-checked"></i></el-button>
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
  name: "VisitorInformationDustbin",
  data() {
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      visName: "",
      house: "",
      visType: "",
      visitorId: "",
      visHelthCode: "",
      unit: "",
      visStatus:"",
      show:"",
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      multipleSelection: [],
    }
  },
  created() {
    if (this.user.account === 'admin'){
      this.show =true
    }else {
      this.show =false
    }
    this.load()
  },
  methods: {
    load() {
      this.request.get("/dustbin/getAllVisitor", {
        params: {
          visName: this.visName,
        }
      }).then(res => {
        console.log(res)
        this.tableData = res.data
        this.total = res.total

      })
    },
    recovery(id){
      this.request.post("/dustbin/recVisitor/" + id).then(res => {
        if (res.status == 201) {
          this.$message.success("恢复成功")
          this.load()
        } else {
          this.$message.error(res.msg);
        }
      })
    },
    recoveryBatch(){
      let ids = this.multipleSelection.map(v => v.visitorId)  // [{}, {}, {}] => [1,2,3]
      this.request.post("/dustbin/recVisitor/batch", ids).then(res => {
        if (res) {
          this.$message.success("批量恢复成功")
          this.load()
        } else {
          this.$message.error("批量恢复失败")
        }
      })
    },
    delBatch() {
      let ids = this.multipleSelection.map(v => v.visitorId)  // [{}, {}, {}] => [1,2,3]
      this.request.post("/dustbin/deleteVisitor/batch", ids).then(res => {
        if (res.status == 200) {
          this.$message.success("批量清除成功")
          this.load()
        } else {
          this.$message.error("批量清除失败")
        }
      })
    },
    handleSelectionChange(val) {
      console.log(val)
      this.multipleSelection = val
    },
    reset() {
      this.visName = ""
      this.visHelthCode = ""
      this.visType = ""
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
    }
  }
}
</script>


<style>
.headerBg {
  background: #eee!important;
}
</style>
