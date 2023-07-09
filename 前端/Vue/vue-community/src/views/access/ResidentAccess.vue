<!--寝室报修-->
<template>
  <el-row>
    <el-col :span="12">
      <el-form ref="form" :model="form" label-width="80px" label-position="top">
        <el-form-item label="姓名">
          <el-input v-model="form.resName"></el-input>
        </el-form-item>
        <el-form-item label="联系电话">
          <el-input v-model="form.resPhone"></el-input>
        </el-form-item>
        <el-form-item label="校区">
          <el-select v-model="form.name" placeholder="请选择校区">
            <el-option label="？？？" value="xxx校区"></el-option>
            <el-option label="？？？" value="xxx校区"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="楼栋">
          <el-input-number v-model="form.house" controls-position="right" :min="1"></el-input-number>
        </el-form-item>
        <el-form-item label="宿舍号">
          <el-input v-model="form.unit"></el-input>
        </el-form-item>
        <el-form-item label="情况说明">
          <el-input v-model="form.infoRemark"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">提交报修单</el-button>
          <el-button type="warning" @click="reset">重置</el-button>
        </el-form-item>
      </el-form>


    </el-col>

    <el-col :span="12">
      <h1>寝室维修管理--管理员须知</h1>
          <ul>
            <li>如有寝室设施损坏或出现其他问题，请及时向宿舍管理员报告，管理员将根据实际情况安排维修人员进行维修</li>
            <li>如需更换或购买新的设备或物品，请提前向超级管理员提交申请并等待批准</li>
            <li>管理员需要对寝室设施和设备进行定期检查和维护，及时处理学生的维修和报修请求，保障学生的住宿条件</li>
            <li>管理员需要负责寝室设备的维修和保养，并及时更新设备和物品。</li>
            <li>学生应当保管好自己的个人隐私，不得将个人信息泄露给他人。</li>
            <li>管理员应当对宿舍内的设施和设备进行定期检查和维护，及时处理学生的维修和报修请求。</li>
            <li>管理员需要保护学生的隐私和个人信息，不得将学生的信息泄露给其他人员。</li>

          </ul>
    </el-col>

  </el-row>


</template>

<script>
export default {
  name: 'ResidentAccess',
  data() {
    return {
      form: {
        resName: '',
        resPhone: '',
        infoRemark: '',
        name: '',
        house: '',
        unit: '',
        desc: ''
      }
    }
  },
  methods: {
    onSubmit() {
      this.request.post("/residentInfo/addResidentInfo", this.form).then(res => {
        if (res.status == 201) {
          this.$message.success("添加成功")
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    reset() {
      this.form = {
        resName: '',
        resPhone: '',
        infoRemark: '',
        name: '',
        house: '',
        unit: '',
        desc: ''
      }
    },
  }
}
</script>

<style scoped>
.el-input {
  width: 300px;
}
h1 { text-align: center; color: #333; font-size: 32px; font-weight: bold; margin-bottom: 20px; }

p, ul {
  font-size: 18px;
  line-height: 1.5;
  margin-left: 20px;
}

ul li {
  margin-bottom: 10px;
}

li:before {
  content: "●";
  color: #f44336;
  display: inline-block;
  font-size: 20px;
  margin-right: 10px;
  vertical-align: middle;
}

</style>
