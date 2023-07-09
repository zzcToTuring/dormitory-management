<!--来访信息管理-->
<!--访客姓名可以搜索，其他不能搜索，删除功能模块完成 -->
<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入访客姓名" suffix-icon="el-icon-search" v-model="visName"></el-input>
      <el-input style="width: 200px" placeholder="请输入楼栋" suffix-icon="el-icon-search" class="ml-5" v-model="ser_house" disabled></el-input>
      <el-input style="width: 200px" placeholder="请输入宿舍号" suffix-icon="el-icon-search" class="ml-5" v-model="ser_unit" disabled></el-input>
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
      <el-table-column prop="visitorId" label="编号" width="60" align="center"></el-table-column>
      <el-table-column prop="visName" label="姓名" width="80" align="center"></el-table-column>
      <el-table-column prop="visPhone" label="电话" width="120" align="center"></el-table-column>
      <el-table-column prop="visAddress" label="来访者地址" align="center"></el-table-column>
      <el-table-column prop="name" label="来访校区" align="center" width="90"></el-table-column>
      <el-table-column prop="house" label="楼栋" align="center" width="65"></el-table-column>
      <el-table-column prop="unit" label="宿舍号" align="center" width="70"></el-table-column>
      <el-table-column prop="visRemark" label="原由" align="center"></el-table-column>
      <el-table-column prop="visTime" label="时间"  width="100" align="center"></el-table-column>
      <el-table-column prop="visStatus" label="状态" align="center" width="80">
        <template slot-scope="scope">
          <p v-if="scope.row.visStatus === 0">正常</p>
        </template>
      </el-table-column>
      <el-table-column label="操作"  width="100" align="center">
        <template slot-scope="scope">
          <el-popconfirm
              class="ml-5"
              confirm-button-text='确定'
              cancel-button-text='我再想想'
              icon="el-icon-info"
              icon-color="red"
              title="您确定操作吗？"
              @confirm="del(scope.row.visitorId)"
          >
            <el-button type="success" slot="reference">到访<i class="el-icon-time"></i></el-button>
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
  name: "VisitorInformation",
  data() {
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      visName: "",
      house: "",
      visitorId: "",
      unit: "",
      visStatus:"",
      multipleSelection: [],
      ser_house:"",
      ser_unit:""
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      //不想改需求了....直接弹出提示框吧
      if (this.ser_house !=="" || this.ser_unit !==""){
        this.$message.error("暂不支持精确楼栋与寝室号的查找")
        return
      }
      this.request.get("/vis/getAll", {
        params: {
          visName: this.visName,
          visHelthCode: this.ser_house,
          visType: this.ser_unit,
        }
      }).then(res => {
        console.log(res)
        this.tableData = res.data
        this.total = res.total

      })
    },
    del(id) {
      this.request.post("/vis/upDelete/" + id).then(res => {
        if (res.status == 201) {
          this.$message.success("操作成功")
          this.load()
        } else {
          this.$message.error(res.msg);
        }
      })
    },
    delBatch() {
      let ids = this.multipleSelection.map(v => v.visitorId)  // [{}, {}, {}] => [1,2,3]
      this.request.post("/vis/upDelete/batch", ids).then(res => {
        if (res) {
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
      this.visName = ""
      this.ser_house = ""
      this.ser_unit = ""
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
