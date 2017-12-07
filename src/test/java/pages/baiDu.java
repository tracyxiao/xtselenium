package pages;
import org.testng.annotations.Test;
import tools.BasePage;

/**
 * Created by P0061799 on 2017/11/28.
 */
public class baiDu {
    @Test
    public void testBaidu() {
        BasePage basePage=new BasePage("demo");
        basePage.startTest("c","http://www.baiDu.com/");
        basePage.getLocator("baidu_input").sendKeys("Glen");
        basePage.getLocator("baidu_button").click();
        basePage.driver.close();
    }
}
