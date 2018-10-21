import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class FirstTest {

  private AppiumDriver driver;

  @Before
  public void setUp() throws Exception{

    DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities.setCapability("platformName", "Android");
    capabilities.setCapability("deviceName", "AndroidTestDevice");
    capabilities.setCapability("platformVersion", "6.0");
    capabilities.setCapability("automationName", "Appium");
    capabilities.setCapability("appPackage", "org.wikipedia");
    capabilities.setCapability("appActivity", ".main.MainActivity");
    capabilities.setCapability("app", "/Users/mfomicheva/Documents/GitHub/mobile_automation/apks/org.wikipedia.apk");

    driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);

  }
  @After
  public void tearDown() {
    driver.quit();
  }

  @Test
  public void firstTest() {

    waitForElementAndClick(By.xpath("//*[contains(@text,'Search Wikipedia')]"),
            "Cannot find 'Search wikipedia' input",
            5);


    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search…')]"),
            "Java",
            "cannot find search input",
            5
            );

    waitForElementPresent(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
            "cannot find 'Object-oriented programming language' searching by 'java'",
            15
    );
  }

  @Test

  public void testCancelSearch() {

    waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot find 'Search wikipedia' input",
            5 );

    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search…')]"),
            "Java",
            "cannot find search input",
            5
    );

    waitForElementClear(
            By.id("search_src_text"), "cannot find search field", 5
    );

    waitForElementAndClick(
            By.id("org.wikipedia:id/search_close_btn"),
            "Cannot find 'x' button",
            5 );

    waitForElementNotPresent(
            By.id("org.wikipedia:id/search_close_btn"),
            " 'x' is still present",
            5);

  }

  @Test

  public void testCompareArticleTitle() {

    waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot find 'Search wikipedia' input",
            5);

    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search…')]"),
            "Java",
            "cannot find search input",
            5
    );

    waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
            "cannot find 'Object-oriented programming language' searching by 'java'",
            5)

    ;

    WebElement title_element = waitForElementPresent(By.id("org.wikipedia:id/view_page_title_text"),
            "cannot find article title",
            15);

    String article_title = title_element.getAttribute("text");

    Assert.assertEquals("Title doesn't match", "Java (programming language)", article_title);


  }

  @Test
  public void testSearchInput(){

    waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot find 'Search wikipedia' input",
            5);

    WebElement search_item = waitForElementPresent(By.id("org.wikipedia:id/search_src_text"),
            "Search box is present",
            5);
    String search_input = search_item.getAttribute("text");

    Assert.assertEquals("Search not found", "Search…" , search_input);

  }

  @Test

  //Ex3 Ищет какое-то слово
//  Убеждается, что найдено несколько статей
//  Отменяет поиск
//  Убеждается, что результат поиска пропал

  public void testSearchAndCancel() {

    waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot find 'Search wikipedia' input",
            5);

    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search…')]"),
            "Java",
            "cannot find search input",
            5);

    List<WebElement> articles = driver.findElements(By.id("org.wikipedia:id/page_list_item_title"));
    int count = articles.size();
    Assert.assertTrue("less than 2 articles found",count >= 2 == true);

    waitForElementClear(
            By.id("search_src_text"), "cannot find search field", 5
    );

    waitForElementAndClick(
            By.id("org.wikipedia:id/search_close_btn"),
            "Cannot find 'x' button",
            5 );

    waitForElementNotPresent(
            By.id("org.wikipedia:id/search_close_btn"),
            " 'x' is still present",
            5);


  }

  @Test
 // EX 4 Ищет какое-то слово
  //Убеждается, что в каждом результате поиска есть это слово.
  public void testSearchItemPresent() {

    waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot find 'Search wikipedia' input",
            5);

    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search…')]"),
            "Java",
            "cannot find search input",
            5);


    List<WebElement> articles = driver.findElements(By.id("org.wikipedia:id/page_list_item_title"));
    int count = articles.size();
    for (int i = 0; i< count; i++ ) {
      String article_title = articles.get(i).getText();
      assertTrue("article title doesn't contain 'java'", article_title.contains("Java") == true);

    }
}

  private WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds) {

    WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
    wait.withMessage(error_message + "\n");
    return wait.until(
            ExpectedConditions.presenceOfElementLocated(by));

  }

  private WebElement waitForElementPresent(By by, String error_message) {

    return waitForElementPresent(by, error_message, 5);

  }

  private WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds){
    WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
    element.click();
    return element;
  }
  private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds){
    WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
    element.sendKeys(value);
    return element;
  }

  private boolean waitForElementNotPresent(By by, String error_message,long timeoutInSeconds ) {
    WebDriverWait wait  = new WebDriverWait(driver,timeoutInSeconds);
    wait.withMessage(error_message + "\n");
    return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
  }

  private WebElement waitForElementClear(By by, String error_message,long timeoutInSeconds) {
    WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
    element.clear();
    return element;
  }

}
