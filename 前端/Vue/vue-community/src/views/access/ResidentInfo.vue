<!--寝室维修管理-->
<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入姓名" suffix-icon="el-icon-search" v-model="resName"></el-input>
      <el-input style="width: 200px" placeholder="请输入校区" suffix-icon="el-icon-message" class="ml-5" v-model="name" disabled></el-input>
      <el-input style="width: 200px" placeholder="请输入楼栋" suffix-icon="el-icon-position" class="ml-5" v-model="house" disabled></el-input>
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
          title="您确定批量删除这些数据吗？"
          @confirm="delBatch"
      >
        <el-button type="danger" slot="reference">批量处理<i class="el-icon-remove-outline"></i></el-button>
      </el-popconfirm>
    </div>

    <el-table :data="tableData.slice((pageNum-1)*pageSize,pageNum*pageSize)" style="width: 100%" border stripe :header-cell-class-name="'headerBg'"  @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="40" align="center"></el-table-column>
      <el-table-column prop="resInfoId" label="编号" width="60" align="center"></el-table-column>
      <el-table-column prop="resName" label="姓名" width="80" align="center"></el-table-column>
      <el-table-column prop="resPhone" label="电话" width="100" align="center"></el-table-column>
      <el-table-column prop="address.name" label="校区" width="120" align="center"></el-table-column>
      <el-table-column prop="address.house" label="楼栋" width="60" align="center"></el-table-column>
      <el-table-column prop="address.unit" label="宿舍号" width="65" align="center"></el-table-column>
      <el-table-column prop="infoRemark" label="情况说明" width="380" align="center"></el-table-column>
      <el-table-column prop="infoTime" label="时间" width="110" align="center"></el-table-column>
      <el-table-column prop="infoStatus" label="状态" width="80" align="center">
        <template slot-scope="scope">
          <p v-if="scope.row.infoStatus === 0">正常</p>
          <p v-else>取消/已完成</p>
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
              title="您确定操作吗？"
              @confirm="del(scope.row.resInfoId)"
          >
            <el-button type="success" slot="reference">已完成<i class="el-icon-circle-check"></i></el-button>
          </el-popconfirm>
          <el-popconfirm
              class="ml-5"
              confirm-button-text='确定'
              cancel-button-text='我再想想'
              icon="el-icon-info"
              icon-color="red"
              title="您确定操作吗？"
              @confirm="del()"
          >
            <el-button type="danger" slot="reference">取消<i class="el-icon-refresh-left"></i></el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
   
  </div>
</template>
<script>

export default {
  name: "ResidentInfo",
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
      resPhone: "",
      unit: "",
      resState: "",
      form: {
        resSex: "1",
      },
      multipleSelection: [],
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      this.request.get("/residentInfo/getAllInfo", {
        params: {
          name: this.name,
          resName: this.resName,
          house: this.house,
        }
      }).then(res => {
        console.log(res)
        this.tableData = res.data
        this.total = res.total

      })
    },
    del(id) {
      this.request.post("/residentInfo/upDeleteInfo/" + id).then(res => {
        if (res.status == 201) {
          this.$message.success("操作成功")
        } else {
          this.$message.error(res.msg);
        }
      })
    },
    delBatch() {
      let ids = this.multipleSelection.map(v => v.resInfoId)  // [{}, {}, {}] => [1,2,3]
      this.request.post("/residentInfo/upDeleteInfo/batch", ids).then(res => {
        if (res.status == 201) {
          this.$message.success("批量删除成功")
          this.load()
        } else {
          this.$message.error("批量删除失败")
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
      this.pageSize = val+1;
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
