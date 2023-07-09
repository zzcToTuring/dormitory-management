<template>
  <div style="line-height: 60px; display: flex">
    <div style="flex: 1;">
      <span :class="collapseBtnClass" style="cursor: pointer; font-size: 18px"></span>
      <el-breadcrumb separator="/" style="display: inline-block; margin-left: 10px">
        <el-breadcrumb-item :to="'/'">首页</el-breadcrumb-item>
        <el-breadcrumb-item>{{ ??? }}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <el-dropdown style="width: 150px; cursor: pointer">
      <div style="display: inline-block">
        <img  :src="user.head"
             style="width: 50px; height: 40px; border-radius: 50%; position: relative; top: 10px; right: 5px">
        <span>{{user.account}}</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
      </div>
      <el-dropdown-menu slot="dropdown" style="width: 100px; text-align: center">
        <el-dropdown-item style="font-size: 14px; padding: 5px 0">
          <router-link to="/person">???</router-link>
        </el-dropdown-item>
        <el-dropdown-item style="font-size: 14px; padding: 5px 0">
          <router-link to="/editPwd">???</router-link>
        </el-dropdown-item>
        <el-dropdown-item style="font-size: 14px; padding: 5px 0">
          <span style="text-decoration: none" @click="logout">退出</span>
        </el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
export default {
  name: "Header",
  props: {
    collapseBtnClass: String,
    collapse: Boolean,
  },
  computed: {
      currentPathName () {
        return this.$store.state.currentPathName;　　//需要监听的数据
      }
  },
  methods: {
    logout() {
      this.request.post("/user/loginout",this.user.account).then(res => {
        if (res.status == 200) {
          this.$router.push("/login")
          localStorage.removeItem("user")
          localStorage.removeItem("token")
          localStorage.removeItem("menus")
          this.$message.success(res.data)
        } else {
          this.$message.error(res.msg);
        }
      })

    }
  },
  data() {
    return {
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  }
}
</script>

<style scoped>

</style>