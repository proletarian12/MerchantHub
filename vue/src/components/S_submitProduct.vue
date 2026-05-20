<template>
  <div class="add-product-wrap">
    <!-- Top Bar -->
    <div class="top-bar">
      <div class="top-inner">
        <h2>添加产品</h2>
        <div class="user">MRUIANG <span class="arrow">▼</span></div>
      </div>
    </div>

    <!-- Form Content -->
    <div class="form-area">
      <div class="form-card">
        <!-- Product Type and Status -->
        <div class="row-2">
          <div class="col">
            <div class="type-box">
              <label>产品类型</label>
              <span class="val">女装/大衣</span>
            </div>

            <div class="status-box">
              <label>产品状态</label>
              <div class="radio-group">
                <label><input type="radio" name="status" value="selling" checked> 销售中</label>
                <label><input type="radio" name="status" value="paused"> 停售中</label>
                <label><input type="radio" name="status" value="outofstock"> 缺货中</label>
              </div>
            </div>
          </div>
        </div>

        <!-- Basic Information -->
        <div class="section">
          <h3>基本信息</h3>
          <div class="row-2">
            <div class="col">
              <div class="field">
                <label>产品名称</label>
                <input type="text" v-model="form.productName" class="input-text">
              </div>
              <div class="field">
                <label>产品价格</label>
                <div class="input-wrap">
                  <input type="number" v-model.number="form.price" class="input-text">
                  <span class="unit">元</span>
                </div>
              </div>
              <div class="field">
                <label>产品描述</label>
                <input type="text" v-model="form.description" class="input-text">
              </div>
            </div>
          </div>
        </div>

        <!-- Pictures -->
        <div class="section">
          <h3>概述图片</h3>
          <input type="file" accept="image/*" @change="onOverviewChange" class="file-input">
          <h3>详情图片</h3>
          <input type="file" accept="image/*" multiple @change="onDetailChange" class="file-input">
        </div>

        <!-- Actions -->
        <div class="actions">
          <button class="btn save" @click="upload">保存</button>
          <button class="btn cancel" @click="cancel">取消</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
  import { ref } from 'vue'
  
  const form = ref({
    productName: '',
    description: '',
    price: '',
    overviewFile: null,
    detailFiles: []
  })
  
  function onOverviewChange(e) {
    form.value.overviewFile = e.target.files[0] || null
  }
  function onDetailChange(e) {
    form.value.detailFiles = Array.from(e.target.files)
  }
  
  async function upload() {
    const fd = new FormData()
    fd.append('productName', form.value.productName)
    fd.append('price', form.value.price)
    fd.append('description', form.value.description)
    if (form.value.overviewFile) fd.append('overviewImage', form.value.overviewFile)
    form.value.detailFiles.forEach(f => fd.append('detailImages', f))
  
    await fetch('/api/productUpload', { method: 'POST', body: fd })
    alert('已提交')
  }
  function cancel() {
    // 重置表单或返回上一页
    Object.keys(form.value).forEach(k => form.value[k] = k === 'detailFiles' ? [] : '')
  }
  </script>

<style scoped>
/* Custom radio button styling */
input[type="radio"]:checked {
  background-color: #fb923c;
  border-color: #fb923c;
}

input[type="radio"]:focus {
  box-shadow: 0 0 0 3px rgba(251, 146, 60, 0.1);
}
.add-product-wrap{ min-height:100vh; background:#fafafa; }

/* 顶部栏 */
.top-bar{ background:#fff; border-bottom:1px solid #e5e5e5; }
.top-inner{ display:flex; justify-content:space-between; align-items:center; padding:12px 24px; }
.top-inner h2{ font-size:20px; font-weight:500; color:#333; }
.user{ font-size:14px; color:#666; }
.arrow{ margin-left:4px; font-size:12px; }

/* 表单区 */
.form-area{ padding:24px; }
.form-card{ background:#fff; border-radius:8px; box-shadow:0 2px 8px rgba(0,0,0,.05); padding:24px; }

.row-2{ display:grid; grid-template-columns:1fr 1fr; gap:32px; margin-bottom:24px; }
.col{ display:flex; flex-direction:column; gap:16px; }

/* 字段 */
.field label{ font-size:14px; color:#555; margin-bottom:6px; display:block; }
.input-text{ width:100%; padding:8px 12px; border:1px solid #ccc; border-radius:4px; font-size:14px; }
.input-text:focus{ outline:none; border-color:#fb923c; }
.input-wrap{ position:relative; }
.unit{ position:absolute; right:12px; top:8px; font-size:14px; color:#888; pointer-events:none; }

/* 类型/状态 */
.type-box label{ font-size:14px; color:#555; }
.type-box .val{ font-size:14px; color:#222; margin-left:8px; }
.status-box label{ font-size:14px; color:#555; margin-bottom:8px; display:block; }
.radio-group{ display:flex; gap:16px; }
.radio-group label{ font-size:14px; cursor:pointer; display:flex; align-items:center; gap:6px; }

/* 图片上传 */
.section{ margin-bottom:24px; }
.section h3{ font-size:16px; font-weight:600; color:#333; margin:16px 0 8px; }
.file-input{ font-size:14px; }

/* 按钮区 */
.actions{ display:flex; gap:16px; margin-top:32px; }
.btn{ padding:8px 24px; border:none; border-radius:4px; font-size:14px; cursor:pointer; transition:background .2s; }
.save{ background:#fb923c; color:#fff; }
.save:hover{ background:#f97316; }
.cancel{ background:#fff; color:#666; border:1px solid #bbb; }
.cancel:hover{ background:#f7f7f7; }

/* 自定义 radio 颜色 */
input[type="radio"]:checked{ accent-color:#fb923c; }
</style>