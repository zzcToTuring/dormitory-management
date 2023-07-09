<template>

</template>

<script>
import router from "@/router";

export default {
  name: "Error",
  created() {
    this.$alert('网址输入有误 或 您的权限不足','提示',{
        confirmButtonText:'确定'
        }).then((response) =>{
          this.$router.replace({path:"/home"})
    })
  }
}
</script>

<style scoped>

</style>
