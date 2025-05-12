# SnackOverFlow 應用系統

一個基於 Spring Boot 的產品管理與預訂系統，提供 RESTful API 接口。

## 專案概述

SnackOverFlow 是一個全功能的產品管理系統，專為零售和電商環境設計。該系統包括會員管理、產品分類、產品預訂以及評論功能。系統採用 JWT 驗證機制確保安全性，並使用統一響應格式、全面的異常處理和 Swagger API 文檔。

## 技術棧

- **Java 21**
- **Spring Boot 3.1.5** - 框架核心
- **Spring Security** - 用於身份驗證和授權
- **Spring Data JPA** - 用於資料持久化
- **MySQL 8** - 作為資料庫
- **Lombok** - 減少樣板代碼
- **MapStruct 1.5.3** - 用於對象映射
- **JWT 0.11.5** - 用於安全令牌管理
- **Springdoc-OpenAPI** - API 文檔
- **Flyway** - 資料庫遷移管理
- **Spring Boot Actuator** - 應用監控和指標收集

## 專案結構

```
src/main/java/com/yc/snackoverflow/
├── aspect/            # 切面類
├── config/            # 配置類
├── constant/          # 常量類
├── controller/        # REST API 控制器
├── dto/               # 數據傳輸對象
│   ├── request/       # 請求 DTO
│   └── response/      # 響應 DTO
├── entity/            # 數據庫實體類
│   └── base/          # 基礎實體類
├── exception/         # 異常處理
├── filter/            # 過濾器
├── handler/           # 響應處理器
├── mapper/            # 對象映射
├── projector/         # 投影類
├── repository/        # 數據訪問層
├── service/           # 業務邏輯層
│   └── impl/          # 服務實現
├── util/              # 工具類
└── SnackOverFlowApplication.java  # 應用入口

src/main/resources/
├── db/                # 資料庫相關資源
│   └── migration/     # Flyway 遷移腳本
├── application.properties  # 主配置文件
└── application.yml     # YAML 格式配置
```

## 最近改進

本專案最近進行了多項重要改進，提高了代碼質量和系統功能：

### 1. API 架構優化

- **統一 API 路徑**: 所有 API 端點統一使用 `/api` 前綴，例如 `/api/members`, `/api/products`
- **RESTful 設計規範**: 遵循 RESTful 設計原則，正確使用 HTTP 方法（GET, POST, PUT, DELETE）
- **URL 命名更規範**: 使用複數形式命名資源集合，如 `/api/members`, `/api/products`
- **權限控制**: 添加 `@PreAuthorize` 注解確保適當的權限檢查

### 2. 響應格式標準化

- **統一響應格式**: 所有 API 返回標準格式的 `ResultData` 對象，包含狀態碼、消息、數據和時間戳
- **分頁響應標準化**: 使用 `PageResult` 封裝分頁數據，提供一致的格式
- **錯誤處理**: 增強錯誤響應，提供更詳細的錯誤信息和統一錯誤碼

### 3. 安全性增強

- **文件上傳限制**: 添加文件類型和大小限制，防止上傳惡意文件
- **可配置上傳路徑**: 使用配置參數替代硬編碼路徑，提高安全性和靈活性
- **唯一文件名**: 上傳文件使用 UUID 生成唯一文件名，避免文件覆蓋

### 4. 代碼優化

- **日誌記錄**: 添加詳細的日誌記錄，跟蹤 API 調用和操作
- **Swagger 文檔**: 增強 API 文檔，提供詳細的操作說明和參數描述
- **命名優化**: 方法名與返回類型更加語義化，如 `createMember`, `getProducts`

## 統一響應格式

所有 API 響應遵循統一格式：

```json
{
  "code": 0,        // 狀態碼，0 表示成功，其他值表示錯誤
  "message": "Success",  // 響應消息
  "data": {},       // 響應數據，可能是單個對象、數組或空
  "timestamp": 1622547602123  // 響應時間戳
}
```

## API 端點列表

### 認證 API

| 方法   | URL                       | 描述                  | 需要權限  |
|------|---------------------------|---------------------|-------|
| POST | /api/auth/register        | 註冊新用戶               | 否     |
| POST | /api/auth/authenticate    | 用戶登入和認證             | 否     |
| GET  | /api/auth/renew           | 刷新 JWT 令牌           | 是     |

### 會員管理 API

| 方法    | URL                      | 描述                | 需要權限  |
|-------|--------------------------|-------------------|-------|
| POST  | /api/members             | 創建新會員             | 否     |
| PUT   | /api/members/{name}      | 更新指定會員            | 是     |
| GET   | /api/members             | 獲取會員列表（可選過濾）      | 是     |
| GET   | /api/members/{name}      | 根據名稱獲取特定會員        | 是     |
| DELETE| /api/members/{name}      | 刪除會員（邏輯刪除）        | 管理員   |

### 產品管理 API

| 方法    | URL                      | 描述                | 需要權限  |
|-------|--------------------------|-------------------|-------|
| POST  | /api/products            | 創建新產品             | 管理員/經理|
| POST  | /api/products/batch      | 批量創建產品            | 管理員/經理|
| PUT   | /api/products/{id}       | 更新指定產品            | 管理員/經理|
| GET   | /api/products            | 獲取產品列表（支持分頁和過濾）   | 是     |
| GET   | /api/products/{id}       | 根據 ID 獲取特定產品      | 是     |
| DELETE| /api/products/{id}       | 刪除產品（邏輯刪除）        | 管理員   |

### 產品分類 API

| 方法    | URL                          | 描述                | 需要權限   |
|-------|------------------------------|-------------------|--------|
| POST  | /api/product-classes         | 創建新產品分類           | 管理員/經理 |
| GET   | /api/product-classes         | 獲取產品分類列表（可選過濾）    | 是      |
| PUT   | /api/product-classes/status  | 更新產品分類狀態          | 管理員/經理 |

### 預訂管理 API

| 方法    | URL                          | 描述                | 需要權限   |
|-------|------------------------------|-------------------|--------|
| POST  | /api/bookings                | 創建新預訂             | 是      |
| PUT   | /api/bookings/{id}           | 更新預訂              | 是      |
| GET   | /api/bookings                | 獲取預訂列表（可選過濾）      | 是      |
| GET   | /api/bookings/{id}           | 根據 ID 獲取特定預訂      | 是      |
| GET   | /api/bookings/count          | 獲取預訂統計（按月和價格過濾）   | 是      |
| GET   | /api/bookings/all            | 獲取所有預訂            | 是      |
| DELETE| /api/bookings/{id}           | 刪除預訂              | 是      |

### 文件上傳 API

| 方法    | URL                      | 描述                | 需要權限  |
|-------|--------------------------|-------------------|-------|
| POST  | /api/upload               | 上傳文件             | 是     |

### 舊版 API（已棄用）

| 方法    | URL                          | 描述                | 需要權限   |
|-------|------------------------------|-------------------|--------|
| POST  | /api/product-legacy          | 創建產品（舊版）          | 管理員/經理 |
| PUT   | /api/product-legacy          | 更新產品（舊版）          | 管理員/經理 |
| GET   | /api/product-legacy          | 獲取產品列表（舊版）        | 是      |
| DELETE| /api/product-legacy          | 刪除產品（舊版）          | 管理員   |

## 詳細 API 規格

### 認證 API

#### 1. 用戶註冊

**請求**:
```http
POST /api/auth/register
Content-Type: application/json

{
  "username": "user1",
  "email": "user1@example.com",
  "password": "password123"
}
```

**響應**:
```json
{
  "code": 0,
  "message": "Success",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
  },
  "timestamp": 1622547602123
}
```

#### 2. 用戶登入

**請求**:
```http
POST /api/auth/authenticate
Content-Type: application/json

{
  "email": "user1@example.com",
  "password": "password123"
}
```

**響應**:
```json
{
  "code": 0,
  "message": "Success",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
  },
  "timestamp": 1622547602123
}
```

#### 3. 刷新令牌

**請求**:
```http
GET /api/auth/renew
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

**響應**:
```json
{
  "code": 0,
  "message": "Success",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
  },
  "timestamp": 1622547602123
}
```

### 會員管理 API

#### 1. 創建會員

**請求**:
```http
POST /api/members
Content-Type: application/json

{
  "name": "John Doe",
  "email": "john.doe@example.com",
  "password": "password123"
}
```

**響應**:
```json
{
  "code": 0,
  "message": "Member created successfully",
  "data": "CREATE_OR_NO_CHANGE",
  "timestamp": 1622547602123
}
```

#### 2. 更新會員

**請求**:
```http
PUT /api/members/john_doe
Content-Type: application/json
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...

{
  "vip": "VIP2",
  "email": "john.updated@example.com"
}
```

**響應**:
```json
{
  "code": 0,
  "message": "Member updated successfully",
  "data": "UPDATE",
  "timestamp": 1622547602123
}
```

#### 3. 獲取會員列表

**請求**:
```http
GET /api/members?memberNameList=john_doe,jane_doe
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

**響應**:
```json
{
  "code": 0,
  "message": "Success",
  "data": [
    {
      "id": 1,
      "name": "john_doe",
      "email": "john.updated@example.com",
      "vip": "VIP2",
      "alive": true,
      "role": "USER"
    },
    {
      "id": 2,
      "name": "jane_doe",
      "email": "jane.doe@example.com",
      "vip": "VIP1",
      "alive": true,
      "role": "USER"
    }
  ],
  "timestamp": 1622547602123
}
```

### 產品管理 API

#### 1. 獲取產品列表（分頁）

**請求**:
```http
GET /api/products?name=snack&classId=1&page=0&size=10&sort=name,asc
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

**響應**:
```json
{
  "code": 0,
  "message": "Success",
  "data": {
    "content": [
      {
        "id": 1,
        "name": "Chocolate Snack",
        "picture": "http://example.com/chocolate.jpg",
        "price": 2.5,
        "productClass": {
          "id": 1,
          "name": "Sweets"
        }
      },
      {
        "id": 2,
        "name": "Fruit Snack",
        "picture": "http://example.com/fruit.jpg",
        "price": 1.5,
        "productClass": {
          "id": 1,
          "name": "Sweets"
        }
      }
    ],
    "totalElements": 10,
    "totalPages": 1,
    "pageNumber": 0,
    "pageSize": 10,
    "first": true,
    "last": true,
    "empty": false
  },
  "timestamp": 1622547602123
}
```

#### 2. 創建產品

**請求**:
```http
POST /api/products
Content-Type: application/json
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...

{
  "name": "Energy Bar",
  "price": 3.75,
  "picture": "http://example.com/energy-bar.jpg",
  "productClassId": 1
}
```

**響應**:
```json
{
  "code": 0,
  "message": "Product created successfully",
  "data": {
    "id": 3,
    "name": "Energy Bar",
    "picture": "http://example.com/energy-bar.jpg",
    "price": 3.75,
    "productClass": {
      "id": 1,
      "name": "Sweets"
    }
  },
  "timestamp": 1622547602123
}
```

### 文件上傳 API

**請求**:
```http
POST /api/upload
Content-Type: multipart/form-data
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...

# Form data
file: [binary data]
```

**響應**:
```json
{
  "code": 0,
  "message": "File uploaded successfully",
  "data": "f47ac10b-58cc-4372-a567-0e02b2c3d479.jpg",
  "timestamp": 1622547602123
}
```

## 錯誤碼說明

系統定義了統一的錯誤碼體系，方便前端處理錯誤情況：

- 0: 成功
- 1: 一般失敗
- 10000-19999: 一般錯誤
  - 10000: 系統錯誤
  - 10001: 參數錯誤
  - 10002: 資源不存在
  - 10003: 數據訪問錯誤
  - 10004: 無效請求
  - 10005: 操作失敗
- 20000-29999: 認證錯誤
  - 20000: 未授權
  - 20001: 令牌過期
  - 20002: 無效憑證
  - 20003: 拒絕訪問
  - 20004: 禁止
- 30000-39999: 產品錯誤
  - 30000: 產品不存在
  - 30001: 產品分類不存在
  - 30002: 產品創建失敗
  - 30003: 產品更新失敗
  - 30004: 產品刪除失敗
- 40000-49999: 預訂錯誤
  - 40000: 預訂不存在
  - 40001: 預訂衝突
  - 40002: 預訂創建失敗
  - 40003: 預訂更新失敗
  - 40004: 預訂刪除失敗
- 50000-59999: 會員錯誤
  - 50000: 會員不存在
  - 50001: 會員已存在
  - 50002: 會員創建失敗

## 授權

本專案採用 MIT 授權。詳見 LICENSE 文件。

## 開發團隊

- YC - 項目負責人和主要開發者