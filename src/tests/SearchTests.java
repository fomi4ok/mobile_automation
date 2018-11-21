package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SearchTests extends CoreTestCase{

  @Test
  public void testSearch() {

    SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
    SearchPageObject.initSearchInput();
    SearchPageObject.typeSearchLine("Java");
    SearchPageObject.waitForSearchResult("Object-oriented programming language");

  }

  //ex 9
  @Test
  public void testSearchBy2Substrings() {

    SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
    SearchPageObject.initSearchInput();
    SearchPageObject.typeSearchLine("Java");
    SearchPageObject.assertTheSearchArticleResultMoreThen(3);
    SearchPageObject.waitForElementByTitleAndDescription("Java (programming language)", "Object-oriented programming language");

  }

  @Test

  public void testCancelSearch() {

    SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

    SearchPageObject.initSearchInput();
    SearchPageObject.waitForCancelButtonToAppear();
    SearchPageObject.clickCancelSearch();
    SearchPageObject.waitForCancelButtonToDisappear();
  }

  @Test
  public void testSearchInput(){

    SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
    SearchPageObject.initSearchInput();
  }

  @Test

  //Ex3 Ищет какое-то слово
//  Убеждается, что найдено несколько статей
//  Отменяет поиск
//  Убеждается, что результат поиска пропал

  public void testSearchAndCancel() {


    SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
    SearchPageObject.initSearchInput();
    SearchPageObject.typeSearchLine("Java");

    SearchPageObject.assertTheSearchArticleResultMoreThen(2);

    SearchPageObject.waitForCancelButtonToAppear();

    SearchPageObject.clickCancelSearch();
    if (Platform.getInstance().isAndroid()) {
       SearchPageObject.clickCancelSearch();
    } else {
      return; }
    SearchPageObject.waitForCancelButtonToDisappear();

  }

  @Test
  // EX 4 Ищет какое-то слово
  //Убеждается, что в каждом результате поиска есть это слово.
  public void testSearchItemPresent() {

    SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
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

  public void testAmountOfNotEmptySearch() {

    SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
    SearchPageObject.initSearchInput();
    SearchPageObject.typeSearchLine("linkin park discography");
    int amount_of_search_results = SearchPageObject.getAmountOfFoundArticles();

    assertTrue("We found too few results",
            amount_of_search_results >0);

  }

  @Test

  public void testAmountOfEmptySearch() {

    SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
    SearchPageObject.initSearchInput();
    SearchPageObject.typeSearchLine("dfpfpfj");
    SearchPageObject.waitForEmptyResultsLabel();
    SearchPageObject.assertThereIsNoResultOfSearch();
  }

  @Test

  public void testElementWithTitle() {

    SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
    SearchPageObject.initSearchInput();
    SearchPageObject.typeSearchLine("Java");
    SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

    ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
    ArticlePageObject.assertThereIsATitle();
  }

}
