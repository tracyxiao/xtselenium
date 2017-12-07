package pages;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tools.ExcelData;
import tools.BasePage;

import java.util.HashMap;

/**
 * Created by P0061799 on 2017/9/26.
 */
public class boKeYuan {

    @DataProvider(name="user")
    public Object[][] Numbers() throws Exception {
        ExcelData e=new ExcelData("E:\\selenium\\xtselenium\\src\\main\\resources\\user.xlsx");
        //System.out.println(e);
        return e.getExcelData();
    }

    @Test(dataProvider="user")
    public void testBoKeYuan(HashMap<String, String> data) throws InterruptedException {
        /*System.setProperty("webdriver.chrome.driver","E:\\selenium\\xtselenium\\src\\main\\resources\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();*/
        /*driver.get("https://passport.cnblogs.com/user/signin?ReturnUrl=https%3A%2F%2Fwww.cnblogs.com%2F");
        WebElement user = driver.findElement(By.id("input1"));
        user.sendKeys(data.get("user"));
        WebElement  password= driver.findElement(By.id("input2"));
        password.sendKeys(data.get("password"));
        driver.findElement(By.id("signin")).click();
        Thread.sleep(5000);
        if(data.get("istrue").equals("f")) {
            Assert.assertEquals(driver.findElement(By.id("tip_btn")).getText(),data.get("assert"));
        }
        else {
            Thread.sleep(5000);
            Assert.assertEquals(driver.findElement(By.id("user_nav_blog_link")).getText(),data.get("assert"));
        }
        //driver.findElement(By.linkText("退出")).click();
        //driver.close();*/
        BasePage basePage=new BasePage("demo");
        basePage.startTest("c","https://passport.cnblogs.com/user/signin?ReturnUrl=https%3A%2F%2Fwww.cnblogs.com%2F");
        basePage.getLocator("user_account").sendKeys(data.get("user"));
        basePage.getLocator("user_password").sendKeys(data.get("password"));
    }
}


