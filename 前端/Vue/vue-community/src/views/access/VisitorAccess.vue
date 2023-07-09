<!--来访登记-->
<template>
  <el-row>
    <el-col :span="12">
      <el-form ref="form" :model="form" label-width="80px" label-position="top">
        <el-form-item label="姓名">
          <el-input v-model="form.visName"></el-input>
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="form.visPhone"></el-input>
        </el-form-item>
        <el-form-item label="访客地址">
          <el-input v-model="form.visAddress" ></el-input>
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
        <el-form-item label="原由">
          <el-input v-model="form.visRemark"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">立即申请</el-button>
        </el-form-item>
      </el-form>
    </el-col>
      <el-col :span="12">
        <h1>来访人员登记--管理员须知</h1>
        <ul>
          <li>为了保障学生的住宿安全，访客进出必须经过学生的同意和宿舍管理员的审核。访客进入宿舍需要出示有效证件，管理员应对其进行查阅</li>
          <li>任何未经授权的人员不得进入宿舍，包括学生的亲友和其他未经审核的访客。</li>
          <li>学生应当保管好自己的房门钥匙和门卡，防止丢失和泄露。</li>
          <li>学生需要遵守宿舍管理规定，保持宿舍内的卫生和安全，不得在宿舍内存储危险品和违禁物品。如有发现违规行为，应及时向宿舍管理员报告。</li>
          <li>学生应当保管好自己的个人隐私，不得将个人信息泄露给他人。</li>
          <li>学生应当保管好自己的财物和重要物品，不得将其放置在公共区域或随意放置在宿舍内。</li>
          <li>管理员应当对宿舍内的设施和设备进行定期检查和维护，及时处理学生的维修和报修请求。</li>
          <li>管理员需要保护学生的隐私和个人信息，不得将学生的信息泄露给其他人员。</li>

        </ul>
      </el-col>
  </el-row>



</template>

<script>
export default {
  name:'VisitorAccess',
  data() {
    return {
      form: {
        visName: '',
        visPhone: '',
        visAddress: '',
        visRemark:'',
        name:'',
        house:'',
        unit:'',
      }
    }
  },
  methods: {
    onSubmit() {
      this.request.post("/vis/addVis", this.form).then(res => {
        if (res.status == 201) {
          this.$message.success("提交成功")
        } else {
          this.$message.error(res.msg)
        }
      })
    }
  }
}
</script>

<style scoped>
.el-input{
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
