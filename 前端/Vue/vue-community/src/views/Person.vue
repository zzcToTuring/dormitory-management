<template>
<el-card style="width: 500px;">

  <el-upload
      class="avatar-uploader"
      action="http://localhost:8088/file/upload"
      :show-file-list="false"
      :on-success="handleAvatarSuccess"
      >
    <img v-if="form.head" :src="form.head" class="avatar">
    <i v-else class="el-icon-plus avatar-uploader-icon"></i>
  </el-upload>

  <el-form label-width="80px" size="small">
    <el-form-item label="用户名">
      <el-input disabled  v-model="form.account" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="角色">
      <el-input disabled  v-model="form.roleName =='admin' ? '管理员':'用户'" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="姓名">
      <el-input v-model="form.userName" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="电话">
      <el-input v-model="form.userPhone" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="邮箱">
      <el-input v-model="form.userEmail" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="地址">
      <el-input type="textarea" v-model="form.userAddress" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="save">确 定</el-button>
    </el-form-item>
  </el-form>
</el-card>
</template>

<script>
export default {
  name: "Person",
  data(){

    return{
      userAddress:"",
      userEmail:"",
      form:{

      },
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}

    }
  },
  created(){
    this.getUser().then(res => {
      console.log(res)
      this.form = res
    })

  },
  methods:{
    async getUser() {
      return (await  this.request.get("/user/getUser/"+this.user.account)).data
    },
    save() {
      this.request.post("/user/edit", this.form).then(res => {
        if(res.status == 201) {
          this.$message.success("修改成功")
          this.getUser().then(res => {
            res.token = JSON.parse(localStorage.getItem("user")).token
            localStorage.setItem("user", JSON.stringify(res))
          })
          this.load()
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    handleAvatarSuccess(res){
      this.form.head =res
    }
  }
}
</script>

<style>
.avatar-uploader{
  text-align: center;
  padding-bottom: 10px;
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 150px;
  height: 150px;
  line-height: 150px;
  text-align: center;
}
.avatar {
  width: 150px;
  height: 150px;
  display: block;
}

</style>