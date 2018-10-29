import io.appium.java_client.AppiumDriver;
import io.appium.java_client.SwipeElementDirection;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
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

    driver.rotate(ScreenOrientation.PORTRAIT);

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

  public void testSwipeArticle() {

    waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot find 'Search wikipedia' input",
            5);

    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search…')]"),
            "Appium",
            "cannot find search input",
            5
    );

    waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Appium']"),
            "cannot find 'Appium' searching by 'Appium'",
            5)

    ;

   waitForElementPresent(By.id("org.wikipedia:id/view_page_title_text"),
            "cannot find article title",
            15);



    swipeUpToFindElement(
            By.xpath("//*[@text='View page in browser']"),
            "cannot find the end of the article",
            20);







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
      assertTrue("article title doesn't contain 'java'", article_title.contains("Java") || article_title.contains("java"));

    }
}


  @Test
  public void saveFirstArticleToMyList() {

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

    waitForElementPresent(By.id("org.wikipedia:id/view_page_title_text"),
            "cannot find article title",
            15);

    waitForElementAndClick(
            By.xpath("//android.widget.ImageView[@content-desc='More options']"),
            "cannot find button to open article options",
            5);
    waitForElementAndClick(
            By.xpath("//*[@text='Add to reading list']"),
            "cannot find option to add to the reading list",
            5
    );

    waitForElementAndClick(
            By.id("org.wikipedia:id/onboarding_button"),
            "cannot find 'got it' tip overlay",
            5);

    waitForElementClear(
            By.id("org.wikipedia:id/text_input"),
            "cannot find input to set name of articles folder",
            5);

    String name_of_the_folder = "Learning programming";

    waitForElementAndSendKeys(By.id("org.wikipedia:id/text_input"),
            name_of_the_folder,
            "cannot put text in the article folder input",
            5);
    waitForElementAndClick(
            By.xpath("//*[@text='OK']"),
            "Cannot press OK button",
            5);

    waitForElementAndClick(
            By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
            "cannot close article, cannot find X link",
            5);

    waitForElementAndClick(
            By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
            "cannot find navigation button to My lists",
            5);

    waitForElementAndClick(
            By.xpath("//*[@text='" + name_of_the_folder+"']"),
            "cannot find created folder ",
            5);

    swipeElementToLeft(By.xpath("//*[@text='Java (programming language)']"),
            "Cannot find saved article");

    waitForElementNotPresent(By.xpath("//*[@text='Java (programming language)']"),
            "cannot delete saved article",
            5);

  }
 /// ex 5
  @Test
  public void saveTwoArticleToMyList() {

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
            5);

    //
    waitForElementPresent(By.id("org.wikipedia:id/view_page_title_text"),
            "cannot find article title",
            15);

    waitForElementAndClick(
            By.xpath("//android.widget.ImageView[@content-desc='More options']"),
            "cannot find button to open article options",
            5);

    waitForElementAndClick(
            By.xpath("//*[@text='Add to reading list']"),
            "cannot find option to add to the reading list",
            5
    );

    waitForElementAndClick(
            By.id("org.wikipedia:id/onboarding_button"),
            "cannot find 'got it' tip overlay",
            5);

    waitForElementClear(
            By.id("org.wikipedia:id/text_input"),
            "cannot find input to set name of articles folder",
            5);

    String name_of_the_folder = "Learning programming";

    waitForElementAndSendKeys(By.id("org.wikipedia:id/text_input"),
            name_of_the_folder,
            "cannot put text in the article folder input",
            5);

    waitForElementAndClick(
            By.xpath("//*[@text='OK']"),
            "Cannot press OK button",
            5);

    waitForElementAndClick(
            By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
            "cannot close article, cannot find X link",
            5);

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
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Programming language']"),
            "cannot find 'programming language' searching by 'java'",
            5);

    waitForElementPresent(By.id("org.wikipedia:id/view_page_title_text"),
            "cannot find article title",
            15);

    waitForElementAndClick(
            By.xpath("//android.widget.ImageView[@content-desc='More options']"),
            "cannot find button to open article options",
            5);

    waitForElementAndClick(
            By.xpath("//*[@text='Add to reading list']"),
            "cannot find option to add to the reading list",
            15
    );

    waitForElementAndClick(
            By.xpath("//android.widget.LinearLayout//*[@text='" + name_of_the_folder+"']"),
            "cannot find created folder",
            5);



    waitForElementAndClick(
            By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
            "cannot close article, cannot find X link",
            15);

    waitForElementAndClick(
            By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
            "cannot find navigation button to My lists",
            5);

    waitForElementAndClick(
            By.xpath("//android.widget.LinearLayout//*[@text='" + name_of_the_folder+"']"),
            "cannot find created folder after 2 articles are added ",
            15);

    swipeElementToLeft(By.xpath("//*[@text='Java (programming language)']"),
            "Cannot find saved article");

    waitForElementNotPresent(By.xpath("//*[@text='Java (programming language)']"),
            "cannot delete saved article",
            5);

    WebElement article_title = waitForElementPresent(By.xpath("//*[@text='JavaScript']"),
            "article title not found",
            5);

    String actual_title = article_title.getAttribute("text");

    String expected_title = "JavaScript";

   Assert.assertEquals("Title doesn't match", expected_title, actual_title);

  }

  @Test

  public void testAmountOfNotEmptySearch() {

    waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot find 'Search wikipedia' input",
            5);

    String search_line = "linkin park discography";

    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search…')]"),
            search_line,
            "cannot find search input",
            5
    );

    String search_result_locator =
            "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
    waitForElementPresent(
            By.xpath(search_result_locator),
            "Cannot find anything by request " + search_line,
            15);

    int amount_of_search_results = getAmmountOfElements(By.xpath(search_result_locator));

    Assert.assertTrue("We found too few results",
            amount_of_search_results >0);

  }

  @Test

  public void testAmountOfEmptySearch() {

    waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot find 'Search wikipedia' input",
            5);

    String search_line = "dfpfpfj";

    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search…')]"),
            search_line,
            "cannot find search input",
            5);

    String search_result_locator =
            "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";

    String empty_result_label = "//*[@text='No results found']";

    waitForElementPresent(
            By.xpath(empty_result_label),
            "cannot find empty result label by the request " + search_line,
            15
    );

    assertElementNotPresent(
            By.xpath(search_result_locator),
                    "We found some results by request" + search_line);

  }

  @Test

  public void testElementWithTitle() {

    waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot find 'Search wikipedia' input",
            5);

    String search_line = "Java";

    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search…')]"),
            search_line,
            "cannot find search input",
            5);

    waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Programming language']"),
            "cannot find 'programming language' searching by 'java'",
            5);

    String article_title = "org.wikipedia:id/view_page_title_text";


    assertElementPresent(By.id(article_title),
            "We didnt' find any results by request " + search_line);


  }

  @Test
  public void testChangeScreenOrientationOnSearchResults() {
    waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot find 'Search wikipedia' input",
            5);

    String search_line = "Java";

    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search…')]"),
            search_line,
            "cannot find search input",
            5);

    waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
            "cannot find 'Object-oriented programming language' topic searching by " + search_line,
            15);

    String title_before_rotation = waitForElementAndGetAttribute(
            By.id("org.wikipedia:id/view_page_title_text"),
            "text",
            "Cannot find title of article ",
            15

    );

    driver.rotate(ScreenOrientation.LANDSCAPE);

    String title_after_rotation = waitForElementAndGetAttribute(
            By.id("org.wikipedia:id/view_page_title_text"),
            "text",
            "Cannot find title of article in landscape ",
            15

    );



    Assert.assertEquals("Article title have been changed after screen rotate", title_before_rotation, title_after_rotation);

    driver.rotate(ScreenOrientation.PORTRAIT);


    String title_after_second_rotation = waitForElementAndGetAttribute(
            By.id("org.wikipedia:id/view_page_title_text"),
            "text",
            "Cannot find title of article in portrait ",
            15

    );

    Assert.assertEquals("Article title have been changed after second screen rotate",
            title_before_rotation, title_after_second_rotation);


  }

  @Test

  public void testSearchArticleInBackGround() {
    waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot find 'Search wikipedia' input",
            5);

    String search_line = "Java";

    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search…')]"),
            search_line,
            "cannot find search input",
            5);

    waitForElementPresent(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
            "cannot find 'Object-oriented programming language' searching by 'java'",
            5);

    driver.runAppInBackground(2);


    waitForElementPresent(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
            "cannot find article after returning from background",
            5);




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

  protected void swipeUp(int timeOfSwipe) {
    TouchAction action = new TouchAction(driver);
    Dimension size = driver.manage().window().getSize();
    int x = size.width /2;
    int start_y = (int )(size.height * 0.8);
    int end_y = (int) (size.height * 0.2);

    action
            .press(x, start_y)
            .waitAction()
            .moveTo(x, end_y)
            .release()
            .perform();
  }

  protected void swipeUpQuick() {

    swipeUp(200);

  }

  protected void swipeUpToFindElement (By by, String error_message, int max_swipes) {

    int already_swiped =0;
    driver.findElements(by);
    driver.findElements(by).size();
    while (driver.findElements(by).size() == 0) {
      if (already_swiped > max_swipes) {
        waitForElementPresent(by, "cannot find element by swiping up. \n" + error_message, 0);
        return;
      }
      swipeUpQuick();
      ++ already_swiped;
    }
  }

  protected void swipeElementToLeft(By by, String error_message) {
    WebElement element = waitForElementPresent(
            by,
            error_message,10);
    int left_x = element.getLocation().getX();
    int right_x = left_x + element.getSize().getWidth();
    int upper_y = element.getLocation().getY();
    int lower_y = upper_y + element.getSize().getHeight();
    int middle_y = (upper_y + lower_y) / 2 ;

    TouchAction action = new TouchAction(driver);
    action
            .press(right_x, middle_y)
            .waitAction(300)
            .moveTo(left_x, middle_y)
            .release()
            .perform();
  }

  private int getAmmountOfElements(By by) {
    List elements = driver.findElements(by);
    return elements.size();
  }

  private void assertElementNotPresent (By by, String error_message) {
    int amount_of_elements = getAmmountOfElements(by);
    if (amount_of_elements > 0) {
      String default_message = "An element '" + by.toString() + "' supposed to be not present";
      throw new AssertionError(default_message + " " + error_message);
    }
  }

  private void assertElementPresent (By by, String error_message) {
    int amount_of_elements = getAmmountOfElements(by);
    if (amount_of_elements != 1) {
      String default_message = "An element '" + by.toString() + "' supposed to be not present";
      throw new AssertionError(default_message + " " + error_message);
    }
  }


  private String waitForElementAndGetAttribute(By by, String attribute, String error_message, long timeoutInSeconds)
  {
    WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
    return element.getAttribute(attribute);
  }
}
