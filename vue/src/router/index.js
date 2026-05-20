import { createRouter, createWebHistory } from 'vue-router'

// 1. 引入页面级组件
import MainPage       from '@/components/mainPage.vue'
import AppProduct     from '@/components/S_appProduct.vue'
import Search         from '@/components/searchProduct.vue'
import Shopping       from '@/components/sellerPage.vue'
import SubmitProduct  from '@/components/S_submitProduct.vue'
import UserAccount    from '@/components/userAccount.vue'
import UserRegister    from '@/components/userRegister.vue'
import Help    from '@/components/helpPage.vue'
import Car from '@/components/shoppingCar.vue'
import Detail from '@/components/productDetail.vue'
import Order from '@/components/confirm_order.vue'
import Seller1 from '@/components/seller/sellerRegister.vue'

// 2. 配置 URL 与组件的映射
const routes = [
  { path: '/',              name: 'home',     component: MainPage },
  { path: '/product/:id',   name: 'product',  component: AppProduct },
  { path: '/searchProduct',        name: 'search',   component: Search },
  { path: '/sellerPage',      name: 'shopping', component: Shopping },
  { path: '/submit',        name: 'submit',   component: SubmitProduct },
  { path: '/account',       name: 'account',  component: UserAccount },
  { path: '/appProduct',       name: 'appProduct',  component: AppProduct },
  { path: '/register',       name: 'userRegister',  component: UserRegister },
  { path: '/help',       name: 'help',  component: Help },
  { path: '/shoppingCar',       name: 'shoping',  component: Car },
  { path: '/productDetail',       name: 'detail',  component: Detail },
  { path: '/order',       name: 'order',  component: Order },
  { path: '/sellerRegister',       name: 'Seller1',  component: Seller1 },
  // 404 兜底
  { path: '/:pathMatch(.*)*', redirect: '/' }
]

export default createRouter({
  history: createWebHistory(),
  routes
})