<template>
  <div class="page-container">
    <!-- Top Header -->
    <div class="top-bar">
      <div class="center-wrap">
        <div class="left-links">
          <span @click="$router.push('/register')">免费注册</span>
        </div>
        <div class="right-links">
          <span @click="$router.push('/register')">你好，请登录</span>|
          <span @click="$router.push('/help')" style="margin-left: 20px;">帮助中心</span>
        </div>
      </div>
    </div>

    <!-- Main Header -->
    <div class="main-header">
      <div class="center-wrap flex-between">
        <!-- Logo -->
        <div class="logo-area">
          <div class="logo-text">
            SHOPPING
            <small>商城</small>
          </div>
          <div class="logo-circle"></div>
        </div>

        <!-- Search Bar -->
        <div class="search-area">
          <div class="search-box">
            <input v-model="searchQuery" type="text" @keyup.enter="search" placeholder="搜索  商品/品牌/店铺" class="search-input">
            <button @click="search" class="search-btn">搜索</button>
          </div>

        </div>
      </div>
    </div>

    <!-- Navigation Menu -->


    <!-- Main Content -->
    <div class="content">
      <div class="center-wrap flex-between">
        <!-- Left Sidebar -->
        <div class="side-bar">
          <div v-for="category in categories" :key="category.name" class="side-item">
            <span class="icon">■</span><span>{{ category.name }}</span>
          </div>
        </div>

        <!-- Main Banner Area -->
        <div class="banner-area">
          <div class="banner-inner">
            <!-- Phone Images -->
            <div class="phone-mockups">
              <div class="phone phone-left"></div>
              <div class="phone phone-center"></div>
              <div class="phone phone-right"></div>
            </div>
            <!-- Product Info -->
            <div class="product-info">
              <h1>HUAWEI P20 系列</h1>
              <p class="sub-title">4000万徕卡三摄 | AI摄影大师</p>
              <p class="slogan">新品上市 震撼开售</p>
              <div class="price-row">
                <span class="price-tag">¥3788起</span>
                <button  class="buy-btn">立即抢购  ></button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const searchQuery = ref('')

const router = useRouter();
function search(){
// 将 sMsg 的值作为查询参数传递到 /search 路由
    router.push({ 
        path: '/searchProduct', query: { msg: searchQuery.value }
    });
}


const categories = ref([
  { name: '女装/大衣' },
  { name: '男装/运动户外' },
  { name: '女鞋/男鞋/箱包' },
  { name: '美妆/个人护理', },
  { name: '腕表/眼镜/珠宝饰品' },
  { name: '手机/数码/电脑办公' },
  { name: '母婴玩具',},
  { name: '零食/茶酒/进口食品'},
  { name: '生鲜水果'},
  { name: '大家电/生活电器'},
  { name: '家居建材' },
  { name: '汽车/配件/用品'},
  { name: '家纺/家饰/鲜花'},
  { name: '医药保健'},
  { name: '厨具/收纳/宠物' },
  { name: '图书音像'}
])
</script>

<style scoped>
/* 通用工具 */
.flex-between{
  display: flex;
  align-items: center;
  justify-content: space-between;
}
.center-wrap{
  max-width: 1280px;
  margin: 0 auto;
  padding: 0 16px;
}

/* 页面容器 */
.page-container{ min-height: 100vh; background: #f7f7f7; }

/* 顶部条 */
.top-bar{ background: #f3f3f3; font-size: 12px; color: #666; padding: 4px 0; }
.top-bar .center-wrap{ display: flex; justify-content: space-between; }
.left-links span,
.right-links span{ margin-right: 12px; cursor: pointer; }
.red{ color: #e53e3e; }

/* 主头部 */
.main-header{ background: #fff; padding: 16px 0; box-shadow: 0 1px 3px rgba(0,0,0,.05); }
.logo-area{ display: flex; align-items: center; gap: 32px; }
.logo-text{ font-size: 36px; font-weight: 700; color: #ffaa00; line-height: 1; }
.logo-text small{ display: block; font-size: 20px; font-weight: 400; color: #ffaa00; margin-top: 2px; }
.logo-circle{ width: 48px; height: 48px; background: #000; border-radius: 50%; }

/* 搜索区 */
.search-area{ flex: 1; max-width: 720px; }
.search-box{ display: flex; }
.search-input{ flex: 1; padding: 12px 16px; border: 1px solid #ccc; border-right: none; border-radius: 4px 0 0 4px; font-size: 14px; }
.search-input:focus{ outline: none; border-color: #e53e3e; }
.search-btn{ background: #ffaa00; color: #fff; padding: 12px 32px; border: none; border-radius: 0 4px 4px 0; cursor: pointer; transition: background .2s; }
.search-btn:hover{ background: #be7f00; }
.hot-words{ display: flex; gap: 12px; margin-top: 8px; font-size: 13px; color: #666; }
.hot-words span{ cursor: pointer; }
.hot-words span:hover{ color: #e53e3e; }

/* 导航条 */
.nav-wrap{ background: #fff; border-top: 1px solid #eee; }
.category-btn{ background: #e53e3e; color: #fff; padding: 10px 24px; display: flex; align-items: center; gap: 8px; cursor: pointer; font-size: 14px; }
.category-btn:hover{ background: #c53030; }
.icon{ font-size: 18px; }
.nav-list{ display: flex; gap: 32px; font-size: 14px; color: #333; }
.nav-list span{ cursor: pointer; padding: 10px 0; }
.nav-list span:hover{ color: #e53e3e; }

/* 主内容区 */
.content{ padding: 16px 0; }
.side-bar{ width: 240px; background: #fff; border-radius: 8px; box-shadow: 0 1px 3px rgba(0,0,0,.05); padding: 16px; }
.side-item{ display: flex; align-items: center; gap: 12px; padding: 8px; font-size: 14px; color: #333; cursor: pointer; border-radius: 4px; }
.side-item:hover{ background: #fafafa; }
.side-item .icon{ color: #999; }

/* 横幅区域 */
.banner-area{ flex: 1; background: linear-gradient(90deg, #4c1d95 0%, #1e3a8a 100%); border-radius: 8px; overflow: hidden; position: relative; height: 384px; }
.banner-inner{ display: flex; align-items: center; height: 100%; padding: 0 32px; }
.phone-mockups{ flex: 1; display: flex; justify-content: center; align-items: center; position: relative; height: 100%; }
.phone{ width: 192px; height: 320px; border-radius: 24px; box-shadow: 0 20px 25px -5px rgba(0,0,0,.2); position: absolute; }
.phone-left{ background: linear-gradient(180deg, #c084fc 0%, #60a5fa 100%); transform: rotate(12deg); left: 0; z-index: 1; }
.phone-center{ background: linear-gradient(180deg, #f9a8d4 0%, #fcd34d 100%); z-index: 3; }
.phone-right{ background: linear-gradient(180deg, #93c5fd 0%, #5eead4 100%); transform: rotate(-6deg); right: 0; z-index: 2; }

/* 文案区 */
.product-info{ flex: 1; color: #fff; padding-left: 32px; }
.product-info h1{ font-size: 36px; font-weight: 700; margin-bottom: 8px; }
.sub-title{ font-size: 18px; opacity: .9; margin-bottom: 16px; }
.slogan{ font-size: 28px; font-weight: 700; margin-bottom: 24px; }
.price-row{ display: flex; align-items: center; gap: 16px; }
.price-tag{ background: linear-gradient(90deg, #2dd4bf 0%, #3b82f6 100%); padding: 6px 16px; border-radius: 999px; font-size: 18px; font-weight: 700; }
.buy-btn{ background: linear-gradient(90deg, #a855f7 0%, #ec4899 100%); border: none; padding: 10px 32px; border-radius: 999px; color: #fff; font-size: 16px; cursor: pointer; transition: filter .2s; }
.buy-btn:hover{ filter: brightness(1.1); }
</style>