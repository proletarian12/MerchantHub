<template>
  <div name="all" style="border:1px solid #000;border-radius:10px;">
  <div class="user-account-container">
    <h2 class="page-title">我的账户</h2>
    
    <div class="account-info">

    </div>
      
    <div class="section-header">基本信息</div>
    <div class="info-row">
      <label class="info-label">店铺名</label>
      <span class="info-value" v-if="!isEditing">{{ accountName }}</span>
      <input
        type="text"
        v-model="accountName"
        v-else
        class="info-input"
        placeholder="请输入账户名"
     />
    </div>

<div class="info-row">
  <label class="info-label">头像</label>
  <div class="avatar-container">
    <img :src="avatarUrl" :alt="accountName" class="avatar-image" />
    <input
      type="file"
      v-if="isEditing"
      @change="handleAvatarChange"
      class="avatar-input"
      accept="image/*"
    />
  </div>
</div>
  <br/>
    <div class="actions">
        <button @click="toggleEditing">{{ isEditing ? '取消' : '编辑' }}</button>
        <button v-if="isEditing" @click=changeM()>确定</button>
        <br/>
        <button @click="changePassword" class="action-link">修改密码</button>
        <button @click="logout" class="action-link">退出当前账号</button>
    </div>
</div>
</div>
</template>

<script setup>
import { ref,onMounted } from 'vue'

// Reactive data

const accountName = ref('');
    const avatarUrl = ref('');
    const isEditing = ref(false);
    const newAvatarFile = ref(null);

// Methods
const fetchProfile = async () => {
      try {
        const response = await fetch('/api/get-profile');
        if (response.ok) {
          const data = await response.json();
          accountName.value = data.accountName || '';
          avatarUrl.value = data.avatarUrl || '';
        } else {
          console.error('Failed to fetch profile');
        }
      } catch (error) {
        console.error('Error fetching profile:', error);
      }
    };
    
    onMounted(fetchProfile);
    
    const toggleEditing = () => {
      isEditing.value = !isEditing.value;
    };

    const handleAvatarChange = (event) => {
      newAvatarFile.value = event.target.files[0];
      if (newAvatarFile.value) {
        const reader = new FileReader();
        reader.onload = (e) => {
          avatarUrl.value = e.target.result;
        };
        reader.readAsDataURL(newAvatarFile.value);
      }
    };

async function changeM(){
  const fd2 = new FormData()
  fd2.append('accountName', accountName.value);
      if (newAvatarFile.value) {
        fd2.append('avatar', newAvatarFile.value);
      }

      try {
        // 发送数据到后端
        const response = await fetch('/api/update-profile', {
          method: 'POST',
          body: fd2
        });

        if (response.ok) {
          // 刷新组件
          isEditing.value = false;
          alert('更新成功');
        } else {
          alert('更新失败，请稍后再试');
        }
      } catch (error) {
        console.error('Error:', error);
        alert('更新失败，请稍后再试');
      }
    }
    


const changePassword = () => {
  console.log('Change password clicked')
  // Add password change logic here
}

const logout = () => {
  console.log('Logout clicked')
  // Add logout logic here
}
</script>

<style scoped>
.user-account-container {
  padding: 24px;
  background-color: #ffffff;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
}

.page-title {
  font-size: 18px;
  font-weight: 500;
  color: #333333;
  margin-bottom: 32px;
  margin-top: 0;
}

.account-info {
  max-width: 600px;
}

.info-row {
  display: flex;
  align-items: center;
  margin-bottom: 24px;
  min-height: 32px;
}

.info-label {
  width: 120px;
  font-size: 14px;
  color: #666666;
  flex-shrink: 0;
}

.info-value {
  font-size: 14px;
  color: #333333;
}

.section-header {
  font-size: 16px;
  font-weight: 500;
  color: #333333;
  margin: 32px 0 24px 0;
  padding-bottom: 8px;
  border-bottom: 1px solid #f0f0f0;
}

.avatar-container {
  display: flex;
  align-items: center;
}

.avatar-image {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  object-fit: cover;
  border: 1px solid #e0e0e0;
}

.actions {
  display: flex;
  flex-direction: column;
  gap: 16px;
  margin-left: 120px;
}

.action-link {
  background: none;
  border: none;
  color: #1890ff;
  font-size: 14px;
  cursor: pointer;
  text-align: left;
  padding: 0;
  text-decoration: none;
  transition: color 0.2s ease;
}

.action-link:hover {
  color: #40a9ff;
  text-decoration: underline;
}

.action-link:active {
  color: #096dd9;
}
</style>