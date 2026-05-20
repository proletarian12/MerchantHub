<template>
  <div class="auth-container">
    <!-- 左上角Logo -->
    <div class="logo">
      <svg
        xmlns="http://www.w3.org/2000/svg"
        width="40"
        height="40"
        viewBox="0 0 24 24"
        fill="none"
        stroke="currentColor"
        stroke-width="2"
        stroke-linecap="round"
        stroke-linejoin="round"
      >
        <circle cx="8" cy="21" r="1" />
        <circle cx="19" cy="21" r="1" />
        <path d="M2.05 2.05h2l2.66 12.42a2 2 0 0 0 2 1.58h9.78a2 2 0 0 0 1.95-1.57l1.65-7.43H5.12" />
      </svg>
      <span class="logo-text">SHOPPING</span>
    </div>

    <!-- 登录/注册卡片 -->
    <div class="auth-card">
      <h1 class="title">商家{{ isLogin ? '登录' : '注册' }}</h1>
      <p class="subtitle">{{ isLogin ? '欢迎回来，请登录您的账户' : '创建您的商家账户，开启您的商业之旅' }}</p>

      <form @submit.prevent="handleSubmit" class="form">
        <!-- 手机号输入 -->
        <div class="input-group">
          <label for="phone">手机号</label>
          <div class="phone-input">
            <span class="country-code">+86</span>
            <input
              id="phone"
              v-model="phone"
              type="tel"
              placeholder="请输入手机号"
              maxlength="11"
            />
          </div>
        </div>

        <!-- 登录方式切换（仅登录时显示） -->
        <div v-if="isLogin" class="login-method-tabs">
          <button
            type="button"
            :class="['tab-btn', { active: loginMethod === 'code' }]"
            @click="loginMethod = 'code'"
          >
            验证码登录
          </button>
          <button
            type="button"
            :class="['tab-btn', { active: loginMethod === 'password' }]"
            @click="loginMethod = 'password'"
          >
            密码登录
          </button>
        </div>

        <!-- 验证码输入（登录选择验证码方式 或 注册时必填） -->
        <div v-if="!isLogin || loginMethod === 'code'" class="input-group">
          <label for="code">短信验证码</label>
          <div class="code-input">
            <input
              id="code"
              v-model="code"
              type="text"
              placeholder="请输入验证码"
              maxlength="6"
            />
            <button
              type="button"
              class="code-btn"
              :disabled="countdown > 0"
              @click="sendCode"
            >
              {{ countdown > 0 ? `${countdown}s` : '获取验证码' }}
            </button>
          </div>
        </div>

        <!-- 密码输入（登录选择密码方式 或 注册时必填） -->
        <div v-if="!isLogin || loginMethod === 'password'" class="input-group">
          <label for="password">{{ isLogin ? '密码' : '设置密码' }}</label>
          <input
            id="password"
            v-model="password"
            type="password"
            :placeholder="isLogin ? '请输入密码' : '请设置登录密码（6-20位）'"
            maxlength="20"
          />
        </div>

        <!-- 确认密码（仅注册时显示） -->
        <div v-if="!isLogin" class="input-group">
          <label for="confirmPassword">确认密码</label>
          <input
            id="confirmPassword"
            v-model="confirmPassword"
            type="password"
            placeholder="请再次输入密码"
            maxlength="20"
          />
        </div>

        <!-- 注册时显示店铺名称 -->
        <div v-if="!isLogin" class="input-group">
          <label for="shopName">店铺名称</label>
          <input
            id="shopName"
            v-model="shopName"
            type="text"
            placeholder="请输入您的店铺名称"
          />
        </div>

        <!-- 提交按钮 -->
        <button type="submit" class="submit-btn">
          {{ isLogin ? '登录' : '注册' }}
        </button>
      </form>

      <!-- 切换登录/注册 -->
      <div class="switch-mode">
        <span>{{ isLogin ? '还没有账户？' : '已有账户？' }}</span>
        <a href="#" @click.prevent="toggleMode">
          {{ isLogin ? '立即注册' : '立即登录' }}
        </a>
      </div>

      <!-- 协议 -->
      <p class="agreement">
        {{ isLogin ? '登录' : '注册' }}即表示您同意
        <a href="#">《用户服务协议》</a>和<a href="#">《隐私政策》</a>
      </p>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const isLogin = ref(true)
const loginMethod = ref('code') // 'code' 或 'password'
const phone = ref('')
const code = ref('')
const password = ref('')
const confirmPassword = ref('')
const shopName = ref('')
const countdown = ref(0)

const toggleMode = () => {
  isLogin.value = !isLogin.value
  phone.value = ''
  code.value = ''
  password.value = ''
  confirmPassword.value = ''
  shopName.value = ''
  loginMethod.value = 'code'
}

const sendCode = () => {
  if (!phone.value || phone.value.length !== 11) {
    alert('请输入正确的手机号')
    return
  }
  
  countdown.value = 60
  const timer = setInterval(() => {
    countdown.value--
    if (countdown.value <= 0) {
      clearInterval(timer)
    }
  }, 1000)
}

const handleSubmit = () => {
  if (!phone.value || phone.value.length !== 11) {
    alert('请输入正确的手机号')
    return
  }

  if (isLogin.value) {
    // 登录逻辑
    if (loginMethod.value === 'code') {
      if (!code.value || code.value.length !== 6) {
        alert('请输入6位验证码')
        return
      }
    } else {
      if (!password.value || password.value.length < 6) {
        alert('请输入密码（至少6位）')
        return
      }
    }
    alert('登录成功！')
  } else {
    // 注册逻辑 - 验证码和密码都必填
    if (!code.value || code.value.length !== 6) {
      alert('请输入6位验证码')
      return
    }
    if (!password.value || password.value.length < 6) {
      alert('请设置密码（至少6位）')
      return
    }
    if (password.value !== confirmPassword.value) {
      alert('两次输入的密码不一致')
      return
    }
    if (!shopName.value) {
      alert('请输入店铺名称')
      return
    }
    alert('注册成功！')
  }
}
</script>

<style scoped>
.auth-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #eff6ff 0%, #dbeafe 100%);
  padding: 24px;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
}

.logo {
  display: flex;
  align-items: center;
  gap: 10px;
  color: #2563eb;
}

.logo-text {
  font-size: 24px;
  font-weight: 700;
  letter-spacing: 1px;
}

.auth-card {
  max-width: 420px;
  margin: 60px auto 0;
  background: #ffffff;
  border-radius: 16px;
  padding: 40px;
  box-shadow: 0 10px 40px rgba(37, 99, 235, 0.1);
}

.title {
  font-size: 28px;
  font-weight: 700;
  color: #1c1917;
  margin: 0 0 8px;
}

.subtitle {
  font-size: 14px;
  color: #78716c;
  margin: 0 0 32px;
}

.form {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.login-method-tabs {
  display: flex;
  gap: 0;
  background: #f1f5f9;
  border-radius: 10px;
  padding: 4px;
}

.tab-btn {
  flex: 1;
  height: 40px;
  border: none;
  border-radius: 8px;
  background: transparent;
  color: #64748b;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s;
}

.tab-btn.active {
  background: #ffffff;
  color: #2563eb;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.tab-btn:hover:not(.active) {
  color: #334155;
}

.input-group {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.input-group label {
  font-size: 14px;
  font-weight: 500;
  color: #44403c;
}

.input-group input {
  width: 100%;
  height: 48px;
  padding: 0 16px;
  border: 1px solid #e7e5e4;
  border-radius: 10px;
  font-size: 15px;
  color: #1c1917;
  transition: all 0.2s;
  box-sizing: border-box;
}

.input-group input:focus {
  outline: none;
  border-color: #3b82f6;
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.15);
}

.input-group input::placeholder {
  color: #a8a29e;
}

.phone-input {
  display: flex;
  align-items: center;
  gap: 8px;
}

.country-code {
  height: 48px;
  padding: 0 16px;
  background: #fafaf9;
  border: 1px solid #e7e5e4;
  border-radius: 10px;
  display: flex;
  align-items: center;
  font-size: 15px;
  color: #44403c;
  font-weight: 500;
}

.phone-input input {
  flex: 1;
}

.code-input {
  display: flex;
  gap: 12px;
}

.code-input input {
  flex: 1;
}

.code-btn {
  min-width: 110px;
  height: 48px;
  padding: 0 16px;
  background: #eff6ff;
  border: 1px solid #bfdbfe;
  border-radius: 10px;
  color: #2563eb;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s;
}

.code-btn:hover:not(:disabled) {
  background: #dbeafe;
}

.code-btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.submit-btn {
  width: 100%;
  height: 52px;
  margin-top: 8px;
  background: linear-gradient(135deg, #3b82f6 0%, #2563eb 100%);
  border: none;
  border-radius: 10px;
  color: #ffffff;
  font-size: 16px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.2s;
}

.submit-btn:hover {
  transform: translateY(-1px);
  box-shadow: 0 6px 20px rgba(59, 130, 246, 0.35);
}

.submit-btn:active {
  transform: translateY(0);
}

.switch-mode {
  text-align: center;
  margin-top: 24px;
  font-size: 14px;
  color: #78716c;
}

.switch-mode a {
  color: #3b82f6;
  text-decoration: none;
  font-weight: 500;
}

.switch-mode a:hover {
  text-decoration: underline;
}

.agreement {
  text-align: center;
  margin-top: 20px;
  font-size: 12px;
  color: #a8a29e;
}

.agreement a {
  color: #3b82f6;
  text-decoration: none;
}

.agreement a:hover {
  text-decoration: underline;
}
</style>
