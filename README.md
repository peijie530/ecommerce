# 🛒 Java 電商後端系統 (E-Commerce Backend)

這是一個基於 Spring Boot 開發的微型電商後端系統，目前已完成核心的**購物車**與**訂單處理**模組。

## 🚀 已完成功能
- **使用者驗證**：整合 JWT 提供安全的身分驗證。
- **購物車管理**：支援商品加入、數量修改、自動計算總價。
- **訂單系統**：
  - 支援一鍵結帳流程（自動扣庫存、清空購物車）。
  - 具備事務控制 (@Transactional)，確保資料一致性。
  - 提供個人訂單歷史紀錄查詢。
- **庫存守門員**：結帳時自動驗證庫存，避免超賣。

## 🛠️ 技術棧 (Tech Stack)
- **Framework**: Spring Boot 3.2
- **Database**: H2 Database (In-Memory)
- **ORM**: Spring Data JPA
- **Security**: Spring Security + JWT
- **Build Tool**: Maven

## 📋 API 介面說明

### 訂單模組 (Order API)
| 方法 | 路徑 | 說明 |

| `POST` | `/api/v1/orders` | 執行結帳（建立訂單） |

| `GET` | `/api/v1/orders` | 查詢個人歷史訂單 |

---

## 🛠️ 如何執行
1. 下載專案並使用 Eclipse/IntelliJ 開啟。
2. 確認 Maven 依賴已載入完畢。
3. 執行 `EcommerceApplication.java`。
4. 預設啟動埠號：`8080`。