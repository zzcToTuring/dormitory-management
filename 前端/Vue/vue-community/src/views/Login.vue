<template>
  <div class="wrapper">
    <div style="margin: 200px auto; background-color: #fff; width: 350px; height: 300px; padding: 20px; border-radius: 10px">
      <div style="margin: 20px 0; text-align: center; font-size: 24px"><b>学生寝室管理系统</b></div>
        <el-form :model="user" :rules="rules" ref="userForm">
        <el-form-item prop="username">
          <el-input size="medium" style="margin: 10px 0" placeholder="账号" prefix-icon="el-icon-user" v-model="user.account"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input size="medium" style="margin: 10px 0" placeholder="密码" prefix-icon="el-icon-lock" show-password v-model="user.password"></el-input>
        </el-form-item>
        <el-form-item style="margin: 10px 0; text-align: right">
          <el-button type="primary" size="small"  autocomplete="off" @click="login">登录</el-button>
          <el-button type="warning" size="small"  autocomplete="off" @click="$router.push('/register')">注册</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import {setRoutes} from "@/router";

export default {
  name: "Login",
  data() {
    return {
      user: {},
      rules: {
        account: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 10, message: '长度在 3 到 5 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur' }
        ],
      }
    }
  },
  methods: {
    login() {
      this.$refs['userForm'].validate((valid) => {
        if (valid) {  // 表单校验合法
          this.request.post("/user/login", this.user).then(res => {
            if(res.status === 200) {
              localStorage.setItem("user", JSON.stringify(res.data))  // 存储用户信息到浏览器
              localStorage.setItem("token",JSON.stringify(res.msg))
              localStorage.setItem("menus",JSON.stringify(res.data.menus)) //存储token
              setRoutes()
              this.$router.push("/")
              this.$message.success("登录成功")
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
  overflow: hidden;
  background: linear-gradient(to right, #3A78B7, #A2C4B1, #FECB2F, #FF715B);
  animation: gradientBackground 10s ease infinite;
}

@keyframes gradientBackground {
  0% {
    background-position: 0% 50%;
  }
  25% {
    background-position: 100% 50%;
  }
  50% {
    background-position: 100% 50%;
  }
  75% {
    background-position: 0% 50%;
  }
  100% {
    background-position: 0% 50%;
  }
}



</style>
