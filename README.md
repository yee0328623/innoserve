# 水域安全小幫手 💧

一個專為水域活動設計的 Android 應用程式，旨在提升使用者的水域安全意識，並在靠近危險水域時發出即時警示，減少意外發生機率。

## 參賽官網

🎉 本專案成功晉級第 28 屆決賽組
[大專校院資訊應用服務創新競賽](https://innoserve.tca.org.tw/)

## 📌 專案簡介

台灣四面環海、河川豐富，水域活動盛行。然而，每年因接近危險水域導致的溺水事件層出不窮。本應用結合政府開放資料與即時定位技術，提供使用者以下服務：

- 📍 危險水域即時警示
- 🌦 即時天氣資訊（來自中央氣象署 API）
- 🧭 地區查詢與歷史事件統計
- 🚨 危險水域通報功能

## ✨ 功能特色

- **GPS 危險水域偵測**：當使用者接近危險水域 100 公尺內，自動跳出警示視窗與警報聲。
- **天氣查詢**：即時查詢當地或指定地點的天氣、溫度、濕度、降雨機率等。
- **地區事故統計查詢**：選擇縣市/年份/水域型態，查詢歷年事故資料。
- **危險通報系統**：使用者可上傳遇到的潛在危險水域情況，供後端通報警消單位。
- **會員系統**：註冊、登入、資料修改、忘記密碼等功能。

## 🛠 使用技術

- **Android App 開發**：Android Studio + Java
- **地圖功能**：Google Maps API
- **後端服務**：Google Cloud Platform (GCP)
- **資料庫**：MySQL
- **資料來源**：
  - 政府開放資料平台（危險水域、事故統計）
  - 中央氣象署開放 API（天氣預報）

## 📷 系統介面預覽

### 首頁畫面
![首頁畫面](![image](https://github.com/user-attachments/assets/a8d08b9e-a083-45fc-b144-de0c53065bcd)
)

### 登入畫面
![登入畫面](![image](https://github.com/user-attachments/assets/160bece7-0cd3-4226-81c2-ac4cec8b7c3d)
)

### 危險水域警示
![危險警示](images/screenshot_3.png)

### 天氣查詢功能
![天氣查詢](images/screenshot_4.png)

### 地區查詢功能
![地區查詢](images/screenshot_5.png)

### 危險通報畫面
![危險通報](images/screenshot_6.png)

## 🧪 安裝與執行方式

1. Clone 本專案：
   ```bash
   git clone https://github.com/your-username/water-safety-helper.git
   ```
2. 開啟 Android Studio，Import 本專案資料夾。
3. 設定 Google Maps API 金鑰至 `google_maps_api.xml`。
4. 執行模擬器或實機測試。

## 🔮 未來展望

- ✅ 危險通報整合政府警消系統
- 🤝 與地方政府、學校合作擴大資料來源與推廣
- 📡 增加離線警示功能（斷網情境）
- 🔒 強化資料安全與使用者隱私保護

## 🙋‍♀️ 作者與貢獻者

- 黃楚宜、林鈺展、劉皓宇、陳孟裕、孔之儀  
- 指導老師：黃錦祥

## 📚 參考資料

- 中央氣象署開放平台：[https://opendata.cwa.gov.tw](https://opendata.cwa.gov.tw)
- 政府資料開放平台：[https://data.gov.tw](https://data.gov.tw)
- Google Maps API
- Android Studio 官方文件
