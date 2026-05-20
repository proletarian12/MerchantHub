<template>
  <div class="register-container">
    <!-- Header -->
    <div class="header">
      <div class="logo">
        <span class="logo-text">商城</span>
        <span class="logo-sub">Shopping</span>
      </div>
      <span class="page-title">{{ isLogin ? '用户登录' : '用户注册' }}</span>
    </div>

    <!-- Tab Switch -->
    <div class="tab-wrapper">
      <div class="tab-container">
        <button 
          class="tab-btn" 
          :class="{ active: isLogin }" 
          @click="isLogin = true"
        >
          登录
        </button>
        <button 
          class="tab-btn" 
          :class="{ active: !isLogin }" 
          @click="isLogin = false"
        >
          注册
        </button>
      </div>
    </div>

    <!-- Form -->
    <div class="form-wrapper">
      <!-- Phone Number -->
      <div class="form-row">
        <label class="form-label">手机号码</label>
        <div class="input-group">
          <div class="country-select" @click="showCountryDropdown = !showCountryDropdown">
            <span>{{ selectedCountry.name }}</span>
            <span class="country-code">{{ selectedCountry.code }}</span>
            <span class="arrow">▼</span>
            <div class="dropdown" v-if="showCountryDropdown">
              <div 
                class="dropdown-item" 
                v-for="country in countries" 
                :key="country.code"
                @click.stop="selectCountry(country)"
              >
                {{ country.name }} {{ country.code }}
              </div>
            </div>
          </div>
          <input 
            type="text" 
            class="phone-input" 
            placeholder="请输入你的手机号码"
            v-model="phoneNumber"
          />
        </div>
      </div>

      <!-- Password (Login only) -->
      <div class="form-row" v-if="isLogin && !useVerifyCode">
        <label class="form-label">登录密码</label>
        <div class="input-group">
          <input 
            type="password" 
            class="code-input full-width" 
            placeholder="请输入登录密码"
            v-model="password"
          />
        </div>
      </div>

      <!-- Verification Code -->
      <div class="form-row" v-if="!isLogin || useVerifyCode">
        <label class="form-label">验证码</label>
        <div class="input-group">
          <input 
            type="text" 
            class="code-input" 
            placeholder="请输入验证码"
            v-model="verifyCode"
          />
          <button 
            class="get-code-btn" 
            @click="getVerifyCode"
            :disabled="countdown > 0"
          >
            {{ countdown > 0 ? `${countdown}秒后重试` : '获取验证码' }}
          </button>
        </div>
      </div>

      <!-- Login method toggle -->
      <div class="form-row toggle-row" v-if="isLogin">
        <span class="toggle-link" @click="useVerifyCode = !useVerifyCode">
          {{ useVerifyCode ? '使用密码登录' : '使用验证码登录' }}
        </span>
        <span class="toggle-link">忘记密码？</span>
      </div>

      <!-- Submit Button -->
      <div class="form-row submit-row">
        <button class="submit-btn" @click="handleSubmit">
          {{ isLogin ? '登录' : '同意并注册' }}
        </button>
      </div>

      <!-- Agreement (Register only) -->
      <div class="form-row agreement-row" v-if="!isLogin">
        <label class="checkbox-wrapper">
          <input type="checkbox" v-model="agreed" />
          <span class="checkmark"></span>
        </label>
        <span class="agreement-text">
          已阅读并同意以下协议
          <a href="#" class="link">淘宝平台服务协议</a>、
          <a href="#" class="link">隐私权政策</a>、
          <a href="#" class="link">法律声明</a>、
          <a href="#" class="link">支付宝及客户端服务协议</a>
        </span>
      </div>

      <!-- Switch hint -->
      <div class="form-row switch-row">
        <span class="switch-text">
          {{ isLogin ? '还没有账号？' : '已有账号？' }}
          <a href="#" class="link" @click.prevent="isLogin = !isLogin">
            {{ isLogin ? '立即注册' : '立即登录' }}
          </a>
        </span>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, computed } from 'vue'

// 状态管理
const isLogin = ref(true)
const useVerifyCode = ref(false)
const agreed = ref(false)
const countdown = ref(0)
const showCountryDropdown = ref(false)
const loading = ref(false)


// 表单数据
const formData = reactive({
  phone: '',
  password: '',
  verifyCode: '',
  nickname: ''
})

// 国家列表
const countries = ref([
  { name: '中国大陆', code: '+86' },
  { name: '中国香港', code: '+852' },
  { name: '中国台湾', code: '+886' },
  { name: '美国', code: '+1' },
])

const selectedCountry = ref({ name: '中国大陆', code: '+86' })

// 计算属性
const isPhoneValid = computed(() => {
  const phone = formData.phone
  return phone && /^1[3-9]\d{9}$/.test(phone)
})

// 方法
const selectCountry = (country) => {
  selectedCountry.value = country
  showCountryDropdown.value = false
}

// 获取验证码
const getVerifyCode = async () => {
  if (!isPhoneValid.value) {
    alert('请输入正确的手机号码')
    return
  }
  
  try {
    countdown.value = 60
    const timer = setInterval(() => {
      countdown.value--
      if (countdown.value <= 0) {
        clearInterval(timer)
      }
    }, 1000)
    
    alert('验证码已发送')
  } catch (error) {
    alert('验证码发送失败，请重试')
  }
}

// 处理提交
const handleSubmit = async () => {
  // 验证手机号
  if (!formData.phone) {
    alert('请输入手机号码')
    return
  }
  
  if (!isPhoneValid.value) {
    alert('请输入正确的手机号码')
    return
  }
  
  // 密码验证
  if (!isLogin.value || !useVerifyCode.value) {
    if (!formData.password) {
      alert('请输入密码')
      return
    }
    if (!isLogin.value && formData.password.length < 6) {
      alert('密码长度不能小于6位')
      return
    }
  }
  
  // 验证码验证
  if ((!isLogin.value || useVerifyCode.value) && !formData.verifyCode) {
    alert('请输入验证码')
    return
  }
  
  // 注册时验证协议同意
  if (!isLogin.value && !agreed.value) {
    alert('请阅读并同意相关协议')
    return
  }
  
  loading.value = true
  
  try {
    let response
    let data
    
    if (isLogin.value) {
      // 登录请求
      response = await fetch('http://localhost:8080/api/user/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          phone: formData.phone,
          password: formData.password
        })
      })
      
      data = await response.json()
      
      if (data.code === 200) {
        // 保存token到localStorage
        localStorage.setItem('token', data.data.token)
        localStorage.setItem('userInfo', JSON.stringify(data.data))
        
        alert('登录成功！')
        // 跳转到首页
        // window.location.href = '/'
      } else {
        alert(data.message || '登录失败')
      }
    } else {
      // 注册请求
      response = await fetch('http://localhost:8080/api/user/register', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          phone: formData.phone,
          password: formData.password,
          nickname: formData.nickname || `用户${formData.phone.substring(7)}`
        })
      })
      
      data = await response.json()
      
      if (data.code === 200) {
        // 保存token到localStorage
        localStorage.setItem('token', data.data.token)
        localStorage.setItem('userInfo', JSON.stringify(data.data))
        
        alert('注册成功！')
        // 跳转到首页
        // window.location.href = '/'
      } else {
        alert(data.message || '注册失败')
      }
    }
  } catch (error) {
    console.error('请求失败:', error)
    alert('网络错误，请检查后端服务是否启动')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

.register-container {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
  font-family: "Microsoft YaHei", "PingFang SC", sans-serif;
}

.header {
  display: flex;
  align-items: center;
  padding-bottom: 20px;
  border-bottom: 1px solid #eee;
  margin-bottom: 60px;
}

.logo {
  display: flex;
  flex-direction: column;
  margin-right: 20px;
}

.logo-text {
  font-size: 32px;
  font-weight: bold;
  color: #ffaa00;
}

.logo-sub {
  font-size: 12px;
  color: #ffaa00;
  letter-spacing: 2px;
}

.page-title {
  font-size: 20px;
  color: #333;
  padding-left: 20px;
  border-left: 1px solid #ddd;
}

.tab-wrapper {
  max-width: 600px;
  margin: 0 auto 30px;
  padding-left: 95px;
}

.tab-container {
  display: flex;
  gap: 0;
  border-bottom: 1px solid #eee;
}

.tab-btn {
  padding: 12px 30px;
  background: none;
  border: none;
  font-size: 16px;
  color: #666;
  cursor: pointer;
  position: relative;
  transition: color 0.3s;
}

.tab-btn:hover {
  color: #ff5000;
}

.tab-btn.active {
  color: #ff5000;
  font-weight: 500;
}

.tab-btn.active::after {
  content: '';
  position: absolute;
  bottom: -1px;
  left: 0;
  right: 0;
  height: 2px;
  background: #ff5000;
}

.form-wrapper {
  max-width: 600px;
  margin: 0 auto;
}

.form-row {
  display: flex;
  align-items: center;
  margin-bottom: 25px;
}

.form-label {
  width: 80px;
  text-align: right;
  margin-right: 15px;
  color: #666;
  font-size: 14px;
}

.input-group {
  display: flex;
  flex: 1;
  align-items: center;
}

.country-select {
  display: flex;
  align-items: center;
  padding: 10px 12px;
  border: 1px solid #ddd;
  border-radius: 4px 0 0 4px;
  background: #fff;
  cursor: pointer;
  position: relative;
  min-width: 140px;
}

.country-select:hover {
  border-color: #ff5000;
}

.country-code {
  margin-left: 8px;
  color: #333;
}

.arrow {
  font-size: 10px;
  color: #999;
  margin-left: 8px;
}

.dropdown {
  position: absolute;
  top: 100%;
  left: -1px;
  right: -1px;
  background: #fff;
  border: 1px solid #ddd;
  border-top: none;
  z-index: 10;
  border-radius: 0 0 4px 4px;
}

.dropdown-item {
  padding: 10px 12px;
  cursor: pointer;
}

.dropdown-item:hover {
  background: #f5f5f5;
}

.phone-input {
  flex: 1;
  padding: 10px 12px;
  border: 1px solid #ddd;
  border-left: none;
  border-radius: 0 4px 4px 0;
  font-size: 14px;
  outline: none;
}

.phone-input:focus {
  border-color: #ff5000;
}

.phone-input::placeholder {
  color: #ccc;
}

.code-input {
  flex: 1;
  padding: 10px 12px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 14px;
  outline: none;
}

.code-input:focus {
  border-color: #ff5000;
}

.code-input::placeholder {
  color: #ccc;
}

.get-code-btn {
  margin-left: 15px;
  padding: 10px 20px;
  background: #fff;
  border: 1px solid #ff5000;
  color: #ff5000;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
  white-space: nowrap;
}

.get-code-btn:hover:not(:disabled) {
  background: #fff5f0;
}

.get-code-btn:disabled {
  border-color: #ccc;
  color: #ccc;
  cursor: not-allowed;
}

.submit-row {
  margin-top: 40px;
  padding-left: 95px;
}

.submit-btn {
  width: 100%;
  max-width: 400px;
  padding: 12px;
  background: linear-gradient(to right, #ffaa55, #ff8800);
  border: none;
  border-radius: 4px;
  color: #fff;
  font-size: 16px;
  cursor: pointer;
  transition: opacity 0.3s;
}

.submit-btn:hover {
  opacity: 0.9;
}

.agreement-row {
  padding-left: 95px;
  align-items: flex-start;
}

.checkbox-wrapper {
  position: relative;
  display: inline-block;
  width: 16px;
  height: 16px;
  margin-right: 8px;
  margin-top: 2px;
}

.checkbox-wrapper input {
  opacity: 0;
  width: 100%;
  height: 100%;
  cursor: pointer;
}

.checkmark {
  position: absolute;
  top: 0;
  left: 0;
  width: 16px;
  height: 16px;
  border: 1px solid #ddd;
  border-radius: 2px;
  pointer-events: none;
}

.checkbox-wrapper input:checked + .checkmark {
  background: #ff5000;
  border-color: #ff5000;
}

.checkbox-wrapper input:checked + .checkmark::after {
  content: '';
  position: absolute;
  left: 5px;
  top: 2px;
  width: 4px;
  height: 8px;
  border: solid #fff;
  border-width: 0 2px 2px 0;
  transform: rotate(45deg);
}

.agreement-text {
  flex: 1;
  font-size: 12px;
  color: #999;
  line-height: 1.8;
}

.link {
  color: #ff5000;
  text-decoration: none;
}

.link:hover {
  text-decoration: underline;
}

.toggle-row {
  padding-left: 95px;
  justify-content: space-between;
  max-width: 495px;
}

.toggle-link {
  font-size: 12px;
  color: #ff5000;
  cursor: pointer;
}

.toggle-link:hover {
  text-decoration: underline;
}

.switch-row {
  padding-left: 95px;
  justify-content: center;
  margin-top: 20px;
}

.switch-text {
  font-size: 14px;
  color: #999;
}

.full-width {
  width: 100%;
}
</style>
