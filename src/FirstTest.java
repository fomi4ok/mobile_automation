import io.appium.java_client.AppiumDriver;
import io.appium.java_client.SwipeElementDirection;
import io.appium.java_client.TouchAction;
import lib.CoreTestCase;
import lib.ui.*;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;


public class FirstTest extends CoreTestCase{

  private MainPageObject MainPageObject;
  protected void setUp() throws Exception {

    super.setUp();
    MainPageObject = new MainPageObject(driver);
  }



  @Test
  public void testSearch() {

    SearchPageObject SearchPageObject = new SearchPageObject(driver);
    SearchPageObject.initSearchInput();
    SearchPageObject.typeSearchLine("Java");
    SearchPageObject.waitForSearchResult("Object-oriented programming language");

  }

  @Test

  public void testCancelSearch() {

    SearchPageObject SearchPageObject = new SearchPageObject(driver);

    SearchPageObject.initSearchInput();
    SearchPageObject.waitForCancelButtonToAppear();
    SearchPageObject.clickCancelSearch();
    SearchPageObject.waitForCancelButtonToDisappear();

  }

  @Test

  public void testCompareArticleTitle() {

    SearchPageObject SearchPageObject = new SearchPageObject(driver);

    SearchPageObject.initSearchInput();
    SearchPageObject.typeSearchLine("Java");
    SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

    ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
    String article_title = ArticlePageObject.getArticleTitle();

    Assert.assertEquals("Title doesn't match", "Java (programming language)",article_title) ;


  }

  @Test

  public void testSwipeArticle() {

    SearchPageObject SearchPageObject = new SearchPageObject(driver);
    SearchPageObject.initSearchInput();
    SearchPageObject.typeSearchLine("Appium");
    SearchPageObject.clickByArticleWithSubstring("Appium");

    ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
    ArticlePageObject.waitForTitleElement();
    ArticlePageObject.swipeToFooter();

  }

  @Test
  public void testSearchInput(){

    MainPageObject.waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot find 'Search wikipedia' input",
            5);

    WebElement search_item = MainPageObject.waitForElementPresent(By.id("org.wikipedia:id/search_src_text"),
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

    SearchPageObject SearchPageObject = new SearchPageObject(driver);
    SearchPageObject.initSearchInput();
    SearchPageObject.typeSearchLine("Java");

    List<WebElement> articles = driver.findElements(By.id("org.wikipedia:id/page_list_item_title"));
    int count = articles.size();
    Assert.assertTrue("less than 2 articles found",count >= 2 == true);

    SearchPageObject.waitForCancelButtonToAppear();
    SearchPageObject.clickCancelSearch();
    SearchPageObject.clickCancelSearch();
    SearchPageObject.waitForCancelButtonToDisappear();


  }

  @Test
 // EX 4 Ищет какое-то слово
  //Убеждается, что в каждом результате поиска есть это слово.
  public void testSearchItemPresent() {

    SearchPageObject SearchPageObject = new SearchPageObject(driver);
    SearchPageObject.initSearchInput();
    SearchPageObject.typeSearchLine("Java");


    List<WebElement> articles = driver.findElements(By.id("org.wikipedia:id/page_list_item_title"));
    int count = articles.size();
    for (int i = 0; i< count; i++ ) {
      String article_title = articles.get(i).getText();
      assertTrue("article title doesn't contain 'java'", article_title.contains("Java") || article_title.contains("java"));

    }
}


  @Test
  public void testSaveFirstArticleToMyList() {

    SearchPageObject SearchPageObject = new SearchPageObject(driver);
    SearchPageObject.initSearchInput();
    SearchPageObject.typeSearchLine("Java");
    SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

    ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
    ArticlePageObject.waitForTitleElement();
    String article_title = ArticlePageObject.getArticleTitle();
    String name_of_the_folder = "Learning programming";
    ArticlePageObject.addArticleToMyList(name_of_the_folder);
    ArticlePageObject.closeArticle();

    NavigationUI NavigationUI = new NavigationUI(driver);
    NavigationUI.clickMyList();

    MyListPageObject MyListPageObject = new MyListPageObject(driver);
    MyListPageObject.openFolderByName(name_of_the_folder);
    MyListPageObject.openFolderByName(name_of_the_folder);
    MyListPageObject.swipeByArticleToDelete(article_title);



  }
 /// ex 5
  @Test
  public void testSaveTwoArticleToMyList() {

    SearchPageObject SearchPageObject = new SearchPageObject(driver);
    SearchPageObject.initSearchInput();
    SearchPageObject.typeSearchLine("Java");
    SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

    ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
    ArticlePageObject.waitForTitleElement();
    String article_title = ArticlePageObject.getArticleTitle();
    String name_of_the_folder = "Learning programming";
    ArticlePageObject.addArticleToMyList(name_of_the_folder);
    ArticlePageObject.closeArticle();
    SearchPageObject.initSearchInput();
    SearchPageObject.typeSearchLine("Java");
    SearchPageObject.clickByArticleWithSubstring("Programming language");
    ArticlePageObject.waitForTitleElement();
    String second_article_title = ArticlePageObject.getArticleTitle();
    ArticlePageObject.addArticleToExistingList(name_of_the_folder);
    ArticlePageObject.closeArticle();
    NavigationUI NavigationUI = new NavigationUI(driver);
    NavigationUI.clickMyList();


    MyListPageObject MyListPageObject = new MyListPageObject(driver);
    MyListPageObject.openFolderByName(name_of_the_folder);
    MyListPageObject.openFolderByName(name_of_the_folder);
    MyListPageObject.swipeByArticleToDelete(article_title);
    MyListPageObject.openArticleFromFolder(second_article_title);
    String title_of_the_article_saved =  ArticlePageObject.getArticleTitle();
   Assert.assertEquals("Title doesn't match", second_article_title, title_of_the_article_saved);

  }

  @Test

  public void testAmountOfNotEmptySearch() {

    SearchPageObject SearchPageObject = new SearchPageObject(driver);
    SearchPageObject.initSearchInput();
    SearchPageObject.typeSearchLine("linkin park discography");
    int amount_of_search_results = SearchPageObject.getAmountOfFoundArticles();

    Assert.assertTrue("We found too few results",
            amount_of_search_results >0);

  }

  @Test

  public void testAmountOfEmptySearch() {

    SearchPageObject SearchPageObject = new SearchPageObject(driver);
    SearchPageObject.initSearchInput();
    SearchPageObject.typeSearchLine("dfpfpfj");



    SearchPageObject.waitForEmptyResultsLabel();
    SearchPageObject.assertThereIsNoResultOfSearch();




  }

  @Test

  public void testElementWithTitle() {

    SearchPageObject SearchPageObject = new SearchPageObject(driver);
    SearchPageObject.initSearchInput();
    SearchPageObject.typeSearchLine("Java");
    SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

    ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
    ArticlePageObject.assertThereIsATitle();


  }

  @Test
  public void testChangeScreenOrientationOnSearchResults() {
    MainPageObject.waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot find 'Search wikipedia' input",
            5);

    String search_line = "Java";

    MainPageObject.waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search…')]"),
            search_line,
            "cannot find search input",
            5);

    MainPageObject.waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
            "cannot find 'Object-oriented programming language' topic searching by " + search_line,
            15);

    String title_before_rotation = MainPageObject.waitForElementAndGetAttribute(
            By.id("org.wikipedia:id/view_page_title_text"),
            "text",
            "Cannot find title of article ",
            15

    );

    driver.rotate(ScreenOrientation.LANDSCAPE);

    String title_after_rotation = MainPageObject.waitForElementAndGetAttribute(
            By.id("org.wikipedia:id/view_page_title_text"),
            "text",
            "Cannot find title of article in landscape ",
            15

    );



    Assert.assertEquals("Article title have been changed after screen rotate", title_before_rotation, title_after_rotation);

    driver.rotate(ScreenOrientation.PORTRAIT);


    String title_after_second_rotation = MainPageObject.waitForElementAndGetAttribute(
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
    MainPageObject.waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot find 'Search wikipedia' input",
            5);

    String search_line = "Java";

    MainPageObject.waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search…')]"),
            search_line,
            "cannot find search input",
            5);

    MainPageObject.waitForElementPresent(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
            "cannot find 'Object-oriented programming language' searching by 'java'",
            5);

    driver.runAppInBackground(2);


    MainPageObject.waitForElementPresent(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
            "cannot find article after returning from background",
            5);




  }





}
