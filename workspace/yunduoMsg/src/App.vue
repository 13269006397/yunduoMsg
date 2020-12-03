<template>
  <div id="app">
    <!-- 路由占位符 -->
    <router-view></router-view>
  </div>
</template>
<script>
export default {
  name: "app"
};
</script>
<style>
  body{
    font-family: "微软雅黑";  /*  设置字体 */
    margin: 0px auto /* 去除上下的边距*/
  }
</style>
