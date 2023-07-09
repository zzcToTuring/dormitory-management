<!--信息管理-寝室信息管理-->
<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入校区名" suffix-icon="el-icon-search" v-model="name"></el-input>
      <el-input style="width: 200px" placeholder="请输入楼栋" suffix-icon="el-icon-message" class="ml-5" v-model="house"></el-input>
      <el-input style="width: 200px" placeholder="请输入宿舍号" suffix-icon="el-icon-position" class="ml-5" v-model="unit"></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button type="warning" @click="reset">重置</el-button>
    </div>

    <div style="margin: 10px 0">
      <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
    </div>

    <el-table :data="tableData.slice((pageNum-1)*pageSize,pageNum*pageSize)" style="width: 100%" border stripe :header-cell-class-name="'headerBg'"  @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" ></el-table-column>
      <el-table-column prop="addressId" label="寝室编号" width="70" align="center"></el-table-column>
      <el-table-column prop="name" label="校区名" width="100" align="center"></el-table-column>
      <el-table-column prop="house" label="楼栋" width="80" align="center"></el-table-column>
      <el-table-column prop="unit" label="宿舍号" align="center"></el-table-column>
      <el-table-column prop="residentCount" label="寝室学生数" align="center"></el-table-column>
      <el-table-column prop="createTime" label="时间" align="center"></el-table-column>
      <el-table-column prop="addressStatus" label="状态" align="center">
        <template slot-scope="scope">
          <p v-if="scope.row.addressStatus === 0">正常寝室</p>
          <p v-if="scope.row.addressStatus === 1">暂未开启</p>
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
              title="您确定删除吗？"
              @confirm="del(scope.row.id)"
          >
            <el-button type="danger" slot="reference" :disabled="scope.row.residentCount !== 0" >
              删除 <i class="el-icon-remove-outline"></i></el-button>
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

    <el-dialog title="添加学生寝室" :visible.sync="dialogFormVisible" width="30%" >
      <el-form label-width="80px" size="small">
        <el-form-item label="校区名称">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="楼栋">
          <el-input v-model="form.house" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="宿舍号">
          <el-input v-model="form.unit" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>


  </div>
</template>
<script>

export default {
  name: "Address",
  data() {
    return {
      tableData: [],
      total: 0,
      name: "",
      house: "",
      createTime: "",
      residentCount: "",
      addressStatus: "",
      unit: "",
      form: {},
      dialogFormVisible: false,
      dialogForm: false,
      multipleSelection: [],
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      this.request.get("/address/getAll")
       .then(res => {
        console.log(res)
        this.tableData = res.data
        this.total = res.total

      })
    },
    save() {
      this.request.post("/address/addAddress", this.form).then(res => {
        if (res.status == 201) {
          this.$message.success("添加成功")
          this.load()
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    handleAdd() {
      this.dialogFormVisible = true
      this.form = {}
      this.form.resSex = "1"
    },
    del(id) {
      this.request.delete("/user/" + id).then(res => {
        if (res.data) {
          this.$message.success("操作成功")
        } else {
          this.$message.error(res.msg);
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
      this.pageNum = 2;
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
</style>
