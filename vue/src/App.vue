<template>
  <div id="app">
    <!-- 导航示例 -->
    <nav>
      <router-link to="/">首页</router-link> |
      <router-link to="/searchProduct">搜索</router-link> |
      <router-link to="/productDetail">商品详情页</router-link>|
      <router-link to="/order">支付页面</router-link>|
      <router-link to="/account">我的账户</router-link>|
      <router-link to="/register">登录/注册</router-link>|
      <router-link to="/help">帮助中心</router-link>|
      <router-link to="/shoppingCar">购物车</router-link>|
      <router-link to="/sellerRegister">商家登录/注册</router-link>|
      <router-link to="/sellerPage">商家后台</router-link> 
    </nav>

    <!-- 页面组件渲染出口 -->
    <router-view />
  </div>
</template>

<script>






export default {
  name: 'App',
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
