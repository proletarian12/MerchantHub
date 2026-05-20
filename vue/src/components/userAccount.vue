<template>
  <div class="page-container">
    <!-- Header -->
    <header class="header">
      <div class="header-content">
        <div class="header-left">
          <span class="logo-text">SHOPPING</span>
          <span class="sub-title">我的账号</span>
        </div>
        <div class="header-right">
          <div class="search-group">
            <input
              type="text"
              v-model="searchQuery"
              placeholder="请输入搜索"
              class="search-input"
            />
            <div class="search-icon">
              <svg class="icon-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 9a2 2 0 012-2h.93a2 2 0 001.664-.89l.812-1.22A2 2 0 0110.07 4h3.86a2 2 0 011.664.89l.812 1.22A2 2 0 0018.07 7H19a2 2 0 012 2v9a2 2 0 01-2 2H5a2 2 0 01-2-2V9z" />
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 13a3 3 0 11-6 0 3 3 0 016 0z" />
              </svg>
            </div>
            <button class="search-btn">搜索</button>
          </div>
        </div>
      </div>
    </header>

    <!-- Main Content -->
    <main class="main-content">
      <!-- Left Sidebar -->


      <!-- Center Content -->
      <div class="center-area">
        <!-- User Info Card -->
        <div class="user-card">
          <div class="avatar-wrapper">
            <div class="avatar-bg">

            </div>
          </div>
          <div class="user-info">
            <div class="user-row">
              <span class="username">040628675228</span>
              <a href="#" class="address-link">收货地址</a>
            </div>

          </div>
        </div>

        <!-- Stats Cards Row -->
        <div class="stats-grid">
          <!-- Orders Card -->
          <div class="card orders-card">
            <div class="card-header">
              <span class="card-title">我的订单</span>
              <a href="#" class="card-more">
                <svg class="icon-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7" />
                </svg>
              </a>
            </div>
            <div class="order-stats">
              <div v-for="(stat, index) in orderStats" :key="index" class="stat-item">
                <div class="stat-count">{{ stat.count }}</div>
                <div class="stat-label">{{ stat.label }}</div>
              </div>
            </div>
            <div class="order-footer">
              <p class="empty-tip">当前暂无物流信息更新</p>
              <a href="#" class="view-all-link">查看全部订单 &gt;</a>
            </div>
          </div>

          <!-- Favorites Card -->
          <div class="card small-card">
            <div class="card-header">
              <span class="card-title">我的收藏</span>
              <a href="#" class="card-more">
                <svg class="icon-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7" />
                </svg>
              </a>
            </div>
            <div class="empty-card-content">
              <div class="empty-icon-wrapper">
                <svg class="empty-icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5" d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z" />
                </svg>
              </div>
              <p class="empty-text">还没有收藏宝贝</p>
              <a href="#" class="browse-link">去逛逛</a>
            </div>
          </div>

          <!-- Footprint Card -->
          <div class="card small-card">
            <div class="card-header">
              <span class="card-title">我的足迹</span>
              <a href="#" class="card-more">
                <svg class="icon-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7" />
                </svg>
              </a>
            </div>
            <div class="empty-card-content">
              <div class="empty-icon-wrapper">
                <svg class="empty-icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z" />
                </svg>
              </div>
              <p class="empty-text">还没有留下足迹</p>
              <a href="#" class="browse-link">去逛逛</a>
            </div>
          </div>
        </div>
      </div>

      <!-- Right Sidebar -->
      <aside class="right-sidebar">
        <div class="benefits-panel">





          <!-- Shopping Cart -->
          <div class="cart-card">
            <div class="card-header">
              <span class="card-title">购物车</span>
              <a href="#" class="card-more">
                <svg class="icon-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7" />
                </svg>
              </a>
            </div>
            <div class="empty-card-content cart-empty">
              <div class="empty-icon-wrapper small">
                <svg class="empty-icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5" d="M3 3h2l.4 2M7 13h10l4-8H5.4M7 13L5.4 5M7 13l-2.293 2.293c-.63.63-.184 1.707.707 1.707H17m0 0a2 2 0 100 4 2 2 0 000-4zm-8 2a2 2 0 11-4 0 2 2 0 014 0z" />
                </svg>
              </div>
              <p class="empty-text">购物车为空</p>
              <a href="#" class="browse-link">去逛逛</a>
            </div>
          </div>
        </div>
      </aside>
    </main>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const searchQuery = ref('')



const orderStats = ref([
  { count: 0, label: '待付款' },
  { count: 0, label: '待发货' },
  { count: 0, label: '待收货' },
  { count: 0, label: '待评价' },
  { count: 0, label: '退款/售后' },
])
</script>

<style scoped>
/* 基础变量与全局重置 */
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: 'Noto Sans SC', -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif;
}

/* 颜色变量 */
:root {
  --orange: #f56c02;
  --orange-light: #ff8533;
  --orange-bg: #fff3e8;
  --orange-50: #fff7ed;
  --gray-50: #f9fafb;
  --gray-100: #f3f4f6;
  --gray-200: #e5e7eb;
  --gray-300: #d1d5db;
  --gray-400: #9ca3af;
  --gray-500: #6b7280;
  --gray-600: #4b5563;
  --gray-700: #374151;
  --gray-800: #1f2937;
  --white: #ffffff;
}

/* 页面容器 */
.page-container {
  min-height: 100vh;
  background-color: var(--gray-50);
  font-family: 'Noto Sans SC', -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif;
}

/* Header 样式 */
.header {
  background-color: var(--white);
  border-bottom: 1px solid var(--gray-200);
}

.header-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 16px;
  height: 56px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.header-left {
  display: flex;
  align-items: center;
  gap: 16px;
}

.logo-text {
  color: var(--orange);
  font-size: 24px;
  font-weight: 700;
}

.sub-title {
  color: var(--gray-700);
  font-size: 18px;
}

.header-right {
  display: flex;
  align-items: center;
  gap: 12px;
}

/* 搜索组件 */
.search-group {
  position: relative;
  display: flex;
  align-items: center;
}

.search-input {
  width: 280px;
  height: 36px;
  padding-left: 16px;
  padding-right: 80px;
  border: 1px solid var(--gray-300);
  border-radius: 9999px 0 0 9999px;
  font-size: 14px;
  outline: none;
  transition: border-color 0.2s;
}

.search-input:focus {
  border-color: var(--orange);
}

.search-icon {
  position: absolute;
  right: 70px;
  display: flex;
  align-items: center;
  gap: 8px;
  color: var(--gray-400);
}

.search-btn {
  height: 36px;
  padding: 0 20px;
  background-color: var(--orange);
  color: var(--white);
  font-size: 14px;
  font-weight: 500;
  border: none;
  border-radius: 0 9999px 9999px 0;
  cursor: pointer;
  transition: background-color 0.2s;
}

.search-btn:hover {
  background-color: var(--orange-light);
}

/* 主要布局 */
.main-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 16px;
  display: flex;
  gap: 16px;
}

/* 左侧边栏 */
.left-sidebar {
  width: 180px;
  flex-shrink: 0;
}

.sidebar-nav {
  background-color: var(--white);
  border-radius: 8px;
  overflow: hidden;
}

.sidebar-title {
  background-color: var(--orange);
  color: var(--white);
  padding: 12px 16px;
  font-weight: 500;
}

.sidebar-list {
  list-style: none;
  padding: 8px 0;
}

.sidebar-link {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 10px 16px;
  font-size: 14px;
  color: var(--gray-700);
  text-decoration: none;
  transition: background-color 0.2s, color 0.2s;
}

.sidebar-link:hover {
  background-color: var(--gray-50);
  color: var(--orange);
}

.arrow-icon {
  width: 16px;
  height: 16px;
  color: var(--gray-400);
  transition: transform 0.2s;
}

.arrow-expanded {
  transform: rotate(180deg);
}

/* 中间区域 */
.center-area {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 16px;
}

/* 用户卡片 */
.user-card {
  background: linear-gradient(to right, #fff7ed, #ffedd5);
  border-radius: 8px;
  padding: 20px;
  display: flex;
  align-items: center;
  gap: 24px;
}

.avatar-wrapper {
  width: 80px;
  height: 80px;
  border-radius: 50%;
  overflow: hidden;
  background-color: #fed7aa;
  display: flex;
  align-items: center;
  justify-content: center;
}

.avatar-bg {
  width: 100%;
  height: 100%;
  background: linear-gradient(to bottom, rgb(94, 148, 255), #0821ff);
  display: flex;
  align-items: center;
  justify-content: center;
}

.avatar-svg {
  width: 64px;
  height: 64px;
  color: #f97316;
}

.user-info {
  flex: 1;
}

.user-row {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 12px;
}

.username {
  color: var(--gray-800);
  font-weight: 500;
}

.address-link {
  color: var(--gray-500);
  font-size: 14px;
  text-decoration: none;
}

.address-link:hover {
  color: var(--orange);
}

.coin-badge {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  background-color: var(--white);
  border-radius: 9999px;
  padding: 8px 16px;
}

.coin-label {
  color: var(--orange);
  font-weight: 700;
  font-size: 14px;
}

.coin-desc {
  color: var(--gray-600);
  font-size: 14px;
}

/* 统计卡片网格 */
.stats-grid {
  display: grid;
  grid-template-columns: 2fr 1fr 1fr;
  gap: 16px;
}

.card {
  background-color: var(--white);
  border-radius: 8px;
  padding: 16px;
}

.card-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 16px;
}

.card-title {
  font-weight: 500;
  color: var(--gray-800);
}

.card-more {
  color: var(--gray-400);
  text-decoration: none;
  transition: color 0.2s;
}

.card-more:hover {
  color: var(--orange);
}

/* 订单卡片 */
.orders-card {
  grid-column: span 2;
}

.order-stats {
  display: grid;
  grid-template-columns: repeat(5, 1fr);
  gap: 8px;
  text-align: center;
}

.stat-item {
  cursor: pointer;
  transition: color 0.2s;
}

.stat-item:hover {
  color: var(--orange);
}

.stat-count {
  font-size: 24px;
  font-weight: 500;
  color: var(--gray-800);
}

.stat-label {
  font-size: 12px;
  color: var(--gray-500);
  margin-top: 4px;
}

.order-footer {
  margin-top: 16px;
  padding-top: 16px;
  border-top: 1px solid var(--gray-100);
  text-align: center;
}

.empty-tip {
  color: var(--gray-400);
  font-size: 14px;
  margin-bottom: 8px;
}

.view-all-link {
  color: var(--orange);
  font-size: 14px;
  text-decoration: none;
}

.view-all-link:hover {
  text-decoration: underline;
}

/* 小型卡片 (收藏/足迹) */
.small-card {
  display: flex;
  flex-direction: column;
}

.empty-card-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 16px 0;
  flex: 1;
}

.empty-icon-wrapper {
  width: 64px;
  height: 64px;
  margin-bottom: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.empty-icon-wrapper.small {
  width: 48px;
  height: 48px;
}

.empty-icon {
  width: 48px;
  height: 48px;
  color: #d1d5db;
}

.empty-text {
  color: var(--gray-400);
  font-size: 14px;
  margin-bottom: 8px;
}

.browse-link {
  color: var(--orange);
  font-size: 14px;
  text-decoration: none;
}

.browse-link:hover {
  text-decoration: underline;
}

/* 右侧边栏 */
.right-sidebar {
  width: 220px;
  flex-shrink: 0;
}

.benefits-panel {
  background-color: var(--white);
  border-radius: 8px;
  padding: 16px;
}

.benefits-row {
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  border-bottom: 1px solid var(--gray-100);
  padding-bottom: 12px;
  margin-bottom: 12px;
}

.benefit-item {
  color: var(--orange);
}

.benefit-item.secondary {
  color: var(--gray-600);
}

.benefit-value {
  font-weight: 500;
}

.benefit-value.highlight {
  color: var(--orange);
}

.pending-tag-wrapper {
  text-align: right;
  margin-bottom: 12px;
}

.pending-tag {
  font-size: 12px;
  color: var(--orange);
  background-color: #fff7ed;
  padding: 4px 8px;
  border-radius: 4px;
}

/* 领券中心卡片 */
.coupon-card {
  display: flex;
  align-items: center;
  justify-content: space-between;
  background-color: var(--gray-50);
  border-radius: 8px;
  padding: 12px;
  margin-bottom: 12px;
}

.coupon-left {
  display: flex;
  align-items: center;
  gap: 8px;
}

.coupon-icon-circle {
  width: 32px;
  height: 32px;
  background-color: #ffedd5;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.coupon-icon {
  width: 16px;
  height: 16px;
  color: var(--orange);
}

.coupon-title {
  font-size: 14px;
  font-weight: 500;
  color: var(--gray-800);
}

.coupon-sub {
  font-size: 12px;
  color: var(--gray-500);
}

.get-coupon-btn {
  font-size: 12px;
  color: var(--orange);
  border: 1px solid var(--orange);
  background: transparent;
  padding: 4px 8px;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.2s;
}

.get-coupon-btn:hover {
  background-color: #fff7ed;
}

/* 购物车卡片 */
.cart-card {
  border: 1px solid var(--gray-100);
  border-radius: 8px;
  padding: 16px;
  background-color: var(--white);
}

.cart-empty {
  padding-top: 16px;
}

/* 通用图标尺寸 */
.icon-4 {
  width: 16px;
  height: 16px;
}

.icon-5 {
  width: 20px;
  height: 20px;
}
</style>