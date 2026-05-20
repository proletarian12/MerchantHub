<template>
  <div class="page-wrapper">
    <!-- Header Banner -->
    <header class="header-banner">
      <div class="header-inner">
        <div class="header-top">
          <div class="brand">
            <span class="brand-logo" @click="$router.push('/')" style="cursor: pointer;">商城</span>
            <div class="brand-divider"></div>
            <span class="brand-title">服务中心</span>
          </div>
          <span class="greeting">{{ greetingText }}，有什么可以帮助您？</span>
        </div>
      </div>
    </header>



    <!-- Search Section -->
    <div class="search-section">
      <div class="section-label">
        <span class="label-line"></span>
        <span class="label-text">搜索</span>
        <span class="label-line"></span>
      </div>

      <div class="search-box" :class="{ focused: isSearchFocused }">
        <input
          class="search-input"
          type="text"
          v-model="searchQuery"
          placeholder='请用一句话描述您的问题'
          @keyup.enter="handleSearch"
          @focus="isSearchFocused = true"
          @blur="isSearchFocused = false"
        />
        <button class="search-btn" @click="handleSearch" aria-label="搜索">
          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round">
            <circle cx="11" cy="11" r="8" />
            <line x1="21" y1="21" x2="16.65" y2="16.65" />
          </svg>
        </button>
      </div>

    </div>

    <!-- Tools Section -->
    <div class="tools-section">
      <div class="section-label">
        <span class="label-line"></span>
        <span class="label-text">常用工具</span>
        <span class="label-line"></span>
      </div>
      <div class="tools-grid">
        <div
          class="tool-item"
          v-for="tool in tools"
          :key="tool.label"
          @click="handleToolClick(tool)"
        >
          <div class="tool-icon-wrap">
            <svg v-html="tool.path" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"></svg>
          </div>
          <span class="tool-label">{{ tool.label }}</span>
        </div>
      </div>
    </div>

    <!-- Announcements Section -->
    <div class="announce-section">
      <div class="section-label">
        <span class="label-line"></span>
        <span class="label-text">最新公告</span>
        <span class="label-line"></span>
      </div>
      <div class="announce-list">
        <div
          class="announce-item"
          v-for="(item, idx) in announcements"
          :key="idx"
        >
          <span class="announce-tag" :class="item.type">{{ item.tag }}</span>
          <span class="announce-text">{{ item.text }}</span>
          <span class="announce-date">{{ item.date }}</span>
        </div>
      </div>
    </div>

    <!-- Footer -->

  </div>
</template>

<script setup>
import { ref, computed } from 'vue'

const searchQuery = ref('')
const isSearchFocused = ref(false)

const greetingText = computed(() => {
  const h = new Date().getHours()
  if (h < 6) return '凌晨好'
  if (h < 9) return '早上好'
  if (h < 12) return '上午好'
  if (h < 14) return '中午好'
  if (h < 18) return '下午好'
  if (h < 22) return '晚上好'
  return '夜深了'
})




const tools = [
  {
    label: '服务记录',
    path: `<path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"/><polyline points="14 2 14 8 20 8"/><line x1="9" y1="13" x2="15" y2="13"/><line x1="9" y1="17" x2="15" y2="17"/>`
  },
  {
    label: '申诉',
    path: `<path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z"/><line x1="12" y1="8" x2="12" y2="11"/><circle cx="12" cy="14" r="0.5" fill="currentColor"/>`
  },
  {
    label: '投诉',
    path: `<path d="M10.29 3.86L1.82 18a2 2 0 0 0 1.71 3h16.94a2 2 0 0 0 1.71-3L13.71 3.86a2 2 0 0 0-3.42 0z"/><line x1="12" y1="9" x2="12" y2="13"/><circle cx="12" cy="16" r="0.5" fill="currentColor"/>`
  }

]

const announcements = [
  { tag: '通知', type: 'notice', text: '....', date: '....' },
  { tag: '更新', type: 'update', text: '....', date: '....' }
]

function handleSearch() {
  if (searchQuery.value.trim()) {
    alert('正在搜索：' + searchQuery.value)
  }
}

function handleToolClick(tool) {
  alert('即将打开：' + tool.label)
}
</script>

<style scoped>
/* ===== Reset ===== */
*,
*::before,
*::after {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

.page-wrapper {
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', 'PingFang SC',
    'Hiragino Sans GB', 'Microsoft YaHei', 'Helvetica Neue', Helvetica, Arial,
    sans-serif;
  background: #eef1f5;
  color: #333;
  min-height: 100vh;
}

/* ===== Header Banner ===== */
.header-banner {
  position: relative;
  background: linear-gradient(135deg, #ff9500 0%, #ffda82 35%, #ffda82 65%, #e1ad00 100%);
  padding: 40px 0 160px;
  overflow: hidden;
}
.header-banner::before {
  content: '';
  position: absolute;
  top: -50%;
  right: -5%;
  width: 550px;
  height: 550px;
  background: radial-gradient(circle, rgba(255, 255, 255, 0.1) 0%, transparent 70%);
  border-radius: 50%;
}
.header-banner::after {
  content: '';
  position: absolute;
  bottom: -30%;
  left: 15%;
  width: 400px;
  height: 400px;
  background: radial-gradient(circle, rgba(255, 255, 255, 0.06) 0%, transparent 70%);
  border-radius: 50%;
}

.header-inner {
  max-width: 1100px;
  margin: 0 auto;
  padding: 0 24px;
  position: relative;
  z-index: 1;
}

.header-top {
  display: flex;
  align-items: center;
  gap: 24px;
}

.brand {
  display: flex;
  align-items: center;
  gap: 10px;
}
.brand-logo {
  font-size: 38px;
  font-weight: 700;
  color: #fff;
  letter-spacing: 2px;
}
.brand-sub {
  font-size: 10px;
  color: rgba(255, 255, 255, 0.7);
  letter-spacing: 1px;
  margin-left: -6px;
  align-self: flex-end;
  margin-bottom: 4px;
}
.brand-divider {
  width: 1px;
  height: 28px;
  background: rgba(255, 255, 255, 0.45);
  margin: 0 4px;
}
.brand-title {
  font-size: 22px;
  color: #fff;
  font-weight: 400;
}
.brand-badge {
  display: inline-block;
  background: #2dcc70;
  color: #fff;
  font-size: 11px;
  padding: 2px 8px;
  border-radius: 10px;
  margin-top: -14px;
  margin-left: -4px;
  font-weight: 500;
}

.greeting {
  font-size: 20px;
  color: rgba(255, 255, 255, 0.92);
  margin-left: auto;
  font-weight: 300;
}

/* ===== Service Cards ===== */
.service-cards {
  display: flex;
  gap: 20px;
  max-width: 1100px;
  margin: -120px auto 0;
  padding: 0 24px;
  position: relative;
  z-index: 2;
}

.service-card {
  flex: 1;
  background: #fff;
  border-radius: 12px;
  padding: 40px 24px 28px;
  text-align: center;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 2px 16px rgba(0, 0, 0, 0.06);
  border: 1.5px solid transparent;
}
.service-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
  border-color: #4a8cc7;
}

.card-icon {
  width: 80px;
  height: 80px;
  margin: 0 auto 18px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
}
.card-icon.blue {
  background: linear-gradient(135deg, #e8f0fe, #d0e2f4);
}
.card-icon.green {
  background: linear-gradient(135deg, #e8f5e9, #c8e6c9);
}
.card-icon.orange {
  background: linear-gradient(135deg, #fff3e0, #ffe0b2);
}

.card-title {
  font-size: 18px;
  font-weight: 600;
  color: #222;
  margin-bottom: 8px;
}
.card-desc {
  font-size: 13px;
  color: #999;
}

/* ===== Section Label ===== */
.section-label {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 14px;
  margin-bottom: 22px;
}
.label-line {
  width: 40px;
  height: 1px;
  background: #c8c8c8;
}
.label-text {
  font-size: 15px;
  color: #666;
  white-space: nowrap;
}

/* ===== Search Section ===== */
.search-section {
  max-width: 1100px;
  margin: 44px auto 0;
  padding: 0 24px;
  display: flex;
  flex-direction: column;
  align-items: center;

  
}

.search-box {
  display: flex;
  align-items: center;
  background: #fff;
  border-radius: 8px;
  padding: 5px 5px 5px 20px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
  border: 1.5px solid #e0e0e0;
  width:100%;
  transition: border-color 0.3s, box-shadow 0.3s;
}
.search-box.focused {
  border-color: #4a8cc7;
  box-shadow: 0 2px 18px rgba(74, 140, 199, 0.15);
}

.search-input {
  flex: 1;
  border: none;
  outline: none;
  font-size: 15px;
  color: #333;
  padding: 12px 0;
  background: transparent;
  font-family: inherit;
}
.search-input::placeholder {
  color: #bbb;
}

.search-btn {
  width: 54px;
  height: 44px;
  border: none;
  background: #eca900;
  color: #fff;
  border-radius: 8px;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: background 0.3s;
  flex-shrink: 0;
}
.search-btn:hover {
  background: #3b7ab3;
}
.search-btn svg {
  width: 20px;
  height: 20px;
}

.hot-tags {
  display: flex;
  gap: 24px;
  margin-top: 14px;
  padding-left: 2px;
}
.hot-tag {
  font-size: 13px;
  color: #666;
  cursor: pointer;
  transition: color 0.2s;
}
.hot-tag:hover {
  color: #4a8cc7;
}

/* ===== Tools Section ===== */
.tools-section {
  max-width: 1100px;
  margin: 48px auto 0;
  padding: 0 24px;
}

.tools-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 28px 0;
  max-width: 780px;
  margin: 0 auto;
}

.tool-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 12px;
  cursor: pointer;
}

.tool-icon-wrap {
  width: 64px;
  height: 64px;
  border-radius: 50%;
  border: 2px solid #f2e8cd;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #fff;
  transition: all 0.3s;
  color: #ffc43a;
}
.tool-icon-wrap svg {
  width: 26px;
  height: 26px;
}
.tool-item:hover .tool-icon-wrap {
  border-color: #ffb811;
  background: #f0f7ff;
  transform: translateY(-3px);
  box-shadow: 0 4px 14px rgba(74, 140, 199, 0.18);
}
.tool-label {
  font-size: 14px;
  color: #555;
  transition: color 0.2s;
}
.tool-item:hover .tool-label {
  color: #4a8cc7;
}

/* ===== Announcements ===== */
.announce-section {
  max-width: 1100px;
  margin: 48px auto 0;
  padding: 0 24px 60px;
}

.announce-list {
  background: #fff;
  border-radius: 12px;
  padding: 20px 28px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.04);
}

.announce-item {
  display: flex;
  align-items: center;
  padding: 14px 0;
  border-bottom: 1px solid #f2f2f2;
  cursor: pointer;
  transition: background 0.2s;
}
.announce-item:last-child {
  border-bottom: none;
}
.announce-item:hover {
  background: #fafbfd;
}

.announce-tag {
  display: inline-block;
  padding: 2px 10px;
  border-radius: 4px;
  font-size: 12px;
  margin-right: 14px;
  flex-shrink: 0;
  font-weight: 500;
}
.announce-tag.notice {
  background: #e8f4fd;
  color: #4a8cc7;
}
.announce-tag.update {
  background: #fff3e0;
  color: #f57c00;
}
.announce-tag.important {
  background: #fce4ec;
  color: #e53935;
}

.announce-text {
  flex: 1;
  font-size: 14px;
  color: #444;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.announce-date {
  font-size: 13px;
  color: #aaa;
  margin-left: 20px;
  flex-shrink: 0;
}

/* ===== Footer ===== */
.page-footer {
  text-align: center;
  padding: 24px 20px;
  color: #aaa;
  font-size: 13px;
  background: #eef1f5;
  border-top: 1px solid #dde2e8;
}

/* ===== Responsive ===== */
@media (max-width: 768px) {
  .header-top {
    flex-wrap: wrap;
  }
  .greeting {
    margin-left: 0;
    width: 100%;
    font-size: 16px;
    margin-top: 8px;
  }
  .service-cards {
    flex-direction: column;
    margin-top: -100px;
  }
  .tools-grid {
    grid-template-columns: repeat(3, 1fr);
  }
  .hot-tags {
    flex-wrap: wrap;
    gap: 12px;
  }
}

@media (max-width: 480px) {
  .tools-grid {
    grid-template-columns: repeat(2, 1fr);
  }
  .brand-logo {
    font-size: 28px;
  }
  .brand-title {
    font-size: 17px;
  }
}
</style>
