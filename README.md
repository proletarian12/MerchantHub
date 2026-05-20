# 🛍️ [MerchantHub] - 双端分离的全栈电商平台

> **一个真正具备完整商家后台的B2C商城，而不只是一个“好看”的购物页面。**
> 基于 Vue3 + Spring Boot + PostgreSQL + OSS 构建，重点展示商家从入驻到商品上架的全链路闭环。

[![License](https://img.shields.io/badge/license-MIT-blue.svg)]()
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7+-green.svg)]()
[![Vue](https://img.shields.io/badge/Vue-3.2+-brightgreen.svg)]()

<p align="center">
  <img src="./docs/screenshots/preview.gif" alt="项目预览" width="700"/>
</p>

## ✨ 核心亮点：不止是购物，更是完整的商业闭环

绝大多数同类开源项目仅实现了用户端的浏览和下单，**商家功能往往被简化或缺失**。本项目将商家端作为一级功能重点打造。

- **🛒 用户购物体验**
  - 注册登录、商品浏览搜索、购物车、下单、模拟支付

- **🏪 商家经营管理** ⭐ **重点特性**
  - **商家自助入驻**：提交资质信息，平台管理员审核
  - **独立商家后台**：独立的登录入口和操作界面
  - **商品管理**：多图 OSS 上传、规格库存配置、上架/下架/草稿
  - **订单履约**：查看本店订单、发货填物流单号
  - **数据看板**：店铺销售额、订单量概览

## 🛠️ 技术栈

| 层级 | 技术 |
|------|------|
| 前端 | Vue 3 + Vite |
| 后端 | Spring Boot 2.7+ / 3.x |
| 安全 | Spring Security + JWT |
| 数据库 | PostgreSQL 14+ |
| 存储 | Aliyun OSS |


## 🚀 为什么这个项目值得关注？

1. **OSS 服务端安全签名直传**：商家上传图片不经过后端中转，后端生成 STS 临时 Token 直传 OSS，安全且低带宽消耗。


## ⚡ 快速开始

**环境前置要求**
- JDK 17+ & Maven 3.8+
- Node.js 16+ & pnpm


