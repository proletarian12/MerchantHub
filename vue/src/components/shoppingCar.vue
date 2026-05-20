<template>
    <div class="cart-page">
      <!-- Header -->
      <header class="header">
        <span class="logo">SHOPPING</span>
        <span class="title">购物车</span>
      </header>
  
      <!-- Tabs -->
      <div class="tabs">
        <div class="tab active">
          <svg class="tab-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <rect x="3" y="3" width="7" height="7"></rect>
            <rect x="14" y="3" width="7" height="7"></rect>
            <rect x="3" y="14" width="7" height="7"></rect>
            <rect x="14" y="14" width="7" height="7"></rect>
          </svg>
          全部商品({{ cartItems.length }})
        </div>

      </div>
  
      <!-- Toolbar -->
      <div class="toolbar">
        <div class="toolbar-left">
          <label class="checkbox-wrapper">
            <input type="checkbox" v-model="selectAll" @change="toggleSelectAll">
            <span class="checkmark"></span>
          </label>
          <span class="select-all-text">全选</span>
          <button class="toolbar-btn">删除</button>
        </div>
        <div class="toolbar-right">

          <div class="search-box">
            <input type="text" placeholder="搜索购物车内商品" v-model="searchQuery">
            <svg class="search-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <circle cx="11" cy="11" r="8"></circle>
              <path d="M21 21l-4.35-4.35"></path>
            </svg>
          </div>
        </div>
      </div>
  
      <!-- Cart Items -->
      <div class="cart-list">
        <div v-for="(shop, shopIndex) in groupedItems" :key="shopIndex" class="shop-group">
          <!-- Shop Header -->
          <div class="shop-header">
            <label class="checkbox-wrapper">
              <input type="checkbox" v-model="shop.selected" @change="toggleShopSelect(shop)">
              <span class="checkmark"></span>
            </label>
            <span :class="['platform-badge', shop.platform]">{{ shop.platformName }}</span>
            <span class="shop-name">{{ shop.shopName }}</span>
            <svg class="shop-arrow" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <polyline points="9 18 15 12 9 6"></polyline>
            </svg>
          </div>
  
          <!-- Products in Shop -->
          <div v-for="(item, itemIndex) in shop.items" :key="itemIndex" class="cart-item">
            <div class="item-checkbox">
              <label class="checkbox-wrapper">
                <input type="checkbox" v-model="item.selected" :disabled="!item.available">
                <span class="checkmark" :class="{ disabled: !item.available }"></span>
              </label>
            </div>
            
            <div class="item-image">
              <img :src="item.image" :alt="item.name">
              <div v-if="item.tag" class="item-tag">{{ item.tag }}</div>
            </div>
            

            
            <div class="item-price">
              <span class="price">¥{{ item.price }}</span>
            </div>
            
            <div class="item-quantity">
              <span>{{ item.quantity }}</span>
            </div>
            
            <div class="item-actions">
              <a href="#" class="action-link">移入收藏</a>
              <a href="#" class="action-link">删除</a>
            </div>
          </div>
        </div>
      </div>
  
      <!-- Footer Bar -->
      <div class="footer-bar">
        <div class="footer-left">
          <label class="checkbox-wrapper">
            <input type="checkbox" v-model="selectAll" @change="toggleSelectAll">
            <span class="checkmark"></span>
          </label>
          <span>全选</span>
        </div>
        <div class="footer-right">
          <div class="total-info">
            <span>已选商品 <em>{{ selectedCount }}</em> 件</span>
            <span class="total-price">合计: <em>¥{{ totalPrice.toFixed(2) }}</em></span>
          </div>
          <button class="checkout-btn" :disabled="selectedCount === 0">结算</button>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, computed } from 'vue'
  
  const searchQuery = ref('')
  const selectAll = ref(false)
  
  const groupedItems = ref([/*
    {
      platform: 'taogongchang',
      platformName: '工厂',
      shopName: '天天特卖工厂',
      selected: false,
      items: [
        {
          id: 1,
          name: '无人机高清专业航拍',
          image: 'https://img.alicdn.com/bao/uploaded/i1/2838892713/O1CN01Uw8Y6u1Vub6Uw6Uwk_!!0-item_pic.jpg',
          price: 222,
          quantity: 1,
          selected: false,
          available: false,
          statusText: '宝贝已不能购买',
          tag: '1800米超长续航'
        }
      ]
    },
    {
      platform: 'taobao',
      platformName: '',
      shopName: '拯救者电竞笔记本商城',
      selected: false,
      items: [
        {
          id: 2,
          name: '拯救者R9000P Y7000P游戏本',
          image: 'https://img.alicdn.com/bao/uploaded/i4/2200724907880/O1CN01JKdJEH1DOPpRAVkCP_!!0-item_pic.jpg',
          price: 11348,
          quantity: 1,
          selected: false,
          available: false,
          statusText: '店铺已关闭，宝贝不能购买，请谅解',
          tag: null
        }
      ]
      }
  */])
  
  const cartItems = computed(() => {
    return groupedItems.value.flatMap(shop => shop.items)
  })
  
  const selectedCount = computed(() => {
    return cartItems.value.filter(item => item.selected && item.available).length
  })
  
  const totalPrice = computed(() => {
    return cartItems.value
      .filter(item => item.selected && item.available)
      .reduce((sum, item) => sum + item.price * item.quantity, 0)
  })
  
  const toggleSelectAll = () => {
    groupedItems.value.forEach(shop => {
      shop.selected = selectAll.value
      shop.items.forEach(item => {
        if (item.available) {
          item.selected = selectAll.value
        }
      })
    })
  }
  
  const toggleShopSelect = (shop) => {
    shop.items.forEach(item => {
      if (item.available) {
        item.selected = shop.selected
      }
    })
  }
  </script>
  
  <style scoped>
  * {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
  }
  
  .cart-page {
    font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', 'PingFang SC', 'Hiragino Sans GB', 'Microsoft YaHei', sans-serif;
    background-color: #f5f5f5;
    min-height: 100vh;
    padding-bottom: 60px;
  }
  
  /* Header */
  .header {
    background: #fff;
    padding: 20px 40px;
    display: flex;
    align-items: baseline;
    gap: 16px;
  }
  
  .logo {
    font-size: 36px;
    font-weight: bold;
    color: #ffaa00;
  }
  
  .title {
    font-size: 28px;
    color: #ffaa00;
  }
  
  /* Tabs */
  .tabs {
    background: #fff;
    padding: 0 40px;
    display: flex;
    gap: 40px;
    border-bottom: 1px solid #e8e8e8;
  }
  
  .tab {
    display: flex;
    align-items: center;
    gap: 6px;
    padding: 16px 0;
    color: #666;
    cursor: pointer;
    position: relative;
    font-size: 14px;
  }
  
  .tab.active {
    color: #ff5000;
  }
  
  .tab.active::after {
    content: '';
    position: absolute;
    bottom: 0;
    left: 0;
    right: 0;
    height: 2px;
    background: #ff5000;
  }
  
  .tab-icon {
    width: 16px;
    height: 16px;
  }
  
  .tab-icon.lightning {
    color: #ffaa00;
  }
  
  /* Toolbar */
  .toolbar {
    background: #fff;
    padding: 16px 40px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    border-bottom: 1px solid #e8e8e8;
  }
  
  .toolbar-left {
    display: flex;
    align-items: center;
    gap: 16px;
  }
  
  .select-all-text {
    font-size: 14px;
    color: #333;
  }
  
  .toolbar-btn {
    padding: 6px 16px;
    border: 1px solid #ddd;
    background: #fff;
    border-radius: 4px;
    font-size: 13px;
    color: #333;
    cursor: pointer;
  }
  
  .toolbar-btn:hover {
    border-color: #ff5000;
    color: #ff5000;
  }
  
  .toolbar-right {
    display: flex;
    align-items: center;
    gap: 16px;
  }
  
  .filter-select {
    display: flex;
    align-items: center;
    gap: 4px;
    padding: 6px 12px;
    border: 1px solid #ddd;
    border-radius: 4px;
    font-size: 13px;
    color: #999;
    cursor: pointer;
  }
  
  .dropdown-icon {
    width: 14px;
    height: 14px;
  }
  
  .search-box {
    display: flex;
    align-items: center;
    border: 1px solid #ddd;
    border-radius: 4px;
    padding: 6px 12px;
    width: 200px;
  }
  
  .search-box input {
    border: none;
    outline: none;
    flex: 1;
    font-size: 13px;
  }
  
  .search-box input::placeholder {
    color: #ccc;
  }
  
  .search-icon {
    width: 18px;
    height: 18px;
    color: #999;
  }
  
  /* Checkbox */
  .checkbox-wrapper {
    display: inline-flex;
    align-items: center;
    position: relative;
    cursor: pointer;
  }
  
  .checkbox-wrapper input {
    position: absolute;
    opacity: 0;
    cursor: pointer;
    height: 0;
    width: 0;
  }
  
  .checkmark {
    width: 18px;
    height: 18px;
    border: 1px solid #ddd;
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
  }
  
  .checkmark.disabled {
    background: #f5f5f5;
  }
  
  .checkbox-wrapper input:checked ~ .checkmark {
    background: #ff5000;
    border-color: #ff5000;
  }
  
  .checkbox-wrapper input:checked ~ .checkmark::after {
    content: '';
    width: 5px;
    height: 9px;
    border: solid white;
    border-width: 0 2px 2px 0;
    transform: rotate(45deg);
    margin-bottom: 2px;
  }
  
  /* Cart List */
  .cart-list {
    padding: 16px 40px;
  }
  
  .shop-group {
    background: #fff;
    border-radius: 8px;
    margin-bottom: 16px;
    overflow: hidden;
  }
  
  .shop-header {
    display: flex;
    align-items: center;
    gap: 12px;
    padding: 16px 20px;
    border-bottom: 1px solid #f0f0f0;
  }
  
  .platform-badge {
    padding: 2px 6px;
    border-radius: 4px;
    font-size: 12px;
    font-weight: bold;
  }
  
  .platform-badge.taobao {
    background: #ffe4d4;
    color: #ff5000;
  }
  
  .platform-badge.taogongchang {
    background: #fff0e6;
    color: #ff7800;
  }
  
  .shop-name {
    font-size: 14px;
    color: #333;
    font-weight: 500;
  }
  
  .shop-arrow {
    width: 16px;
    height: 16px;
    color: #999;
  }
  
  /* Cart Item */
  .cart-item {
    display: flex;
    align-items: center;
    padding: 20px;
    border-bottom: 1px solid #f0f0f0;
  }
  
  .cart-item:last-child {
    border-bottom: none;
  }
  
  .item-checkbox {
    width: 50px;
    display: flex;
    justify-content: center;
  }
  
  .item-image {
    width: 100px;
    height: 100px;
    position: relative;
    margin-right: 16px;
    flex-shrink: 0;
  }
  
  .item-image img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    border-radius: 4px;
  }
  
  .item-tag {
    position: absolute;
    bottom: 0;
    left: 0;
    right: 0;
    background: linear-gradient(135deg, #ff6b00, #ff9500);
    color: #fff;
    font-size: 11px;
    padding: 2px 4px;
    text-align: center;
  }
  
  .item-info {
    flex: 1;
    min-width: 0;
  }
  
  .item-name {
    font-size: 14px;
    color: #333;
    margin-bottom: 8px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
  
  .item-name.unavailable {
    color: #999;
  }
  
  .item-status {
    font-size: 12px;
    color: #ff5000;
    margin-bottom: 8px;
  }
  
  .find-similar-btn {
    padding: 6px 16px;
    border: 1px solid #333;
    background: #fff;
    border-radius: 20px;
    font-size: 12px;
    color: #333;
    cursor: pointer;
  }
  
  .find-similar-btn:hover {
    border-color: #ff5000;
    color: #ff5000;
  }
  
  .item-price {
    width: 120px;
    text-align: center;
  }
  
  .price {
    font-size: 16px;
    color: #ff5000;
    font-weight: 500;
  }
  
  .item-quantity {
    width: 80px;
    text-align: center;
    color: #666;
  }
  
  .item-actions {
    width: 100px;
    display: flex;
    flex-direction: column;
    align-items: flex-end;
    gap: 8px;
  }
  
  .action-link {
    font-size: 12px;
    color: #666;
    text-decoration: none;
  }
  
  .action-link:hover {
    color: #ff5000;
  }
  
  /* Footer Bar */
  .footer-bar {
    position: fixed;
    bottom: 0;
    left: 0;
    right: 0;
    background: #fff;
    padding: 12px 40px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    box-shadow: 0 -2px 10px rgba(0, 0, 0, 0.05);
    z-index: 100;
  }
  
  .footer-left {
    display: flex;
    align-items: center;
    gap: 8px;
    font-size: 14px;
    color: #333;
  }
  
  .footer-right {
    display: flex;
    align-items: center;
    gap: 20px;
  }
  
  .total-info {
    display: flex;
    gap: 20px;
    font-size: 14px;
    color: #666;
  }
  
  .total-info em {
    font-style: normal;
    color: #ff5000;
    font-weight: 500;
  }
  
  .total-price em {
    font-size: 20px;
  }
  
  .checkout-btn {
    padding: 12px 40px;
    background: linear-gradient(135deg, #ff7800, #ff5000);
    color: #fff;
    border: none;
    border-radius: 20px;
    font-size: 16px;
    cursor: pointer;
  }
  
  .checkout-btn:disabled {
    background: #ccc;
    cursor: not-allowed;
  }
  
  .checkout-btn:not(:disabled):hover {
    background: linear-gradient(135deg, #ff6b00, #e64500);
  }
  </style>
  