<template>
  <!-- Main Content -->
  <main class="main-content">
    <h2 class="page-title">所有产品</h2>

    <!-- Filters -->
    <div class="filter-card">
      <div class="filter-row">
        <div class="filter-item">
          <label>产品名称</label>
          <input v-model="filters.productName" type="text" class="input-text">
        </div>
        <div class="filter-item">
          <label>产品类型</label>
          <select v-model="filters.category" class="select-box">
            <option value="">全部</option>
            <option value="clothing">服装</option>
            <option value="accessories">配饰</option>
          </select>
        </div>
        <button class="btn btn-primary">筛选</button>
        <button class="btn btn-secondary">重置</button>
      </div>

      <!-- Status Tabs -->
      <div class="filter-row">
        <span class="label">产品状态</span>
        <button
          v-for="status in statusTabs"
          :key="status.key"
          @click="activeStatus = status.key"
          :class="['tab-btn', { active: activeStatus === status.key }]"
        >
          {{ status.label }}
        </button>
        <span class="total">共50件</span>
      </div>

      <!-- Action Buttons -->
      <div class="action-row">
        <button class="btn btn-success">添加一件产品</button>
        <button class="btn btn-info">编辑产品列表</button>
      </div>
    </div>

    <!-- Search Results -->
    <div class="result-tips">
      <span>🔍 查看合计 产品总数 {{products.length}} 件</span>
    </div>

    <!-- Product Table -->
    <div class="table-wrapper">
      <table class="data-table">
        <thead>
          <tr>
            <th>产品名称</th>
            <th>产品标题</th>
            <th>原价</th>
            <th>促销价</th>
            <th>创建时间</th>
            <th>上架状态</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="product in products" :key="product.id">
            <td>{{ product.name }}</td>
            <td>{{ product.title }}</td>
            <td>{{ product.originalPrice }}</td>
            <td>{{ product.salePrice }}</td>
            <td>{{ product.createdAt }}</td>
            <td>
              <span class="status-green">{{ product.status }}</span>
            </td>
            <td>
              <button class="link-btn">详情</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </main>
</template>

<script setup>
import { ref, reactive ,onMounted} from 'vue'

// Reactive data
const filters = reactive({
  productName: '',
  category: ''
})


const products = ref([]);
const activeStatus = ref('selling')

const statusTabs = [
  { key: 'selling', label: '销售中' },
  { key: 'discontinued', label: '停售中' },
]

function product(){
  fetch('/api/product')
    .then(res => {
      if (!res.ok) throw new Error(res.status + ' ' + res.statusText)
      return res.json()   // 只有 ok 才继续
    })
    .then(data => { products.value = data.values })
    .catch(err => {
      console.error(err)
      products.value = []
    })
}



onMounted(()=>{
    product()
})
</script>

<style scoped>
/* 整体布局 */
.main-content {
  flex: 1;
  padding: 24px;
  background: #f7f7f7;
  min-height: 100vh;
}
.page-title {
  font-size: 20px;
  font-weight: 500;
  color: #333;
  margin-bottom: 24px;
}

/* 通用卡片 */
.filter-card {
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0,0,0,.05);
  padding: 16px;
  margin-bottom: 24px;
}
.filter-row {
  display: flex;
  align-items: center;
  gap: 16px;
  margin-bottom: 16px;
}
.filter-item {
  display: flex;
  align-items: center;
  gap: 8px;
}
.filter-item label,
.label {
  font-size: 14px;
  color: #666;
  white-space: nowrap;
}
.input-text,
.select-box {
  border: 1px solid #ccc;
  border-radius: 4px;
  padding: 4px 8px;
  font-size: 14px;
  width: 140px;
}

/* 按钮 */
.btn {
  border: none;
  border-radius: 4px;
  padding: 6px 16px;
  font-size: 14px;
  cursor: pointer;
  transition: background .2s;
}
.btn-primary { background: #fb923c; color: #fff; }
.btn-primary:hover { background: #f97316; }
.btn-secondary { background: #e5e7eb; color: #374151; }
.btn-secondary:hover { background: #d1d5db; }
.btn-success { background: #22c55e; color: #fff; }
.btn-success:hover { background: #16a34a; }
.btn-info { background: #3b82f6; color: #fff; }
.btn-info:hover { background: #2563eb; }

/* 状态标签 */
.tab-btn {
  padding: 4px 12px;
  font-size: 14px;
  border-radius: 4px;
  border: 1px solid transparent;
  background: transparent;
  color: #666;
  cursor: pointer;
}
.tab-btn.active {
  background: #fee2e2;
  color: #dc2626;
}
.total { font-size: 14px; color: #999; margin-left: auto; }

/* 表格 */
.table-wrapper { background: #fff; border-radius: 8px; overflow: hidden; }
.data-table { width: 100%; border-collapse: collapse; }
.data-table th {
  background: #f9fafb;
  padding: 12px 16px;
  text-align: left;
  font-size: 14px;
  font-weight: 500;
  color: #374151;
}
.data-table td {
  padding: 12px 16px;
  font-size: 14px;
  color: #333;
}
.data-table tr:hover { background: #fafafa; }
.status-green {
  display: inline-block;
  padding: 2px 8px;
  font-size: 12px;
  font-weight: 500;
  border-radius: 12px;
  background: #d1fae5;
  color: #065f46;
}
.link-btn {
  background: none;
  border: none;
  color: #ef4444;
  cursor: pointer;
  font-size: 14px;
}
.link-btn:hover { color: #b91c1c; }

/* 结果提示 */
.result-tips { margin-bottom: 12px; font-size: 14px; color: #666; }
.text-balance {
  text-wrap: balance;
}
</style>