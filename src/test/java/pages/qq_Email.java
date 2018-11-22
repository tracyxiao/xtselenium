package pages;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import tools.ExcelData;
import tools.BasePage;

import java.util.HashMap;

/**
 * Created by P0061799 on 2017/12/5.
 */
@Listeners({ZTestReport.class})
public class qq_Email {
   @DataProvider(name="user")
    public Object[][] Numbers() throws Exception {
        ExcelData e=new ExcelData("src\\main\\resources\\user.xlsx");
        return e.getExcelData();
    }
    @Test(dataProvider="user",description = "编号1：正确的用户名和密码；编号2：错误的密码")
    public void login_test(HashMap<String, String> data) throws InterruptedException {
        BasePage basePage=new BasePage("qqemail");
        basePage.startTest("c","https://mail.qq.com/");
        basePage.driver.switchTo().frame("login_frame");
        basePage.getLocator("login_byaccount").click();
        basePage.getLocator("user_account").clear();
        basePage.getLocator("user_account").sendKeys(data.get("user"));
        basePage.getLocator("user_password").sendKeys(data.get("password"));
        basePage.getLocator("login_button").click();
        //Thread.sleep(5000);
        if(data.get("istrue").equals("f")) {
            Assert.assertEquals(basePage.getLocator("login_fail_assert").getText(),data.get("assert"));
        }
        else {
            Assert.assertEquals(basePage.getLocator("login_success_assert").getText(),data.get("assert"));
            //basePage.getLocator("logout_button").click();
        }
    }
}
