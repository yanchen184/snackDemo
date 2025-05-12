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

### 1. 架構優化

- 目錄結構標準化：修改 `reposity` 為 `repository`，新增 `constant` 和 `util` 目錄
- 統一命名規範：統一使用 DTO 後綴，規範實體類命名
- 代碼分層更清晰：請求/響應 DTO 分離，實體類與傳輸對象分離

### 2. 安全增強

- JWT 配置優化：增加令牌過期時間配置，支持令牌刷新機制
- 安全錯誤處理：增強 JWT 相關異常處理

### 3. API 改進

- 統一響應格式：所有 API 返回標準格式的 `ResultData` 對象
- 分頁響應標準化：使用 `PageResult` 封裝分頁數據
- Controller 方法改進：區分 create、update、delete 方法，不再使用通用方法

### 4. 功能增強

- 全面的異常處理：增加 `GlobalExceptionHandler` 處理各類業務和技術異常
- API 文檔集成：集成 Springdoc-OpenAPI，提供易用的 Swagger 界面
- 資料庫遷移工具：集成 Flyway 進行數據庫版本控制
- 監控與指標：添加 Spring Boot Actuator 和自定義指標

## 功能特性

### 用戶管理
- 用戶註冊與登入
- JWT 認證
- 基於角色的訪問控制
- 令牌刷新
- 密碼加密存儲

### 產品管理
- 產品增刪改查
- 產品分類管理
- 產品圖片上傳
- 產品價格變更歷史
- 支持分頁和多條件查詢

### 預訂系統
- 創建預訂單
- 預訂詳情管理
- 預訂狀態追蹤
- 預訂統計和報表

### 評論和反饋
- 產品評論
- 評論回覆
- 星級評分

### 系統功能
- JWT 認證與授權
- 全局異常處理
- 統一響應格式
- API 文檔 (Swagger)
- 資料庫遷移 (Flyway)
- 系統監控 (Actuator)

## 快速開始

### 前置需求

- JDK 21
- Maven 3.8+
- MySQL 8+

### 資料庫設置

在 MySQL 中創建一個名為 `demo1` 的資料庫：

```sql
CREATE DATABASE demo1 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 編輯配置

修改 `application.properties` 以設置您的資料庫連接：

```properties
DB_URL=localhost
DB_PORT=3306
DB_NAME=demo1
DB_PARAM=useUnicode=true&connectionCollation=utf8mb4_unicode_ci&characterEncoding=utf-8&useSSL=false&allowPublicKeyRetrieval=true&useAffectedRows=true
DB_USERNAME=root
DB_PASSWORD=123456
```

### 編譯及運行

```bash
mvn clean install
mvn spring-boot:run
```

應用程序將在 http://localhost:8086 上啟動。

### API 文檔訪問

啟動應用後，可以通過以下鏈接訪問 Swagger API 文檔：

```
http://localhost:8086/swagger-ui.html
```

## API 規格

### 統一響應格式

所有 API 響應遵循統一格式：

```json
{
  "code": 0,        // 狀態碼，0 表示成功，其他值表示錯誤
  "message": "Success",  // 響應消息
  "data": {},       // 響應數據，可能是單個對象、數組或空
  "timestamp": 1622547602123  // 響應時間戳
}
```

### 分頁響應格式

分頁 API 的返回格式：

```json
{
  "code": 0,
  "message": "Success",
  "data": {
    "content": [
      // 數據列表
    ],
    "totalElements": 50,
    "totalPages": 5,
    "pageNumber": 0,
    "pageSize": 10,
    "first": true,
    "last": false,
    "empty": false
  },
  "timestamp": 1622547602123
}
```

### 錯誤響應格式

錯誤響應示例：

```json
{
  "code": 30000,
  "message": "產品不存在",
  "data": null,
  "timestamp": 1622547602123
}
```

## API 端點

### 認證

#### 用戶註冊
- **URL**: `POST /api/auth/register`
- **Request Body**:
  ```json
  {
    "username": "user1",
    "password": "password123",
    "email": "user1@example.com",
    "name": "User One"
  }
  ```
- **Response**: 返回帶有 JWT 令牌的用戶信息

#### 用戶登入
- **URL**: `POST /api/auth/authenticate`
- **Request Body**:
  ```json
  {
    "username": "user1",
    "password": "password123"
  }
  ```
- **Response**: 返回包含 JWT 令牌的認證信息

#### 刷新令牌
- **URL**: `GET /api/auth/renew`
- **Headers**: `Authorization: Bearer <token>`
- **Response**: 返回新的 JWT 令牌

### 產品管理

#### 獲取產品列表
- **URL**: `GET /products`
- **Query Parameters**:
  - `name`: 產品名稱（可選）
  - `classId`: 產品分類 ID（可選）
  - `page`: 頁碼，從 0 開始（默認 0）
  - `size`: 每頁大小（默認 10）
  - `sort`: 排序字段，格式為 `field,direction`（默認 `id,desc`）
- **Response**: 返回分頁產品列表

#### 獲取特定產品
- **URL**: `GET /products/{id}`
- **Path Parameters**:
  - `id`: 產品 ID
- **Response**: 返回特定產品詳情

#### 創建產品
- **URL**: `POST /products`
- **Headers**: `Authorization: Bearer <token>`
- **Request Body**:
  ```json
  {
    "name": "產品名稱",
    "price": 100,
    "picture": "http://example.com/image.jpg",
    "productClassId": 1
  }
  ```
- **Response**: 返回創建的產品信息

#### 批量創建產品
- **URL**: `POST /products/batch`
- **Headers**: `Authorization: Bearer <token>`
- **Request Body**:
  ```json
  [
    {
      "name": "產品名稱1",
      "price": 100,
      "picture": "http://example.com/image1.jpg",
      "productClassId": 1
    },
    {
      "name": "產品名稱2",
      "price": 200,
      "picture": "http://example.com/image2.jpg",
      "productClassId": 2
    }
  ]
  ```
- **Response**: 返回創建的產品列表

#### 更新產品
- **URL**: `PUT /products/{id}`
- **Headers**: `Authorization: Bearer <token>`
- **Path Parameters**:
  - `id`: 產品 ID
- **Request Body**:
  ```json
  {
    "name": "新產品名稱",
    "price": 120,
    "picture": "http://example.com/new-image.jpg",
    "productClassId": 2
  }
  ```
- **Response**: 返回更新的產品信息

#### 刪除產品
- **URL**: `DELETE /products/{id}`
- **Headers**: `Authorization: Bearer <token>`
- **Path Parameters**:
  - `id`: 產品 ID
- **Response**: 返回操作結果

### 產品分類

#### 獲取分類列表
- **URL**: `GET /product-classes`
- **Query Parameters**:
  - `name`: 分類名稱（可選）
  - `page`: 頁碼，從 0 開始（默認 0）
  - `size`: 每頁大小（默認 10）
- **Response**: 返回分頁分類列表

#### 獲取特定分類
- **URL**: `GET /product-classes/{id}`
- **Path Parameters**:
  - `id`: 分類 ID
- **Response**: 返回特定分類詳情

#### 創建分類
- **URL**: `POST /product-classes`
- **Headers**: `Authorization: Bearer <token>`
- **Request Body**:
  ```json
  {
    "name": "分類名稱",
    "description": "分類描述"
  }
  ```
- **Response**: 返回創建的分類信息

#### 更新分類
- **URL**: `PUT /product-classes/{id}`
- **Headers**: `Authorization: Bearer <token>`
- **Path Parameters**:
  - `id`: 分類 ID
- **Request Body**:
  ```json
  {
    "name": "新分類名稱",
    "description": "新分類描述"
  }
  ```
- **Response**: 返回更新的分類信息

#### 刪除分類
- **URL**: `DELETE /product-classes/{id}`
- **Headers**: `Authorization: Bearer <token>`
- **Path Parameters**:
  - `id`: 分類 ID
- **Response**: 返回操作結果

### 預訂管理

#### 獲取預訂列表
- **URL**: `GET /bookings`
- **Query Parameters**:
  - `memberId`: 會員 ID（可選）
  - `status`: 預訂狀態（可選）
  - `page`: 頁碼，從 0 開始（默認 0）
  - `size`: 每頁大小（默認 10）
- **Response**: 返回分頁預訂列表

#### 獲取特定預訂
- **URL**: `GET /bookings/{id}`
- **Path Parameters**:
  - `id`: 預訂 ID
- **Response**: 返回特定預訂詳情

#### 創建預訂
- **URL**: `POST /bookings`
- **Headers**: `Authorization: Bearer <token>`
- **Request Body**:
  ```json
  {
    "memberId": 1,
    "details": [
      {
        "productId": 1,
        "quantity": 2
      },
      {
        "productId": 2,
        "quantity": 1
      }
    ]
  }
  ```
- **Response**: 返回創建的預訂信息

#### 更新預訂
- **URL**: `PUT /bookings/{id}`
- **Headers**: `Authorization: Bearer <token>`
- **Path Parameters**:
  - `id`: 預訂 ID
- **Request Body**:
  ```json
  {
    "status": "CONFIRMED"
  }
  ```
- **Response**: 返回更新的預訂信息

#### 取消預訂
- **URL**: `DELETE /bookings/{id}`
- **Headers**: `Authorization: Bearer <token>`
- **Path Parameters**:
  - `id`: 預訂 ID
- **Response**: 返回操作結果

## 安全說明

系統使用 JWT 進行 API 保護。除了 `/api/auth/*` 端點外，所有請求需要有效的 JWT 令牌。令牌應在請求頭中以 Bearer 形式提供：

```
Authorization: Bearer <token>
```

JWT 配置特性：
- 令牌過期時間: 24 小時
- 刷新令牌: 支持通過 `/api/auth/renew` 刷新令牌
- 令牌加密算法: HS256

## 開發指南

### 創建新功能

1. 定義 DTO 類 (request/response)
2. 創建或更新 Entity 類
3. 添加 Repository 接口
4. 實現 Service 層邏輯
5. 創建 Controller 端點
6. 添加必要的測試

### 資料庫遷移

使用 Flyway 管理數據庫遷移。遷移腳本位於 `src/main/resources/db/migration` 目錄，命名格式為 `V<version>__<description>.sql`。

```sql
-- 示例: V1__init_schema.sql
CREATE TABLE product_class (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    created_by VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

執行 Flyway 遷移命令：

```bash
mvn flyway:migrate
```

### 監控與指標

應用集成了 Spring Boot Actuator，提供了豐富的監控端點：

- 健康檢查: `/actuator/health`
- 指標信息: `/actuator/metrics`
- 環境信息: `/actuator/env`
- 應用信息: `/actuator/info`
- Flyway 遷移: `/actuator/flyway`
- 日誌配置: `/actuator/loggers`

系統還通過 TimingAspect 實現了方法執行時間的監控，可以幫助發現性能問題。

### 錯誤碼說明

系統定義了統一的錯誤碼體系，方便前端處理錯誤情況：

- 0: 成功
- 10000-19999: 一般錯誤
  - 10000: 系統錯誤
  - 10001: 參數錯誤
  - 10002: 資源不存在
- 20000-29999: 認證錯誤
  - 20000: 未授權
  - 20001: 令牌過期
  - 20002: 無效憑證
- 30000-39999: 產品錯誤
  - 30000: 產品不存在
  - 30001: 產品分類不存在
- 40000-49999: 預訂錯誤
  - 40000: 預訂不存在
  - 40001: 預訂衝突
- 50000-59999: 會員錯誤
  - 50000: 會員不存在
  - 50001: 會員已存在

### 代碼風格

- 使用 4 個空格縮進
- 類名使用 PascalCase
- 方法名和變量名使用 camelCase
- 常量使用 UPPER_SNAKE_CASE
- 包名使用小寫
- 實體類使用 `@Entity`、`@Table` 等注解
- DTO 類使用 `@Data`、`@Builder` 等注解
- Controller 類使用 `@RestController`、`@RequestMapping` 等注解
- Service 類使用 `@Service`、`@Transactional` 等注解
- Repository 類使用 `@Repository` 注解

## 系統擴展與未來功能

### 計劃中的功能
- 產品庫存管理
- 會員積分系統
- 促銷活動管理
- 多語言支持
- 支付集成
- 報表和數據分析

### 擴展建議
- 使用 Redis 添加緩存
- 實現分布式鎖處理併發訂單
- 引入消息隊列處理異步任務
- 添加 Elasticsearch 提升搜索體驗
- 集成第三方文件存儲

## 常見問題

### Q: 啟動應用時出現 "Table 'schema_version' doesn't exist" 錯誤
A: 這是 Flyway 的錯誤，表示數據庫中沒有遷移歷史表。設置 `spring.flyway.baseline-on-migrate=true` 可以解決此問題。

### Q: 如何更改服務器端口？
A: 在 `application.yml` 中修改 `server.port` 屬性。

### Q: 如何禁用 Swagger 文檔？
A: 在 `application.yml` 中設置 `springdoc.api-docs.enabled=false`。

### Q: 令牌過期時間如何設置？
A: 在 `application.yml` 中修改 `app.jwt.expiration` 屬性，值為毫秒數。

## 授權

本專案採用 MIT 授權。詳見 LICENSE 文件。

## 聯繫方式

如有任何問題，請聯繫項目維護者。
