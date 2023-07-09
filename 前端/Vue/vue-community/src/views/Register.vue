<template>
  <div class="wrapper">
    <div style="margin: 100px auto; background-color: #fff; width: 350px; height: 550px; padding: 20px; border-radius: 10px">
      <div style="margin: 20px 0; text-align: center; font-size: 24px"><b>注 册</b></div>
      <el-form :model="user" :rules="rules" ref="userForm">
        <el-form-item prop="account">
          <el-input placeholder="请输入账号" size="medium" style="margin: 5px 0" prefix-icon="el-icon-user" v-model="user.account"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input placeholder="请输入密码" size="medium" style="margin: 5px 0" prefix-icon="el-icon-lock" show-password v-model="user.password"></el-input>
        </el-form-item>
        <el-form-item prop="confirmPassword">
          <el-input placeholder="请确认密码" size="medium" style="margin: 5px 0" prefix-icon="el-icon-lock" show-password v-model="user.confirmPassword"></el-input>
        </el-form-item>
        <el-form-item prop="userName">
          <el-input placeholder="请输入名字" size="medium" style="margin: 5px 0" prefix-icon="el-icon-lock"  v-model="user.userName"></el-input>
        </el-form-item>
        <el-form-item prop="userPhone">
          <el-input placeholder="请输入电话" size="medium" style="margin: 5px 0" prefix-icon="el-icon-lock"  v-model="user.userPhone"></el-input>
        </el-form-item>
        <el-form-item prop="codeText">
          <el-input placeholder="请输入系统管理员的授权码" size="medium" style="margin: 5px 0" prefix-icon="el-icon-lock" v-model="user.codeText"></el-input>
        </el-form-item>
        <el-form-item style="margin: 5px 0; text-align: right">
          <el-button type="primary" size="small"  autocomplete="off" @click="reg">注册</el-button>
          <el-button type="warning" size="small"  autocomplete="off" @click="$router.push('/login')">返回登录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: "Register",
  data() {
    return {
      user: {},
      rules: {
        account: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 1, max: 10, message: '长度在 3 到 5 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur' }
        ],
        userPhone: [
          { required: true, message: '请输入电话', trigger: 'blur' },
          { min: 1, max: 11, message: '长度在 1 到 11 个字符', trigger: 'blur' }
        ],
        userName: [
          { required: true, message: '请输入姓名', trigger: 'blur' },
          { min: 1, max: 4, message: '长度在 2 到 4 个字符', trigger: 'blur' }
        ],
        codeText: [
          { required: true, message: '请输入注册码', trigger: 'blur' }
        ],
      }
    }
  },
  methods: {
    reg() {
      this.$refs['userForm'].validate((valid) => {
        if (valid) {  // 表单校验合法
          if (this.user.password !== this.user.confirmPassword) {
            this.$message.error("两次输入的密码不一致")
            return false
          }
          this.request.post("/user/reg", this.user).then(res => {
            if(res.status === 201) {
              this.$message.success("注册成功")
              this.$router.push("/login")
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      });
    }
  }
}
</script>

<style>
.wrapper {
  height: 100vh;
  background-image: linear-gradient(45deg, #0081ff, #1cbbb4);
  overflow: hidden;
}
</style>
