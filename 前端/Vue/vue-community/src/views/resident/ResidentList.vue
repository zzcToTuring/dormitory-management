<!--普通学生界面-->
<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入学生姓名" suffix-icon="el-icon-search" v-model="resName"></el-input>
      <el-input style="width: 200px" placeholder="请输入楼栋" suffix-icon="el-icon-message" class="ml-5" v-model="house"></el-input>
      <el-input style="width: 200px" placeholder="请输入宿舍号" suffix-icon="el-icon-position" class="ml-5" v-model="unit"></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button type="warning" @click="reset">重置</el-button>
    </div>

    <div style="margin: 10px 0">
      <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
      <el-popconfirm
          class="ml-5"
          confirm-button-text='确定'
          cancel-button-text='我再想想'
          icon="el-icon-info"
          icon-color="red"
          title="您确定批量删除这些数据吗？"
          @confirm="delBatch"
      >
        <el-button type="danger" slot="reference">批量删除 <i class="el-icon-remove-outline"></i></el-button>
      </el-popconfirm>
      <el-button type="primary" @click="exp" class="ml-5">导出 <i class="el-icon-top"></i></el-button>
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
      <el-table-column prop="resIntotime" label="入住时间" align="center" width="100"></el-table-column>
      <el-table-column prop="resRemarks" label="学生备注" width="200" align="center"></el-table-column>
      <el-table-column prop="resStatus" label="状态" width="100" align="center">
        <template slot-scope="scope">
          <p v-if="scope.row.resStatus === 0">正常</p>
          <p v-if="scope.row.resStatus === 1">注销</p>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button type="success" @click="handleEdit(scope.row)">编辑<i class="el-icon-edit"></i></el-button>
          <el-popconfirm
              class="ml-5"
              confirm-button-text='确定'
              cancel-button-text='我再想想'
              icon="el-icon-info"
              icon-color="red"
              title="您确定删除吗？"
              @confirm="del(scope.row.resId)"
          >
            <el-button type="danger" slot="reference">注销 <i class="el-icon-remove-outline"></i></el-button>
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
    <!--点击添加之后的弹框-->
    <el-dialog title="学生入住办理" :visible.sync="dialogFormVisible" width="30%" >
      <el-form label-width="80px" size="small">
        <el-form-item label="学号">
          <el-input v-model="xxx" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="xxx" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio v-model="xxx" label="1">男</el-radio>
          <el-radio v-model="xxx" label="0">女</el-radio>
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="xxx" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="学生标记">
        <el-select v-model="xxx" placeholder="请选择">
          <el-option
              v-for="item in options"
              :value="item.value">
          </el-option>
        </el-select>
        </el-form-item>
        <el-form-item label="备注信息">
          <el-input v-model="form.resRemarks" autocomplete="off"></el-input>
        </el-form-item>
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


    <el-dialog title="修改住户信息" :visible.sync="dialogForm" width="30%" >
      <el-form label-width="80px" size="small">
        <el-form-item label="性别">
          <el-input v-model="form.resName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="姓名">
          <el-radio v-model="form.resSex " label="1">男</el-radio>
          <el-radio v-model="form.resSex " label="0">女</el-radio>
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="form.resPhone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="学生标记">
          <el-select v-model="form.resState" placeholder="请选择">
            <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注信息">
          <el-input v-model="form.resRemarks" autocomplete="off"></el-input>
        </el-form-item>
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
        <el-button @click="dialogForm = false">取 消</el-button>
        <el-button type="primary" @click="edit">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>
<script>

export default {
  name: "ResidentList",
  data() {
    return {
      tableData: [],
      resId:"",
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
      form: {},
      dialogFormVisible: false,
      dialogForm: false,
      multipleSelection: [],
      options: [{
        value: 0,
        label: '正常学生'
      }
      ],
    }
  },
  methods: {
    load() {
      this.request.get("/resident/?", {
        params: {
          resName: this.resName,
          house: this.house,
          unit: this.unit,
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
        return ((x < y) ? -1 : (x > y) ? 0 : 0)
      })
    },
    save() {
      this.request.post("/resident/addRes", this.form).then(res => {
        if (res.status == 201) {
          this.$message.success("保存成功")
          this.dialogFormVisible = false
          this.load()
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    handleAdd() {
      this.dialogFormVisible = true
      this.form = {
        // resSex: '1'
      }
      this.$set(this.form, 'resSex', '1')
      // this.form.resSex = "1"
    },
    edit(){
      this.request.post("/resident/edit").then(res => {
        if (res.status == 201) {
          this.$message.success("修改成功")
          this.dialogForm = false
          this.load()
        } else {
          this.$message.error(res.msg)
        }
      })

    },
    handleEdit(row) {
      console.log(row)
      this.form = row
      this.dialogForm = true
      if (row.resSex === 1){
        this.form.resSex = "1"
      }
      else {
        this.form.resSex = "0"
      }

    },
    del(id) {
      this.request.delete("/resident/upDelete/" + id).then(res => {
        if (res.status == 201) {
          this.$message.success("删除成功")
          this.load()
        } else {
          this.$message.error(res.msg);
        }
      })
    },
    delBatch() {
      let ids = this.multipleSelection.map(v => v.resId)  // [{}, {}, {}] => [1,2,3]
      this.request.post("/resident/upDelete/batch", ids).then(res => {
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
    handleSizeChange(val) {
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
      window.open("http://localhost:8080/resident/export")
    },

  }
}
</script>


<style>
.headerBg {
  background: #eee!important;
}
</style>
