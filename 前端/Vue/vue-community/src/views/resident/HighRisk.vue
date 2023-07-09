<!--标记学生-->
<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入姓名" suffix-icon="el-icon-search" v-model="resName"></el-input>
      <el-input style="width: 200px" placeholder="请输入楼号" suffix-icon="el-icon-message" class="ml-5" v-model="house"></el-input>
      <el-input style="width: 200px" placeholder="请输入单元号" suffix-icon="el-icon-position" class="ml-5" v-model="unit"></el-input>
      <!--此处的搜索可能会出现无法刷新的bug，原因是后台已经返回了，但框架没有及时更新，因此失效，遇到此情况刷新即可，后端也会返回数据-->
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button type="warning" @click="reset">重置</el-button>
    </div>

    <div style="margin: 10px 0">
      <el-upload action="http://localhost:9090/api/user/import" :show-file-list="false" accept="xlsx" :on-success="handleExcelImportSuccess" style="display: inline-block">
        <el-button type="primary" class="ml-5" disabled>导入 <i class="el-icon-bottom"></i></el-button>
      </el-upload>
      <el-button type="primary" @click="exp" class="ml-5" >导出 <i class="el-icon-top"></i></el-button>
    </div>

    <el-table :data="tableData.slice((pageNum-1)*pageSize,pageNum*pageSize)" style="width: 100%" border stripe :header-cell-class-name="'headerBg'"  @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="40" align="center"></el-table-column>
      <el-table-column prop="resId" label="学号" width="70" align="center"></el-table-column>
      <el-table-column prop="resName" label="姓名" width="80" align="center"></el-table-column>
      <el-table-column prop="resSex" label="性别" width="55" align="center">
        <template slot-scope="scope">
          <p v-if="scope.row.resSex === 0">女</p>
          <p v-else>男</p>
        </template>
      </el-table-column>
      <el-table-column prop="resPhone" label="电话" width="100" align="center"></el-table-column>
      <el-table-column prop="name" label="校区" width="120" align="center"></el-table-column>
      <el-table-column prop="house" label="楼栋" width="65" align="center"></el-table-column>
      <el-table-column prop="unit" label="宿舍号" width="70" align="center"></el-table-column>
      <el-table-column prop="residentCount" label="寝室人数" align="center" width="80"></el-table-column>
      <el-table-column prop="resRemarks" label="学生备注" width="240" align="center"></el-table-column>
      <el-table-column prop="resState" label="标记类型" width="140" align="center" >
        <template slot-scope="scope">
          <p v-if="scope.row.resState === 1">晚归</p>
          <p v-if="scope.row.resState === 2">请假</p>
          <p v-if="scope.row.resState === 3">短期外宿</p>
          <p v-if="scope.row.resState === 4">多次违纪</p>
          <p v-if="scope.row.resState === 5">其他情况</p>
          <p v-if="scope.row.resState === 6">考察期</p>
        </template>
      </el-table-column>
      <el-table-column label="操作"  align="center">
        <template slot-scope="scope">
          <el-popconfirm
              class="ml-5"
              confirm-button-text='确定'
              cancel-button-text='我再想想'
              icon="el-icon-info"
              icon-color="red"
              title="确定吗？当学生存在多次违纪记录，不能立即解除"
              @confirm="edit(scope.row.resId)"
          >
            <el-button type="success" slot="reference">解除<i class="el-icon-upload2"></i></el-button>
          </el-popconfirm>
          <template>
            <el-popconfirm
                class="ml-5"
                confirm-button-text='确定'
                cancel-button-text='我再想想'
                icon="el-icon-info"
                icon-color="red"
                title="确定注销吗？"
                @confirm="del(scope.row.resId)"
            >
              <el-button type="danger"  slot="reference" >注销<i class="el-icon-remove-outline"></i></el-button>
            </el-popconfirm>
          </template>
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
  name: "HighRisk",
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
      resState: "",
      dialogForm: false,
      multipleSelection: [],
    }
  },
  created() {
    this.load()
  },
  methods: {
    focus() {

    },
    load() {
      this.request.get("/resident/getState",{
        params:{
          resName:this.resName,
          house:this.house,
          unit:this.unit
        }
      }).then(res => {
        console.log("获取的数据",res)
        this.tableData = res.data
        this.total = res.total
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
    del(id) {
      this.request.delete("/resident/upDelete/" + id).then(res => {
        if (res.status === 201) {
          this.$message.success("删除成功")
          this.load()
        } else {
          this.$message.error(res.msg);
        }
      })
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
    edit(id){
      this.request.post("/resident/editResState/" + id).then(res => {
        if (res.status == 201) {
          this.$message.success("操作成功")
          this.dialogForm = false
          this.load()
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    exp() {
      this.$message({
        message: '请于普通学生界面进行导出操作',
        type: 'warning',
      });
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
