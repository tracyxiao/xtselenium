package tools;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.*;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by P0061799 on 2017/12/1.
 */
public class BasePage {
    public ParseYamlUntil locator;
    public WebDriver driver;

    public BasePage(String yamlFileName){
        locator=new ParseYamlUntil(yamlFileName);
    }

    public By getBy(String type, String value) {
        By by = null;
        if(type.equals("id")) {
            by = By.id(value);
        }

        if(type.equals("name")) {
            by = By.name(value);
        }

        if(type.equals("xpath")) {
            by = By.xpath(value);
        }

        if(type.equals("className")) {
            by = By.className(value);
        }

        if(type.equals("linkText")) {
            by = By.linkText(value);
        }

        return by;
    }

    public WebElement getLocator(String key) {
        WebElement element=null;
        //WebDriverWait wait;
        if(locator.ml.containsKey(key)) {
            HashMap<String, String> m = (HashMap)locator.ml.get(key);
            final String type =m.get("type");
            final String value =m.get("value");
                try {
                    //wait= new WebDriverWait(driver, 30);
                    Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)

                    .withTimeout(30, TimeUnit.SECONDS)

                    .pollingEvery(2, TimeUnit.SECONDS)

                    .ignoring(NoSuchElementException.class);

                    element=wait.until(new ExpectedCondition<WebElement>() {
                        public WebElement apply(WebDriver d) {
                            return d.findElement(getBy(type,value));
                        }
                    });
                } catch (Exception var10) {
                    element = null;
                }
            /*wait= new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.visibilityOfElementLocated(getBy(type,value)));
            element=driver.findElement(getBy(type,value));*/
        }

        else {
            System.out.println("Locator " + key + " is not exist in " +locator.yamlFile + ".yaml");
        }

        return element;
    }

    public boolean startTest(String explore,String pageUrl) {
        try {
            try {
                if ("f".equals(explore)) {
                    System.out.println("firefox");
                    System.setProperty("webdriver.gecko.driver", "src/main/drivers/geckodriver.exe");
                    driver = new FirefoxDriver();
                    driver.get(pageUrl);
                } else if ("ie".equals(explore)) {
                    System.out.println("internet explorer");
                    System.setProperty("webdriver.ie.driver", "src/main/drivers/IEDriverServer.exe");
                    DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
                    desiredCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                    desiredCapabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
                    desiredCapabilities.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, pageUrl);
                    driver= new InternetExplorerDriver(desiredCapabilities);
                    driver.get(pageUrl);
                } else {
                    System.out.println("chrome");
                    System.setProperty("webdriver.chrome.driver", "src/main/drivers/chromedriver.exe");
                    driver = new ChromeDriver();
                    driver.get(pageUrl);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

    }


}
