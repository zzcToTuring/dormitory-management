<!--历史信息--寝室信息维护-->
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
          title="您确定批量恢复这些数据吗？"
          @confirm="recoveryBatch"
      >
        <el-button type="success" slot="reference" :disabled="!show">批量恢复 <i class="el-icon-magic-stick"></i></el-button>
      </el-popconfirm>
    </div>

    <el-table :data="tableData.slice((pageNum-1)*pageSize,pageNum*pageSize)" style="width: 100%" border stripe :header-cell-class-name="'headerBg'"  @selection-change="handleSelectionChange">

      <el-table-column type="selection" width="50" align="center"></el-table-column>
      <el-table-column prop="resInfoId" label="编号" width="70" align="center"></el-table-column>
      <el-table-column prop="resName" label="姓名" width="90" align="center"></el-table-column>
      <el-table-column prop="resPhone" label="电话" width="100" align="center"></el-table-column>
      <el-table-column prop="address.name" label="校区" width="120" align="center"></el-table-column>
      <el-table-column prop="address.house" label="楼栋" width="60" align="center"></el-table-column>
      <el-table-column prop="address.unit" label="宿舍号" width="65" align="center"></el-table-column>
      <el-table-column prop="infoRemark" label="情况说明" width="380" align="center"></el-table-column>
      <el-table-column prop="infoTime" label="时间" width="110" align="center"></el-table-column>
      <el-table-column prop="infoStatus" label="状态" width="120" align="center">
        <template slot-scope="scope">
          <p v-if="scope.row.infoStatus === 0">正常</p>
          <p v-else>取消/已完成</p>
        </template>
      </el-table-column>
      <el-table-column label="操作"   align="center">
        <template  slot-scope="scope">
          <el-popconfirm
              class="ml-5"
              confirm-button-text='确定'
              cancel-button-text='我再想想'
              icon="el-icon-info"
              title="您确定恢复吗？"
              @confirm="recovery(scope.row.resInfoId)"
          >
            <el-button type="success" slot="reference" :disabled="show">恢复<i class="el-icon-magic-stick"></i></el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>
<script>

export default {
  name: "ResidentInfoDustbin",
  data() {
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      resName: "",
      house: "",
      resSex: "",
      resInfoId:"",
      name:"",
      resRemarks: "",
      resPhone: "",
      infoStatus: "",
      unit: "",
      resState: "",
      show:"",
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
      this.request.get("/dustbin/getResidentInFo", {
        params: {
          name: this.name
        }
      }).then(res => {
        console.log(res)
        this.tableData = res.data
        this.total = res.total

      })
    },
    delBatch() {
      let ids = this.multipleSelection.map(v => v.resInfoId)  // [{}, {}, {}] => [1,2,3]
      this.request.post("/dustbin/deleteResidentInfo/batch", ids).then(res => {
        if (res.status == 200) {
          this.$message.success("批量清除成功")
        } else {
          this.$message.error("批量清除失败")
        }
      })
    },
    recovery(id){
      this.request.post("/dustbin/recResidentInfo/" + id).then(res => {
        if (res.status == 201) {
          this.$message.success("恢复成功")
        } else {
          this.$message.error(res.msg);
        }
      })
    },
    recoveryBatch() {
      let ids = this.multipleSelection.map(v => v.resInfoId)  // [{}, {}, {}] => [1,2,3]
      this.request.post("/dustbin/recResidentInfo/batch", ids).then(res => {
        if (res.status == 201) {
          this.$message.success("批量恢复成功")
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
      this.name = ""
      this.house = ""
      this.load()
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.pageNum = 1;
      this.pageSize = val-1;
      this.load()
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.pageNum = val+1;
      this.load()
    }
  }
}
</script>


<style>
</style>
