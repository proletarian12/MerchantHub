<template>
  <div class="admin-wrap">
    <!-- Header -->
    <header class="top-bar">
      <div class="top-inner" style="background: linear-gradient(135deg, #eff6ff 0%, #dbeafe 100%);;">
        <div class="logo-area">
          <div class="logo-icon">SHOPPING</div>
          <h1>商家管理后台</h1>
        </div>
        <div class="user-area">
          <span>MRJIANG</span>
          <button class="arrow-btn">
            <svg class="icon" fill="currentColor" viewBox="0 0 20 20">
              <path fill-rule="evenodd" d="M5.293 7.293a1 1 0 011.414 0L10 10.586l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z" clip-rule="evenodd" />
            </svg>
          </button>
        </div>
      </div>
    </header>

    <div class="body-flex">
      <!-- Sidebar -->
      <aside class="side">
        <nav class="side-nav">
          <ul>
            <li v-for="t in tabs" :key="t.key">
              <button
                @click="current = t.key"
                :class="['side-btn', { active: current === t.key }]"
              >
                <span class="btn-icon">{{ t.icon }}</span>
                {{ t.label }}
              </button>
            </li>
          </ul>
        </nav>
      </aside>

      <!-- Main -->
      <main class="main-area">
        <KeepAlive>
          <component :is="comMap[current]" />
        </KeepAlive>
      </main>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import AllGoods    from '@/components/S_appProduct.vue'
import AddGood     from '@/components/S_submitProduct.vue'
import MyAccount   from '@/components/S_sellerAccount.vue'

/* 按钮配置 */
const tabs = [
  { key: 'AllGoods',  label: '所有商品',  icon: '📦' },
  { key: 'AddGood',   label: '添加商品',  icon: '➕' },
  { key: 'MyAccount', label: '我的账户',  icon: '⚙️' }
]

/* 默认显示所有商品 */
const current = ref('AllGoods')

/* 动态组件映射 */
const comMap = ref({
  AllGoods,
  AddGood,
  MyAccount
})
</script>

<style>
  /* 通用工具 */
.admin-wrap{ min-height:100vh; background:#f7f7f7; }

/* 顶部栏 */
.top-bar{ background:#fff; border-bottom:1px solid #e5e5e5; }
.top-inner{ display:flex; align-items:center; justify-content:space-between; padding:8px 24px; }
.logo-area{ display:flex; align-items:center; gap:12px; }
.logo-icon{ width:80px; height:32px; background:#5488ff; color:#fff; border-radius:4px; display:flex; align-items:center; justify-content:center; font-size:14px; font-weight:700; }
.logo-area h1{ font-size:18px; color:#333; }
.user-area{ display:flex; align-items:center; gap:8px; font-size:14px; color:#666; }
.arrow-btn{ background:none; border:none; cursor:pointer; color:#999; }
.icon{ width:16px; height:16px; }

/* 主体左右分栏 */
.body-flex{ display:flex; }

/* 侧边栏 */
.side{ width:192px; background:#fff; box-shadow:2px 0 4px rgba(0,0,0,.05); min-height:calc(100vh - 49px); }
.side-nav ul{ list-style:none; padding:16px 0; margin:0; }
.side-btn{ width:100%; display:flex; align-items:center; gap:8px; padding:10px 20px; border:none; background:none; font-size:14px; color:#555; cursor:pointer; transition:background .2s; }
.side-btn:hover{ background:#fafafa; }
.side-btn.active{ background:#eef2ff; color:#2563eb; border-right:2px solid #2563eb; }
.btn-icon{ font-size:16px; }

/* 主内容区 */
.main-area{ flex:1; padding:24px; }
</style>