<template>
  <div class="confirm-order">
    <h2>确认订单信息</h2>
    
    <!-- 商品信息 -->
    <div class="product-info">
      <img :src="orderData.productImage" alt="">
      <div>
        <h3>{{ orderData.productName }}</h3>
        <p>单价：¥{{ orderData.productPrice }}</p>
        <p>数量：{{ orderData.quantity }}</p>
        <p>总价：¥{{ orderData.totalAmount }}</p>
      </div>
    </div>
    
    <!-- 收货地址 -->
    <div class="address-section">
      <h3>收货地址</h3>
      
      <!-- 从token加载的默认地址 -->
      <div v-if="savedAddress" class="saved-address">
        <div class="address-card">
          <div class="address-info">
            <p><strong>{{ savedAddress.name }}</strong> {{ savedAddress.phone }}</p>
            <p>{{ savedAddress.province }} {{ savedAddress.city }} {{ savedAddress.district || '' }} {{ savedAddress.detail }}</p>
          </div>
          <button @click="selectSavedAddress" class="use-address-btn" :class="{ active: selectedAddress === savedAddress }">
            {{ selectedAddress === savedAddress ? '已选择' : '使用此地址' }}
          </button>
        </div>
      </div>
      
      <!-- 手动输入地址 -->
      <div class="manual-address">
        <div class="address-tabs">
          <button 
            @click="useManualAddress = true" 
            class="tab-btn" 
            :class="{ active: useManualAddress }"
          >
            使用新地址
          </button>
          <button 
            v-if="savedAddress" 
            @click="useManualAddress = false" 
            class="tab-btn" 
            :class="{ active: !useManualAddress }"
          >
            使用保存的地址
          </button>
        </div>
        
        <div v-if="useManualAddress" class="address-form">
          <div class="form-row">
            <input 
              v-model="manualAddress.name" 
              placeholder="收货人姓名" 
              class="address-input"
            >
          </div>
          <div class="form-row">
            <input 
              v-model="manualAddress.phone" 
              placeholder="联系电话" 
              class="address-input"
            >
          </div>
          
          <!-- 省份选择带搜索 -->
          <div class="form-row">
            <div class="custom-select" :class="{ 'select-open': showProvinceDropdown }">
              <div class="select-input" @click="toggleProvinceDropdown">
                <input 
                  type="text"
                  v-model="provinceSearchKeyword"
                  :placeholder="manualAddress.province || '请选择省份'"
                  @input="handleProvinceSearch"
                  @focus="showProvinceDropdown = true"
                  @click.stop
                  class="select-search-input"
                >
                <span class="select-arrow">▼</span>
              </div>
              
              <div v-if="showProvinceDropdown" class="select-dropdown">
                <div 
                  v-for="province in filteredProvinces" 
                  :key="province"
                  class="select-option"
                  @click="selectProvince(province)"
                >
                  {{ province }}
                </div>
                <div v-if="filteredProvinces.length === 0" class="no-options">
                  暂无匹配的省份
                </div>
              </div>
            </div>
            
            <!-- 城市选择带搜索 -->
            <div class="custom-select" :class="{ 'select-open': showCityDropdown }">
              <div class="select-input" @click="toggleCityDropdown">
                <input 
                  type="text"
                  v-model="citySearchKeyword"
                  :placeholder="manualAddress.city || '请选择城市'"
                  :disabled="!manualAddress.province"
                  @input="handleCitySearch"
                  @focus="showCityDropdown = true"
                  @click.stop
                  class="select-search-input"
                >
                <span class="select-arrow">▼</span>
              </div>
              
              <div v-if="showCityDropdown" class="select-dropdown">
                <div 
                  v-for="city in filteredCities" 
                  :key="city"
                  class="select-option"
                  @click="selectCity(city)"
                >
                  {{ city }}
                </div>
                <div v-if="filteredCities.length === 0" class="no-options">
                  {{ !manualAddress.province ? '请先选择省份' : '暂无匹配的城市' }}
                </div>
              </div>
            </div>
          </div>
          
          <div class="form-row">
            <input 
              v-model="manualAddress.detail" 
              placeholder="详细地址" 
              class="address-input"
            >
          </div>
          <button @click="saveManualAddress" class="save-address-btn" :disabled="savingAddress">
            {{ savingAddress ? '保存中...' : '确认使用此地址' }}
          </button>
        </div>
      </div>
    </div>
    
    <!-- 支付方式 - 可点击按钮 -->
    <div class="payment-section">
      <h3>支付方式</h3>
      <div class="payment-buttons">
        <button 
          @click="paymentMethod = 'wechat'" 
          class="payment-btn" 
          :class="{ active: paymentMethod === 'wechat' }"
        >
          <span class="payment-icon">📱</span>
          微信支付
        </button>
        <button 
          @click="paymentMethod = 'alipay'" 
          class="payment-btn" 
          :class="{ active: paymentMethod === 'alipay' }"
        >
          <span class="payment-icon">💰</span>
          支付宝
        </button>
      </div>
    </div>
    
    <!-- 按钮组 -->
    <div class="button-group">
      <button @click="goToHome" class="back-btn">返回首页</button>
      <button @click="submitOrder" class="submit-btn" :disabled="!canSubmit">提交订单并支付</button>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, onUnmounted, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'

const route = useRoute()
const router = useRouter()
const paymentMethod = ref('wechat')
const selectedAddress = ref(null)
const savedAddress = ref(null)
const useManualAddress = ref(false)
const savingAddress = ref(false)

// 下拉框状态
const showProvinceDropdown = ref(false)
const showCityDropdown = ref(false)

// 搜索关键词
const provinceSearchKeyword = ref('')
const citySearchKeyword = ref('')

// 省份列表
const provinces = ref([
  '北京市', '天津市', '上海市', '重庆市',
  '河北省', '山西省', '辽宁省', '吉林省', '黑龙江省',
  '江苏省', '浙江省', '安徽省', '福建省', '江西省', '山东省',
  '河南省', '湖北省', '湖南省', '广东省', '海南省',
  '四川省', '贵州省', '云南省', '陕西省', '甘肃省', '青海省', '台湾省',
  '内蒙古自治区', '广西壮族自治区', '西藏自治区', '宁夏回族自治区', '新疆维吾尔自治区',
  '香港特别行政区', '澳门特别行政区'
])

// 城市数据映射
const cityData = ref({
  '北京市': ['北京市'],
  '天津市': ['天津市'],
  '上海市': ['上海市'],
  '重庆市': ['重庆市'],
  
  '河北省': ['石家庄市', '唐山市', '秦皇岛市', '邯郸市', '邢台市', '保定市', '张家口市', '承德市', '沧州市', '廊坊市', '衡水市'],
  '山西省': ['太原市', '大同市', '阳泉市', '长治市', '晋城市', '朔州市', '晋中市', '运城市', '忻州市', '临汾市', '吕梁市'],
  '辽宁省': ['沈阳市', '大连市', '鞍山市', '抚顺市', '本溪市', '丹东市', '锦州市', '营口市', '阜新市', '辽阳市', '盘锦市', '铁岭市', '朝阳市', '葫芦岛市'],
  '吉林省': ['长春市', '吉林市', '四平市', '辽源市', '通化市', '白山市', '松原市', '白城市', '延边朝鲜族自治州'],
  '黑龙江省': ['哈尔滨市', '齐齐哈尔市', '鸡西市', '鹤岗市', '双鸭山市', '大庆市', '伊春市', '佳木斯市', '七台河市', '牡丹江市', '黑河市', '绥化市', '大兴安岭地区'],
  
  '江苏省': ['南京市', '无锡市', '徐州市', '常州市', '苏州市', '南通市', '连云港市', '淮安市', '盐城市', '扬州市', '镇江市', '泰州市', '宿迁市'],
  '浙江省': ['杭州市', '宁波市', '温州市', '嘉兴市', '湖州市', '绍兴市', '金华市', '衢州市', '舟山市', '台州市', '丽水市'],
  '安徽省': ['合肥市', '芜湖市', '蚌埠市', '淮南市', '马鞍山市', '淮北市', '铜陵市', '安庆市', '黄山市', '滁州市', '阜阳市', '宿州市', '六安市', '亳州市', '池州市', '宣城市'],
  '福建省': ['福州市', '厦门市', '莆田市', '三明市', '泉州市', '漳州市', '南平市', '龙岩市', '宁德市'],
  '江西省': ['南昌市', '景德镇市', '萍乡市', '九江市', '新余市', '鹰潭市', '赣州市', '吉安市', '宜春市', '抚州市', '上饶市'],
  '山东省': ['济南市', '青岛市', '淄博市', '枣庄市', '东营市', '烟台市', '潍坊市', '济宁市', '泰安市', '威海市', '日照市', '临沂市', '德州市', '聊城市', '滨州市', '菏泽市'],
  
  '河南省': ['郑州市', '开封市', '洛阳市', '平顶山市', '安阳市', '鹤壁市', '新乡市', '焦作市', '濮阳市', '许昌市', '漯河市', '三门峡市', '南阳市', '商丘市', '信阳市', '周口市', '驻马店市', '济源市'],
  '湖北省': ['武汉市', '黄石市', '十堰市', '宜昌市', '襄阳市', '鄂州市', '荆门市', '孝感市', '荆州市', '黄冈市', '咸宁市', '随州市', '恩施土家族苗族自治州', '仙桃市', '潜江市', '天门市', '神农架林区'],
  '湖南省': ['长沙市', '株洲市', '湘潭市', '衡阳市', '邵阳市', '岳阳市', '常德市', '张家界市', '益阳市', '郴州市', '永州市', '怀化市', '娄底市', '湘西土家族苗族自治州'],
  '广东省': ['广州市', '深圳市', '珠海市', '汕头市', '佛山市', '韶关市', '湛江市', '肇庆市', '江门市', '茂名市', '惠州市', '梅州市', '汕尾市', '河源市', '阳江市', '清远市', '东莞市', '中山市', '潮州市', '揭阳市', '云浮市'],
  '海南省': ['海口市', '三亚市', '三沙市', '儋州市', '五指山市', '琼海市', '文昌市', '万宁市', '东方市', '定安县', '屯昌县', '澄迈县', '临高县', '白沙黎族自治县', '昌江黎族自治县', '乐东黎族自治县', '陵水黎族自治县', '保亭黎族苗族自治县', '琼中黎族苗族自治县'],
  
  '四川省': ['成都市', '自贡市', '攀枝花市', '泸州市', '德阳市', '绵阳市', '广元市', '遂宁市', '内江市', '乐山市', '南充市', '眉山市', '宜宾市', '广安市', '达州市', '雅安市', '巴中市', '资阳市', '阿坝藏族羌族自治州', '甘孜藏族自治州', '凉山彝族自治州'],
  '贵州省': ['贵阳市', '六盘水市', '遵义市', '安顺市', '毕节市', '铜仁市', '黔西南布依族苗族自治州', '黔东南苗族侗族自治州', '黔南布依族苗族自治州'],
  '云南省': ['昆明市', '曲靖市', '玉溪市', '保山市', '昭通市', '丽江市', '普洱市', '临沧市', '楚雄彝族自治州', '红河哈尼族彝族自治州', '文山壮族苗族自治州', '西双版纳傣族自治州', '大理白族自治州', '德宏傣族景颇族自治州', '怒江傈僳族自治州', '迪庆藏族自治州'],
  '陕西省': ['西安市', '铜川市', '宝鸡市', '咸阳市', '渭南市', '延安市', '汉中市', '榆林市', '安康市', '商洛市'],
  '甘肃省': ['兰州市', '嘉峪关市', '金昌市', '白银市', '天水市', '武威市', '张掖市', '平凉市', '酒泉市', '庆阳市', '定西市', '陇南市', '临夏回族自治州', '甘南藏族自治州'],
  '青海省': ['西宁市', '海东市', '海北藏族自治州', '黄南藏族自治州', '海南藏族自治州', '果洛藏族自治州', '玉树藏族自治州', '海西蒙古族藏族自治州'],
  '台湾省': ['台北市', '新北市', '桃园市', '台中市', '台南市', '高雄市', '基隆市', '新竹市', '嘉义市', '新竹县', '苗栗县', '彰化县', '南投县', '云林县', '嘉义县', '屏东县', '宜兰县', '花莲县', '台东县', '澎湖县', '金门县', '连江县'],
  
  '内蒙古自治区': ['呼和浩特市', '包头市', '乌海市', '赤峰市', '通辽市', '鄂尔多斯市', '呼伦贝尔市', '巴彦淖尔市', '乌兰察布市', '兴安盟', '锡林郭勒盟', '阿拉善盟'],
  '广西壮族自治区': ['南宁市', '柳州市', '桂林市', '梧州市', '北海市', '防城港市', '钦州市', '贵港市', '玉林市', '百色市', '贺州市', '河池市', '来宾市', '崇左市'],
  '西藏自治区': ['拉萨市', '日喀则市', '昌都市', '林芝市', '山南市', '那曲市', '阿里地区'],
  '宁夏回族自治区': ['银川市', '石嘴山市', '吴忠市', '固原市', '中卫市'],
  '新疆维吾尔自治区': ['乌鲁木齐市', '克拉玛依市', '吐鲁番市', '哈密市', '昌吉回族自治州', '博尔塔拉蒙古自治州', '巴音郭楞蒙古自治州', '阿克苏地区', '克孜勒苏柯尔克孜自治州', '喀什地区', '和田地区', '伊犁哈萨克自治州', '塔城地区', '阿勒泰地区', '石河子市', '阿拉尔市', '图木舒克市', '五家渠市', '北屯市', '铁门关市', '双河市', '可克达拉市', '昆玉市'],
  
  '香港特别行政区': ['香港岛', '九龙', '新界'],
  '澳门特别行政区': ['澳门半岛', '氹仔', '路环', '路氹城']
})

// 过滤后的省份
const filteredProvinces = computed(() => {
  if (!provinceSearchKeyword.value) return provinces.value
  return provinces.value.filter(p => 
    p.toLowerCase().includes(provinceSearchKeyword.value.toLowerCase())
  )
})

// 过滤后的城市
const filteredCities = computed(() => {
  if (!manualAddress.province) return []
  const cities = cityData.value[manualAddress.province] || []
  if (!citySearchKeyword.value) return cities
  return cities.filter(c => 
    c.toLowerCase().includes(citySearchKeyword.value.toLowerCase())
  )
})

// 手动输入的地址
const manualAddress = reactive({
  name: '',
  phone: '',
  province: '',
  city: '',
  detail: ''
})

// 从query参数获取订单数据
const orderData = ref({
  productId: route.query.productId,
  quantity: route.query.quantity,
  productPrice: route.query.price,
  productName: route.query.productName || '商品名称',
  productImage: route.query.productImage || '默认图片URL',
  totalAmount: (route.query.price * route.query.quantity).toFixed(2)
})

// 计算是否可以提交订单
const canSubmit = computed(() => {
  return selectedAddress.value !== null
})

// 切换省份下拉框
const toggleProvinceDropdown = () => {
  showProvinceDropdown.value = !showProvinceDropdown.value
  if (showProvinceDropdown.value) {
    showCityDropdown.value = false
  }
}

// 切换城市下拉框
const toggleCityDropdown = () => {
  if (!manualAddress.province) {
    alert('请先选择省份')
    return
  }
  showCityDropdown.value = !showCityDropdown.value
  if (showCityDropdown.value) {
    showProvinceDropdown.value = false
  }
}

// 处理省份搜索
const handleProvinceSearch = () => {
  showProvinceDropdown.value = true
}

// 处理城市搜索
const handleCitySearch = () => {
  if (manualAddress.province) {
    showCityDropdown.value = true
  }
}

// 选择省份
const selectProvince = (province) => {
  manualAddress.province = province
  manualAddress.city = '' // 清空城市
  provinceSearchKeyword.value = ''
  showProvinceDropdown.value = false
}

// 选择城市
const selectCity = (city) => {
  manualAddress.city = city
  citySearchKeyword.value = ''
  showCityDropdown.value = false
}

// 点击其他地方关闭下拉框
const handleClickOutside = (event) => {
  if (!event.target.closest('.custom-select')) {
    showProvinceDropdown.value = false
    showCityDropdown.value = false
  }
}

// 选择保存的地址
const selectSavedAddress = () => {
  if (savedAddress.value) {
    selectedAddress.value = savedAddress.value
    useManualAddress.value = false
  }
}

// 从token解析用户地址
const parseTokenForAddress = () => {
  const token = localStorage.getItem('token')
  if (token) {
    try {
      const userInfo = JSON.parse(localStorage.getItem('userInfo') || '{}')
      
      if (userInfo.address) {
        savedAddress.value = userInfo.address
        selectedAddress.value = userInfo.address
      } else {
        // 模拟默认地址
        savedAddress.value = {
          name: '张三',
          phone: '13800138000',
          province: '广东省',
          city: '深圳市',
          district: '南山区',
          detail: '科技园XX栋XX号',
          id: 1
        }
      }
    } catch (error) {
      console.error('解析用户信息失败:', error)
    }
  }
}

// 从后端获取用户的地址列表
const fetchUserAddresses = async () => {
  try {
    const token = localStorage.getItem('token')
    if (!token) return
    
    const response = await fetch('http://localhost:8080/api/user/addresses', {
      headers: {
        'Authorization': `Bearer ${token}`
      }
    })
    
    const result = await response.json()
    
    if (result.code === 200 && result.data.length > 0) {
      // 获取默认地址或第一个地址
      const defaultAddress = result.data.find(addr => addr.isDefault) || result.data[0]
      savedAddress.value = defaultAddress
      selectedAddress.value = defaultAddress
    }
  } catch (error) {
    console.error('获取地址列表失败:', error)
  }
}

// 保存手动输入的地址到后端
const saveManualAddress = async () => {
  // 验证地址信息
  if (!manualAddress.name || !manualAddress.phone || 
      !manualAddress.province || !manualAddress.city || 
      !manualAddress.detail) {
    alert('请填写完整的地址信息')
    return
  }
  
  // 手机号验证
  if (!/^1[3-9]\d{9}$/.test(manualAddress.phone)) {
    alert('请输入正确的手机号码')
    return
  }
  
  savingAddress.value = true
  
  try {
    // 获取token
    const token = localStorage.getItem('token')
    if (!token) {
      alert('请先登录')
      router.push('/login')
      return
    }
    
    // 准备地址数据
    const addressData = {
      name: manualAddress.name,
      phone: manualAddress.phone,
      province: manualAddress.province,
      city: manualAddress.city,
      detail: manualAddress.detail,
      isDefault: false // 可以根据需要设置是否默认
    }
    
    // 调用后端API保存地址
    const response = await fetch('http://localhost:8080/api/user/address', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      },
      body: JSON.stringify(addressData)
    })
    
    const result = await response.json()
    
    if (result.code === 200) {
      // 保存成功
      const newAddress = {
        ...addressData,
        id: result.data.id || Date.now(), // 使用后端返回的ID
        district: '' // 添加district字段
      }
      
      // 更新选中的地址
      selectedAddress.value = newAddress
      
      // 更新用户信息中的地址列表
      const userInfo = JSON.parse(localStorage.getItem('userInfo') || '{}')
      if (!userInfo.addresses) {
        userInfo.addresses = []
      }
      userInfo.addresses.push(newAddress)
      localStorage.setItem('userInfo', JSON.stringify(userInfo))
      
      alert('地址保存成功！')
      
      // 可选：关闭手动输入表单
      useManualAddress.value = false
    } else {
      alert(result.message || '地址保存失败，请重试')
    }
  } catch (error) {
    console.error('保存地址失败:', error)
    alert('网络错误，请检查后端服务是否启动')
  } finally {
    savingAddress.value = false
  }
}

// 提交订单时使用选中的地址
const submitOrder = async () => {
  if (!selectedAddress.value) {
    alert('请选择或填写收货地址')
    return
  }
  
  try {
    const token = localStorage.getItem('token')
    if (!token) {
      alert('请先登录')
      router.push('/login')
      return
    }
    
    const response = await createOrder({
      ...orderData.value,
      paymentMethod: paymentMethod.value,
      addressId: selectedAddress.value.id,
      address: selectedAddress.value
    })
    
    // 统一使用 response.data.code
    if (response.data.code === 200) {
      // 处理支付逻辑
      if (paymentMethod.value === 'wechat') {
        if (response.data.payType === 'qr_code') {
          showQRCode(response.data.codeUrl)
        } else if (response.data.payType === 'h5') {
          window.location.href = response.data.mwebUrl
        } else {
          window.location.href = response.data.payUrl
        }
      } else if (paymentMethod.value === 'alipay') {
        window.location.href = response.data.payUrl
      }
    } else {
      alert(response.data.message || '创建订单失败')
    }
  } catch (error) {
    console.error('创建订单失败:', error)
    alert('网络错误，请检查后端服务是否启动')
  }
}

// 创建订单API调用
const createOrder = async (orderParams) => {
  try {
    const response = await fetch('http://localhost:8080/api/create-order', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      },
      body: JSON.stringify(orderParams)
    })
    
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`)
    }
    
    const data = await response.json()
    return { data }
  } catch (error) {
    console.error('创建订单失败:', error)
    throw error
  }
}

// 显示二维码
const showQRCode = (codeUrl) => {
  console.log('支付二维码地址:', codeUrl)
  // 这里可以添加显示二维码的弹窗逻辑
  alert('请使用微信扫描二维码支付')
}

// 返回首页
const goToHome = () => {
  router.push('/')
}

// 组件挂载时
onMounted(() => {
  // 先尝试从后端获取地址
  fetchUserAddresses()
    .finally(() => {
      // 如果没有后端地址，再使用token中的地址
      if (!savedAddress.value) {
        parseTokenForAddress()
      }
    })
  
  document.addEventListener('click', handleClickOutside)
})

// 组件卸载时移除事件监听
onUnmounted(() => {
  document.removeEventListener('click', handleClickOutside)
})
</script>

<style scoped>
.confirm-order {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}

.save-address-btn:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}


.product-info {
  display: flex;
  gap: 20px;
  margin: 20px 0;
  padding: 15px;
  border: 1px solid #eee;
  border-radius: 8px;
}

.product-info img {
  width: 100px;
  height: 100px;
  object-fit: cover;
  border-radius: 4px;
}

.address-section,
.payment-section {
  margin: 20px 0;
  padding: 15px;
  border: 1px solid #eee;
  border-radius: 8px;
}

/* 地址样式 */
.saved-address {
  margin-bottom: 20px;
}

.address-card {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px;
  background-color: #f9f9f9;
  border-radius: 6px;
  border: 1px solid #e0e0e0;
}

.address-info p {
  margin: 5px 0;
}

.use-address-btn {
  padding: 8px 16px;
  background-color: #fff;
  border: 1px solid #07c160;
  color: #07c160;
  border-radius: 4px;
  cursor: pointer;
  transition: all 0.3s;
}

.use-address-btn.active {
  background-color: #07c160;
  color: white;
}

.address-tabs {
  display: flex;
  gap: 10px;
  margin-bottom: 15px;
}

.tab-btn {
  padding: 8px 16px;
  background: none;
  border: none;
  border-bottom: 2px solid transparent;
  cursor: pointer;
  color: #666;
}

.tab-btn.active {
  color: #07c160;
  border-bottom-color: #07c160;
}

.address-form {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.form-row {
  display: flex;
  gap: 10px;
  position: relative;
}

.address-input {
  flex: 1;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 14px;
}

.address-input:focus {
  outline: none;
  border-color: #07c160;
}

/* 自定义下拉框样式 */
.custom-select {
  flex: 1;
  position: relative;
  cursor: pointer;
}

.select-input {
  display: flex;
  align-items: center;
  border: 1px solid #ddd;
  border-radius: 4px;
  background-color: white;
  transition: all 0.3s;
}

.select-input:hover {
  border-color: #07c160;
}

.select-open .select-input {
  border-color: #07c160;
  border-bottom-left-radius: 0;
  border-bottom-right-radius: 0;
}

.select-search-input {
  flex: 1;
  padding: 10px;
  border: none;
  outline: none;
  font-size: 14px;
  background: transparent;
  cursor: pointer;
}

.select-search-input:disabled {
  background-color: #f5f5f5;
  cursor: not-allowed;
}

.select-arrow {
  padding: 0 10px;
  color: #999;
  font-size: 12px;
  transition: transform 0.3s;
}

.select-open .select-arrow {
  transform: rotate(180deg);
}

.select-dropdown {
  position: absolute;
  top: 100%;
  left: 0;
  right: 0;
  max-height: 200px;
  overflow-y: auto;
  background-color: white;
  border: 1px solid #07c160;
  border-top: none;
  border-radius: 0 0 4px 4px;
  z-index: 1000;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.select-option {
  padding: 8px 10px;
  cursor: pointer;
  transition: background-color 0.2s;
  font-size: 14px;
}

.select-option:hover {
  background-color: #f0f9f0;
  color: #07c160;
}

.no-options {
  padding: 10px;
  text-align: center;
  color: #999;
  font-size: 14px;
}

.save-address-btn {
  padding: 10px;
  background-color: #07c160;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
}

.save-address-btn:hover {
  background-color: #06a050;
}

/* 支付按钮样式 */
.payment-buttons {
  display: flex;
  gap: 20px;
  margin-top: 10px;
}

.payment-btn {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  padding: 15px;
  background-color: #fff;
  border: 2px solid #ddd;
  border-radius: 8px;
  cursor: pointer;
  font-size: 16px;
  transition: all 0.3s;
}

.payment-btn.active {
  border-color: #07c160;
  background-color: #f0fff0;
}

.payment-btn:hover {
  border-color: #07c160;
}

.payment-icon {
  font-size: 24px;
}

/* 按钮组 */
.button-group {
  display: flex;
  gap: 10px;
  margin-top: 20px;
}

.submit-btn {
  flex: 2;
  background-color: #07c160;
  color: white;
  border: none;
  padding: 12px 24px;
  border-radius: 4px;
  font-size: 16px;
  cursor: pointer;
}

.submit-btn:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

.back-btn {
  flex: 1;
  background-color: #f5f5f5;
  color: #333;
  border: 1px solid #ddd;
  padding: 12px 24px;
  border-radius: 4px;
  font-size: 16px;
  cursor: pointer;
}

.submit-btn:hover:not(:disabled) {
  background-color: #06a050;
}

.back-btn:hover {
  background-color: #e8e8e8;
}
</style>