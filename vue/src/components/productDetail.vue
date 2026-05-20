<template>
  <div class="page-container">
    <!-- 左侧可滚动区域 -->
    <div class="left-section">
      <!-- 顶部店铺信息 -->
      <div class="header">
        <div class="logo">
          <span class="taobao-logo">商城</span>
        </div>
        <div class="store-info">
          <div class="store-avatar">
            <img :src="productData?.storeAvatar || 'https://via.placeholder.com/50'" alt="店铺头像" />
          </div>
          <div class="store-detail">
            <div class="store-name">{{ productData?.storeName || '无人机设备' }} <span class="arrow">›</span></div>

          </div>
        </div>
      </div>

      <!-- 产品图片区域 -->
      <div class="product-images">
        <!-- 缩略图列表 -->
        <div class="thumbnail-list">
          <div 
            v-for="(img, index) in productData?.images || []" 
            :key="index"
            class="thumbnail-item"
            :class="{ active: currentImage === index }"
            @click="currentImage = index"
          >
            <img :src="img.thumb" :alt="'产品图' + (index + 1)" />
          </div>

        </div>

        <!-- 主图区域 -->
        <div class="main-image">
          <div class="brand-logo">
            <span class="dji-logo">{{ productData?.brand || 'DJI' }}</span>
          </div>
          <img :src="currentFullImage" :alt="productData?.productName" />
        </div>

        <!-- 客服进店按钮 -->
        <div class="action-buttons-top">
          <button class="btn-service">
            <span class="icon">💬</span> 客服
          </button>
          <button class="btn-store">
            <span class="icon">🏪</span> 进店
          </button>
        </div>
      </div>

      <!-- 图集/参数切换标签 -->
      <div class="tabs-switch">
        <button 
          :class="{ active: activeTab === 'gallery' }"
          @click="activeTab = 'gallery'"
        >图集</button>
        <button 
          :class="{ active: activeTab === 'params' }"
          @click="activeTab = 'params'"
        >参数</button>
      </div>

      <!-- 用户评价 -->
      <div class="section" id="reviews" ref="sectionRefs">
        <h3 class="section-title">用户评价</h3>
        <div v-if="productData?.reviews?.length" class="reviews-list">
          <div v-for="(review, index) in productData.reviews" :key="index" class="review-item">
            <div class="review-user">{{ review.user }}</div>
            <div class="review-content">{{ review.content }}</div>
            <div class="review-time">{{ review.time }}</div>
          </div>
        </div>
        <div v-else class="empty-reviews">
          <div class="empty-icon">💬</div>
          <p>暂时还没有评价呢~</p>
        </div>
      </div>

      <!-- 参数信息 -->
      <div class="section" id="params" ref="sectionRefs">
        <h3 class="section-title">参数信息</h3>
        <div class="warranty-box">
          <div class="warranty-value">{{ productData?.warranty || '1个月' }}</div>
          <div class="warranty-label">保修期</div>
        </div>
        <div class="params-grid">
          <div 
            v-for="(value, key) in productData?.parameters || {}" 
            :key="key"
            class="param-row"
          >
            <span class="param-label">{{ key }}</span>
            <span class="param-value">{{ value }}</span>
          </div>
        </div>
      </div>

      <!-- 图文详情 -->
      <div class="section" id="detail" ref="sectionRefs">
        <h3 class="section-title">图文详情</h3>
        <div class="detail-header">
          <span>概述</span>
        </div>
        <div class="detail-content">
          <p>{{ productData?.description }}</p>
          <div v-for="(detailImg, index) in productData?.detailImages || []" :key="index" class="detail-image">
            <img :src="detailImg" :alt="'详情图' + (index + 1)" />
          </div>
        </div>
      </div>
    </div>

    <!-- 右侧固定区域 -->
    <div class="right-section">
      <div class="product-info">
        <h1 class="product-title">{{ productData?.productName || '产品标题' }}</h1>
        <div class="sales-info">已售 <span class="sales-count">{{ productData?.salesCount || 0 }}</span></div>
        <div class="price">
          <span class="currency">¥</span>
          <span class="amount">{{ productData?.price || 0 }}</span>
        </div>

        <div class="shipping-info">
          <div class="shipping-row">
            <span class="shipping-text orange">{{ productData?.shipping?.promise || '承诺24小时内发货' }}</span>
            <span class="shipping-detail">{{ productData?.shipping?.method || '快递: 免运费' }}</span>
            <span class="shipping-location">{{ productData?.shipping?.location || '惠州 至 北京' }}</span>
          </div>
          <div class="shipping-row">
            <span class="shipping-icon">🛡️</span>
            <span class="shipping-text">{{ productData?.shipping?.returnPolicy || '7天无理由退货' }}</span>
          </div>
        </div>

        <div class="quantity-section">
          <span class="quantity-label">数量</span>
          <div class="quantity-control">
            <button class="qty-btn" @click="decreaseQty">−</button>
            <input type="text" v-model="quantity" class="qty-input" />
            <button class="qty-btn" @click="increaseQty">+</button>
          </div>
          <span class="stock-info">{{ productData?.stockStatus || '有货' }}</span>
        </div>

        
      </div>

      <!-- 底部操作按钮 -->
      <div class="bottom-actions">
        <button class="btn-cart" @click="addToCart">加入购物车</button>
        <button class="btn-buy" @click="buyNow">立即购买</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import router from '@/router'
import { ref, computed, onMounted } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()
const currentImage = ref(0)
const activeTab = ref('gallery')
const quantity = ref(1)
const productData = ref(null)
const loading = ref(true)

// 计算当前显示的图片
const currentFullImage = computed(() => {
  if (productData.value?.images?.length > currentImage.value) {
    return productData.value.images[currentImage.value].full
  }
  return 'https://via.placeholder.com/500'
})

// 从token中解析出用户ID（移到外部，避免重复定义）
const parseToken = () => {
  const token = localStorage.getItem('token')
  if (!token) return null
  
  try {
    const base64Url = token.split('.')[1]
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/')
    const payload = JSON.parse(atob(base64))
    return payload.userId // 返回用户ID
  } catch (error) {
    console.error('Token解析失败:', error)
    return null
  }
}

// 获取产品数据
const fetchProductData = async () => {
  loading.value = true
  try {
    // 测试数据 - 添加 productNum 字段
    const data = {
      productNum: 'DJI-MINI3-001', // 添加商品编号
      productName: '大疆 DJI Mini 3 系列全向桨叶保护罩 Mini 3 / Mini 3 Pro 配件',
      price: 49,
      brand: 'DJI',
      storeName: '无人机设备',
      storeAvatar: 'https://via.placeholder.com/50',
      rating: 4.5,
      description: 'DJI Mini 3 系列全向桨叶保护罩，专为 Mini 3 / Mini 3 Pro 设计，有效保护桨叶和周围物体，适合新手飞行练习使用。安装简便，材质轻巧，不影响飞行性能。',
      warranty: '1个月',
      stockStatus: '有货',
      stock: 100, // 添加库存数量
      service: '选购更多',
      parameters: {
        '品牌': 'DJI/大疆',
        '型号': 'mini3',
        '图传距离': '1km',
        '飞行时间': '30分钟',
        '最大抗风速': '5级'
      },
      images: [
        { thumb: 'https://via.placeholder.com/80?text=1', full: 'https://via.placeholder.com/500?text=桨叶保护罩' },
        { thumb: 'https://via.placeholder.com/80?text=2', full: 'https://via.placeholder.com/500?text=产品图2' },
        { thumb: 'https://via.placeholder.com/80?text=3', full: 'https://via.placeholder.com/500?text=产品图3' },
        { thumb: 'https://via.placeholder.com/80?text=4', full: 'https://via.placeholder.com/500?text=产品图4' },
        { thumb: 'https://via.placeholder.com/80?text=5', full: 'https://via.placeholder.com/500?text=产品图5' },
        { thumb: 'https://via.placeholder.com/80?text=6', full: 'https://via.placeholder.com/500?text=产品图6' },
        { thumb: 'https://via.placeholder.com/80?text=5', full: 'https://via.placeholder.com/500?text=产品图5' },
        { thumb: 'https://via.placeholder.com/80?text=5', full: 'https://via.placeholder.com/500?text=产品图5' },
      ],
      detailImages: [
        'https://via.placeholder.com/600x400?text=产品详情图',
        'https://via.placeholder.com/600x400?text=安装示意图'
      ],
      reviews: [],
      shipping: {
        label: '24小时发',
        promise: '承诺24小时内发货',
        method: '快递: 免运费',
        location: '北京 至 广州 白云',
        returnPolicy: '7天无理由退货',
        refund: '极速退款'
      }
    }
    
    productData.value = data
  } catch (error) {
    console.error('获取产品数据失败:', error)
  } finally {
    loading.value = false
  }
}

// 数量控制
const decreaseQty = () => {
  if (quantity.value > 1) quantity.value--
}

const increaseQty = () => {
  quantity.value++
}

// 购物车操作
const addToCart = async () => {
  try {
    // 1. 检查是否有商品
    if (!productData.value) {
      alert('请先选择商品')
      return
    }
    
    // 2. 获取用户ID
    const userId = parseToken()
    if (!userId) {
      alert('请先登录')
      router.push('/register')
      return
    }
    
    // 3. 获取当前选中的商品图片
    const currentImageUrl = productData.value.images?.[currentImage.value]?.full 
      || productData.value.images?.[0]?.full 
      || ''
    
    // 4. 准备发送到后端的数据
    const cartData = {
      userId: userId,
      quantity: quantity.value,
      productId: route.params.id || route.query.id || productData.value.productNum,
      productNum: productData.value.productNum,
      productName: productData.value.productName,
      productPrice: productData.value.price,
      productImage: currentImageUrl,
      storeName: productData.value.storeName,
      addedTime: new Date().toISOString(),
    }
    
    console.log('准备加入购物车:', cartData)
    
    // 5. 发送请求到后端
    const response = await fetch('/api/shoppingcart', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      },
      body: JSON.stringify(cartData)
    })
    
    // 6. 检查响应
    if (!response.ok) {
      const errorData = await response.json()
      throw new Error(errorData.message || '加入购物车失败')
    }
    
    alert('成功加入购物车')
  } catch (error) {
    console.error('加入购物车失败:', error)
    alert(error.message || '加入购物车失败，请稍后重试')
  }
}

// 立即购买
const buyNow = () => {
  try {
    // 1. 检查商品数据是否存在
    if (!productData.value) {
      alert('商品信息不存在')
      return
    }
    
    // 2. 检查用户是否登录
    const userId = parseToken()
    if (!userId) {
      alert('请先登录')
      router.push({
        path: '/register',
        query: { redirect: route.fullPath }
      })
      return
    }
    
    // 3. 检查商品库存
    if (productData.value.stockStatus === '无货' || productData.value.stock <= 0) {
      alert('抱歉，该商品暂无库存')
      return
    }
    
    // 4. 检查购买数量是否有效
    if (quantity.value < 1) {
      alert('购买数量不能小于1')
      return
    }
    
    // 5. 获取商品ID
    const productId = productData.value.productNum || route.params.id || route.query.id
    if (!productId) {
      alert('商品ID不存在')
      return
    }
    
    // 6. 获取当前选中的商品图片
    const currentImageUrl = productData.value.images?.[currentImage.value]?.full 
      || productData.value.images?.[0]?.full 
      || ''
    
    // 7. 计算总价
    const totalPrice = productData.value.price * quantity.value
    
    // 8. 构建订单数据
    const orderData = {
      userId: userId,
      productId: productId,
      productNum: productData.value.productNum,
      productName: productData.value.productName,
      productPrice: productData.value.price,
      productImage: currentImageUrl,
      quantity: quantity.value,
      totalAmount: totalPrice,
      storeName: productData.value.storeName || '',
      storeId: productData.value.storeId || '',
      orderTime: new Date().toISOString(),
    }
    
    console.log('准备生成的订单数据:', orderData)
    
    // 9. 跳转到订单确认页
    router.push({
      path: '/confirm-order',  //具体自己写
      query: {
        productId: productId,
        quantity: quantity.value,
        price: productData.value.price
      }
    })
    
  } catch (error) {
    console.error('购买失败:', error)
    alert('购买失败，请稍后重试')
  }
}

// 组件挂载时获取数据
onMounted(() => {
  fetchProductData()
})
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

.page-container {
  display: flex;
  width: 100%;
  min-height: 100vh;
  background: #f5f5f5;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'PingFang SC', 'Microsoft YaHei', sans-serif;
}

/* 左侧可滚动区域 */
.left-section {
  flex: 1;
  max-width: 60%;
  height: 100vh;
  overflow-y: auto;
  background: #fff;
  border-right: 1px solid #e8e8e8;
}

/* 右侧固定区域 */
.right-section {
  width: 40%;
  height: 90vh;
  position: sticky;
  top: 0;
  display: flex;
  flex-direction: column;
  background: #fff;
}

/* 头部样式 */
.header {
  display: flex;
  align-items: center;
  padding: 15px 20px;
  border-bottom: 1px solid #f0f0f0;
}

.taobao-logo {
  font-size: 28px;
  font-weight: bold;
  color: #ffaa00;
  margin-right: 30px;
}

.store-info {
  display: flex;
  align-items: center;
}

.store-avatar img {
  width: 50px;
  height: 50px;
  border-radius: 4px;
  margin-right: 10px;
}

.store-name {
  font-size: 16px;
  font-weight: 500;
  color: #333;
}

.arrow {
  color: #999;
  margin-left: 5px;
}

.rating {
  margin-top: 5px;
}

.hearts {
  font-size: 14px;
}

.no-rating {
  color: #ff5000;
  font-size: 12px;
  margin-left: 5px;
}

/* 产品图片区域 */
.product-images {
  display: flex;
  padding: 20px;
  gap: 20px;
}

.thumbnail-list {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.thumbnail-item {
  width: 70px;
  height: 70px;
  border: 2px solid transparent;
  border-radius: 4px;
  cursor: pointer;
  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #f9f9f9;
}

.thumbnail-item.active {
  border-color: #ff5000;
}

.thumbnail-item img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.thumbnail-item.params-btn {
  flex-direction: column;
  background: #f5f5f5;
  color: #666;
  font-size: 12px;
}

.params-icon {
  font-size: 20px;
  margin-bottom: 2px;
}

.main-image {
  flex: 1;
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #fff;
}

.brand-logo {
  position: absolute;
  top: 10px;
  left: 10px;
}

.dji-logo {
  font-size: 24px;
  font-weight: bold;
  color: #333;
  font-family: Arial, sans-serif;
}

.main-image img {
  max-width: 100%;
  max-height: 400px;
  object-fit: contain;
}

.action-buttons-top {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.btn-service,
.btn-store {
  display: flex;
  align-items: center;
  gap: 5px;
  padding: 10px 20px;
  border: 1px solid #e8e8e8;
  border-radius: 20px;
  background: #fff;
  cursor: pointer;
  font-size: 14px;
  color: #333;
}

.btn-service:hover,
.btn-store:hover {
  background: #f9f9f9;
}

/* 标签切换 */
.tabs-switch {
  display: flex;
  justify-content: center;
  padding: 15px;
  gap: 0;
  border-bottom: 1px solid #f0f0f0;
}

.tabs-switch button {
  padding: 8px 30px;
  border: 1px solid #e8e8e8;
  background: #fff;
  cursor: pointer;
  font-size: 14px;
  color: #666;
}

.tabs-switch button:first-child {
  border-radius: 20px 0 0 20px;
}

.tabs-switch button:last-child {
  border-radius: 0 20px 20px 0;
  border-left: none;
}

.tabs-switch button.active {
  background: #333;
  color: #fff;
  border-color: #333;
}

/* 详情导航 */
.detail-nav {
  display: flex;
  padding: 15px 20px;
  gap: 30px;
  border-bottom: 1px solid #f0f0f0;
  position: sticky;
  top: 0;
  background: #fff;
  z-index: 10;
}

.nav-item {
  font-size: 15px;
  color: #666;
  cursor: pointer;
  padding-bottom: 10px;
  position: relative;
}

.nav-item.active {
  color: #ff5000;
}

.nav-item.active::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  height: 3px;
  background: #ff5000;
  border-radius: 2px;
}

/* 内容区块 */
.section {
  padding: 20px;
  border-bottom: 10px solid #f5f5f5;
}

.section-title {
  font-size: 18px;
  font-weight: 600;
  color: #333;
  margin-bottom: 20px;
}

/* 用户评价 */
.empty-reviews {
  text-align: center;
  padding: 40px 0;
  color: #999;
}

.empty-icon {
  font-size: 40px;
  margin-bottom: 10px;
  opacity: 0.5;
}

/* 参数信息 */
.warranty-box {
  background: #f9f9f9;
  padding: 20px;
  text-align: center;
  margin-bottom: 20px;
  border-radius: 8px;
}

.warranty-value {
  font-size: 24px;
  font-weight: bold;
  color: #333;
}

.warranty-label {
  font-size: 14px;
  color: #ff5000;
  margin-top: 5px;
}

.params-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 15px;
}

.param-row {
  display: flex;
  gap: 20px;
}

.param-label {
  color: #999;
  min-width: 70px;
}

.param-value {
  color: #333;
}

/* 图文详情 */
.detail-header {
  background: #ff5000;
  color: #fff;
  padding: 10px 20px;
  border-radius: 4px;
  margin-bottom: 20px;
}

.detail-content {
  line-height: 1.8;
  color: #666;
}

.detail-image {
  margin: 20px 0;
  text-align: center;
}

.detail-image img {
  max-width: 100%;
  border-radius: 4px;
}

/* 推荐商品 */
.recommend-list {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 15px;
}

.recommend-item {
  background: #f9f9f9;
  border-radius: 8px;
  overflow: hidden;
  cursor: pointer;
}

.recommend-item:hover {
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
}

.recommend-item img {
  width: 100%;
  aspect-ratio: 1;
  object-fit: cover;
}

.recommend-name {
  padding: 10px;
  font-size: 13px;
  color: #333;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}



/* 右侧产品信息 */
.product-info {
  padding: 20px;
  flex: 1;
  overflow-y: auto;
}

.product-title {
  font-size: 20px;
  font-weight: 500;
  color: #333;
  line-height: 1.4;
  margin-bottom: 10px;
}

.sales-info {
  font-size: 13px;
  color: #ff5000;
  margin-bottom: 10px;
}

.price {
  color: #ffaa00;
  margin-bottom: 20px;
}

.currency {
  font-size: 16px;
}

.amount {
  font-size: 32px;
  font-weight: bold;
}

.shipping-info {
  padding: 15px 0;
  border-top: 1px solid #f0f0f0;
  border-bottom: 1px solid #f0f0f0;
  margin-bottom: 20px;
}

.shipping-row {
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  gap: 10px;
  margin-bottom: 10px;
  font-size: 13px;
  color: #666;
}

.shipping-row:last-child {
  margin-bottom: 0;
}

.shipping-label {
  color: #ff5000;
  font-weight: 500;
}

.shipping-text.orange {
  color: #ff5000;
}

.shipping-location {
  color: #333;
}

.quantity-section {
  display: flex;
  align-items: center;
  gap: 15px;
  margin-bottom: 20px;
}

.quantity-label {
  color: #999;
  font-size: 14px;
}

.quantity-control {
  display: flex;
  align-items: center;
  border: 1px solid #e8e8e8;
  border-radius: 4px;
}

.qty-btn {
  width: 36px;
  height: 36px;
  border: none;
  background: #f9f9f9;
  cursor: pointer;
  font-size: 18px;
  color: #666;
}

.qty-btn:hover {
  background: #f0f0f0;
}

.qty-input {
  width: 50px;
  height: 36px;
  border: none;
  border-left: 1px solid #e8e8e8;
  border-right: 1px solid #e8e8e8;
  text-align: center;
  font-size: 14px;
}

.stock-info {
  color: #999;
  font-size: 13px;
}

.service-section {
  display: flex;
  align-items: center;
  gap: 15px;
  font-size: 14px;
}

.service-label {
  color: #999;
}

.service-value {
  color: #333;
  cursor: pointer;
}

/* 底部操作按钮 */
.bottom-actions {
  display: flex;
  padding: 15px 20px;
  gap: 0;
  border-top: 1px solid #f0f0f0;
  background: #fff;
}

.btn-cart {
  flex: 1;
  padding: 15px;
  border: none;
  background: linear-gradient(to right, #ffc107, #ffb300);
  color: #fff;
  font-size: 16px;
  font-weight: 500;
  cursor: pointer;
  border-radius: 4px 0 0 4px;
}

.btn-buy {
  flex: 1;
  padding: 15px;
  border: none;
  background: linear-gradient(to right, #ff5000, #ff7000);
  color: #fff;
  font-size: 16px;
  font-weight: 500;
  cursor: pointer;
  border-radius: 0 4px 4px 0;
}

.btn-cart:hover {
  background: linear-gradient(to right, #e6ac00, #e6a200);
}

.btn-buy:hover {
  background: linear-gradient(to right, #e64800, #e66400);
}

.btn-favorite {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 10px 15px;
  border: none;
  background: #fff;
  cursor: pointer;
  color: #666;
  font-size: 12px;
  margin-left: 10px;
}

.fav-icon {
  font-size: 20px;
  margin-bottom: 2px;
}

.btn-favorite:hover {
  color: #ff5000;
}

/* 滚动条样式 */
.left-section::-webkit-scrollbar {
  width: 6px;
}

.left-section::-webkit-scrollbar-track {
  background: #f1f1f1;
}

.left-section::-webkit-scrollbar-thumb {
  background: #ccc;
  border-radius: 3px;
}

.left-section::-webkit-scrollbar-thumb:hover {
  background: #aaa;
}
</style>
