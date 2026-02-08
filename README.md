Swag Labs 自動化測試練習 (Selenium + Java)
嗨！我是 Seal。 這是我為了轉職軟體測試工程師 (QA) 所建立的練習專案。 我選擇了 Swag Labs (Sauce Demo) 這個標準測試網站，來模擬真實電商系統的自動化測試流程。
這個專案紀錄了我如何從零開始，使用 Java 和 Selenium 建立自動化腳本，並練習 Git 版本控制。

我將測試情境寫在 src/test/java 資料夾中，目前已完成：

1.登入功能 (Login)
驗證「標準使用者 (Standard User)」能成功登入並跳轉首頁。
驗證「被鎖定使用者 (Locked Out User)」登入時，系統是否會跳出錯誤訊息。

2.購物車與結帳流程 (Shopping)
模擬使用者點擊商品，並驗證右上角購物車圖示的數量是否正確更新
結帳測試執行完整的購物旅程，並驗證最終的訂單完成訊息

技術優化與學習心得

1.定位策略優化
將錄製腳本中不穩定且冗長的 CSS Selector (如 div:nth-child(3))，透過 Chrome DevTools 分析，優化為穩定且唯一的 By.id 定位。
移除錄製時產生的冗餘滑鼠 操作 (無意義的 Mouse Hover 與 Click)，讓腳本執行更乾淨俐落。

2.環境穩定性
發現 Chrome 預設會彈出密碼儲存提示與外洩警告，導致測試中斷。
解決方案：在 ChromeOptions 加入 --incognito (無痕模式) 與禁用密碼管理員參數，確保測試環境純淨。

3.程式碼封裝
將重複使用的「登入步驟」封裝為 login() Helper Method，讓購物車測試腳本只需一行指令即可完成登入，提高程式碼重用性。

4.斷言機制
使用 JUnit 的 assertEquals 與 assertTrue，確保每一個測試步驟（如網址跳轉、錯誤訊息顯示、訂單完成）都有實際的驗證結果，而非僅是操作介面。