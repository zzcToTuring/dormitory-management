<template>
  <el-card style="width: 500px;">

    <el-form label-width="80px" size="small">
      <el-form-item label="用户名">
        <el-input disabled  v-model="form.account" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="旧密码">
        <el-input   v-model="form.password" show-password autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="新密码">
        <el-input   v-model="form.newPassword" show-password autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="确认密码">
        <el-input   v-model="form.confirmPassword" show-password autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="save">确 定</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script>
export default {
  name: "EditPwd",
  data(){
    return{
      form:{},
      newPassword:'',
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},

    }
  },
  created(){
    this.getUser().then(res => {
      this.form = res
    })

  },
  methods:{
    async getUser() {
      return (await  this.request.get("/user/getUser/"+this.user.account)).data
    },
    save(){
      this.request.post("/user/editPwd", this.form).then(res => {
        if(res.status == 201) {
          this.$message.success("修改成功")
          this.request.post("/user/loginout",this.user.account).then(res => {
            if (res.status == 200) {
              this.$router.push("/login")
              localStorage.removeItem("user")
              localStorage.removeItem("token")
              localStorage.removeItem("menus")
            } else {
              this.$message.error(res.msg);
            }
          })
        }else{
          this.$message.error(res.msg)
        }
      })
    }
  }

}
</script>

<style scoped>

</style>