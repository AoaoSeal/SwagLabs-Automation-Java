import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ShoppingTest {

    private WebDriver driver;

    @Before
    public void setUp() {
        // --- 這裡直接沿用LoginTest 已經寫好的設定 ---
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito"); // 無痕模式

        // 禁用密碼管理員
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // 因為購物車測試的前提是「必須先登入」，我們不想在每個測試裡都重寫一次登入步驟
    private void login() {
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
    }

    // 測試案例 1: 加入商品到購物車
    @Test
    public void testAddProductToCart() throws InterruptedException {
        // 1. 先呼叫小幫手登入
        login();

        // 2. 找到第一個商品 (Sauce Labs Backpack) 的 "Add to cart" 按鈕並點擊
        // 這裡用 id 定位最穩
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();

        // 3. 驗證：右上角購物車圖示應該出現 "1"
        String badgeText = driver.findElement(By.className("shopping_cart_badge")).getText();
        assertEquals("1", badgeText);
        Thread.sleep(3000);
    }

    // 測試案例 2: 完整的結帳流程
    @Test
    public void testCheckoutFlow() throws InterruptedException {
        // 1. 登入 (呼叫小幫手)
        login();

        // 2. 把商品加入購物車
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();

        // 3. 點擊右上角購物車圖示，進入購物車頁面
        driver.findElement(By.className("shopping_cart_link")).click();

        // 4. 點擊 "Checkout" 按鈕
        driver.findElement(By.id("checkout")).click();

        // 5. 填寫收件人資訊 (隨便填，因為是測試網)
        driver.findElement(By.id("first-name")).sendKeys("Wen-Liang");
        driver.findElement(By.id("last-name")).sendKeys("Wang");
        driver.findElement(By.id("postal-code")).sendKeys("400");

        // 6. 點擊 "Continue"
        driver.findElement(By.id("continue")).click();

        // 7. 點擊 "Finish" 完成訂單
        driver.findElement(By.id("finish")).click();

        // 8. 關鍵驗證 (Assertion)：確認看到 "Thank you for your order!"
        // 這是最重要的一步，證明錢付了、訂單成立了
        String successMessage = driver.findElement(By.className("complete-header")).getText();
        assertEquals("Thank you for your order!", successMessage);

        Thread.sleep(3000);
    }



}