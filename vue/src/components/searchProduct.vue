<template>
  <div class="page-box">
    <!-- Top Navigation Bar -->
    <div class="top-nav">
      <div class="center">
        <div class="left" @click="$router.push('/register')">
          <span>请登录</span>
          <span>免费注册</span>
        </div>
      </div>
    </div>

    <!-- Main Header -->
    <header class="main-hd">
      <div class="center flex-between">
        <div class="logo-box" @click="$router.push('/')" style="cursor:pointer">
          <h1>SHOPPING</h1>
          <span>商城</span>
        </div>
        <div class="search-box">
          <div class="search-line">
            <input v-model="searchQuery" type="text"  class="search-input">
            <button class="search-btn" @click="search">搜索</button>
          </div>

        </div>
      </div>
    </header>

    <!-- Breadcrumb Navigation -->
    <div class="bread">
      <div class="center">
        <span v-for="b in breadList" :key="b">{{ b }}</span>
      </div>
    </div>

    <!-- Main Content -->
    <main class="main">
      <div class="center">
        <div class="content-card">
          <div class="cols">
            <!-- Product Images -->
            <div class="left">
  <div class="main-img">
    <img :src="selectedImage" :alt="currentProduct?.productName" >
  </div>

</div>

  <div class="right">
  <div class="info">
    <div class="price">¥{{ currentProduct?.productPrice }}</div>
    <h2 class="title">{{ currentProduct?.productName }}</h2>
    <p class="desc">{{ currentProduct?.productDescription }}</p>
    <div class="btn-group">
      <button class="buy" @click="goToProductDetail">立即购买</button>
      <button class="cart">加入购物车</button>
    </div>
    </div>
    </div>
  </div>
  </div>

        <!-- Service Guarantees -->
        <div class="service-card">

        </div>
      </div>
    </main>
  </div>
</template>

<script setup>
  import { onMounted, ref } from 'vue'
  import { useRoute, useRouter } from 'vue-router'
  
  const route  = useRoute()
  const router = useRouter()
  
  const searchQuery   = ref('')
  const products      = ref([])
  
  const breadList     = ['综合','销量','价格']
  
  const currentProduct = ref(null)
const selectedImage = ref('')

function search() {
  router.push({ path: '/searchProduct', query: { q: searchQuery.value } })
}

function goToProductDetail() {
  if (currentProduct.value) {
    router.push({
      path: '/productDetail',
      query: { 
        id: currentProduct.value.productNum  // 传递 productNum 作为 id
      }
    })
  }
}

async function getProduct() {
  console.log("start")
  try {
    const msg = route.query.msg
    searchQuery.value = msg
    
    // 模拟API调用
    // const res = await fetch(`/searchProduct?msg=${encodeURIComponent(msg)}`)
    // if (!res.ok) throw new Error(res.statusText)
    // const data = await res.json()
    
    // 测试数据
    const data = {
      products: [{
        productNum: '1',
        productPrice: '1999',
        productName: '无线降噪耳机 Pro',
        productDescription: '主动降噪，40小时续航，快速充电，Hi-Fi级音质体验',
        images: [
          'https://ns-strategy.cdn.bcebos.com/ns-strategy/upload/fc_big_pic/part-00169-3149.jpg'
        ]
      }]
    }
    products.value = data.products || []
    console.log("go")
    if (products.value.length > 0) {
      currentProduct.value = products.value[0]
      if (currentProduct.value.images && currentProduct.value.images.length > 0) {
        selectedImage.value = currentProduct.value.images[0]
      }
    }
    
  } catch (e) {
    console.error(e)
    products.value = []
  }
  }
  
  onMounted(getProduct)
  

  </script>
<style>
/* 通用工具 */
.flex-between{display:flex;align-items:center;justify-content:space-between;}
.center{max-width:1280px;margin:0 auto;padding:0 16px;}

/* 页面壳 */
.page-box{min-height:100vh;background:#fafafa;}

/* 顶部条 */
.top-nav{background:#f3f3f3;font-size:12px;color:#666;padding:4px 0;}
.top-nav .left span,
.top-nav .right span{margin-right:12px;cursor:pointer;}
.cart{color:#e53e3e;font-weight:700;}

/* 主头部 */
.main-hd{background:#fff;padding:16px 0;box-shadow:0 1px 3px rgba(0,0,0,.05);}
.logo-box{display:flex;align-items:baseline;gap:8px;}
.logo-box h1{font-size:32px;font-weight:700;color:#ffaa00;}
.logo-box span{font-size:20px;color:#ffaa00;}
.search-box{flex:1;max-width:720px;}
.search-line{display:flex;}
.search-input{flex:1;padding:10px 14px;border:1px solid #ccc;border-right:none;border-radius:4px 0 0 4px;font-size:14px;}
.search-input:focus{outline:none;border-color:#e53e3e;}
.search-btn{background:#ffaa00;color:#fff;padding:10px 24px;border:none;border-radius:0 4px 4px 0;cursor:pointer;transition:background .2s;}
.search-btn:hover{background:#be7f00;}
.hot-word{display:flex;gap:12px;margin-top:6px;font-size:12px;color:#666;}
.hot-word span{cursor:pointer;}
.hot-word span:hover{color:#e53e3e;}

/* 面包屑 */
.bread{background:#fff;border-top:1px solid #eee;padding:8px 0;}
.bread span{background:#f3f3f3;padding:4px 8px;margin-right:8px;border-radius:4px;font-size:12px;cursor:pointer;}

/* 主内容区 */
.main{padding:32px 0;}
.content-card{background:#fff;border-radius:8px;box-shadow:0 2px 8px rgba(0,0,0,.05);padding:24px;}
.cols{display:flex;gap:32px;}
.left{flex:0 0 400px;}
.main-img{background:#ffb013;border-radius:3px;padding:2px;display:flex;align-items:center;justify-content:center;}
.main-img img{max-width:100%;max-height:380px;object-fit:contain;}
.thumb-list{display:flex;gap:8px;margin-top:12px;}
.thumb{width:64px;height:64px;border:2px solid #eee;border-radius:4px;overflow:hidden;cursor:pointer;}
.thumb img{width:100%;height:100%;object-fit:cover;}
.thumb.active{border-color:#e53e3e;}

.right{flex:1;}
.price{font-size:32px;font-weight:700;color:#e53e3e;margin-bottom:12px;}
.title{font-size:20px;font-weight:600;color:#222;margin-bottom:8px;}
.desc{font-size:14px;color:#555;margin-bottom:24px;line-height:1.6;}
.btn-group{display:flex;gap:16px;}
.buy{flex:1;background:#e53e3e;color:#fff;padding:12px 0;border:none;border-radius:6px;font-size:16px;cursor:pointer;transition:background .2s;}
.buy:hover{background:#c53030;}
.cart{flex:1;background:#fff;color:#e53e3e;border:2px solid #e53e3e;padding:12px 0;border-radius:6px;font-size:16px;cursor:pointer;transition:all .2s;}
.cart:hover{background:#fee2e2;}

/* 服务卡片 */
.service-card{background:#fff;border-radius:8px;box-shadow:0 2px 8px rgba(0,0,0,.05);padding:24px;margin-top:32px;}
.service-list{display:grid;grid-template-columns:repeat(auto-fit,minmax(220px,1fr));gap:24px;}
.service-item{display:flex;align-items:center;gap:12px;}
.icon{width:48px;height:48px;border-radius:50%;background:#e53e3e;color:#fff;display:flex;align-items:center;justify-content:center;font-weight:700;font-size:20px;}
.txt h3{font-size:15px;font-weight:600;color:#222;margin-bottom:4px;}
.txt p{font-size:12px;color:#666;}
</style>