package lib.ui;


import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

import static org.junit.Assert.assertTrue;

abstract public class SearchPageObject extends MainPageObject {


  public SearchPageObject(AppiumDriver driver) {

    super(driver);
  }

   protected static String
          SEARCH_INIT_ELEMENT,
          SEARCH_INPUT,
          SEARCH_RESULT_BY_SUBSTRING_TPL,
          SEARCH_CANCEL_BUTTON,
          SEARCH_RESULT_ELEMENT,
          SEARCH_EMPTY_RESULT_ELEMENT,
          SEARCH_TITLE_DESCRIPTION_ELEMENT,
          SEARCH_ARTICLES_RETURNED_ELEMENT;


  //templates methods
  private static String getResultSearchElement(String substring) {
    return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
  }


  private static String getResultSearchElementBy (String title, String description) {

    return SEARCH_TITLE_DESCRIPTION_ELEMENT.replace("{TITLE}",title).replace( "{DESCRIPTION}" , description);
  }



  public void initSearchInput() {
    this.waitForElementAndClick(SEARCH_INIT_ELEMENT, "Cannot find and click search init element",
            10);
    this.waitForElementPresent(SEARCH_INPUT, "cannot find search input after clicking init element", 15);
  }

  public void typeSearchLine(String search_line) {

    this.waitForElementAndSendKeys(SEARCH_INPUT, search_line, "Cannot find any type into search input ", 15);


  }

  public void waitForSearchResult(String substring) {

    String search_result_xpath = getResultSearchElement(substring);
    this.waitForElementPresent(search_result_xpath, "Cannot find searched result with substring " + substring, 35);
  }

  public void waitForElementByTitleAndDescription(String title, String description) {
    String search_title_desc_xpath = getResultSearchElementBy(title, description);
    this.waitForElementPresent(search_title_desc_xpath , "Cannot find and click searched result with title " + title + "and description  " + description, 25);
  }



  public void clickByArticleWithSubstring(String substring) {

    String search_result_xpath = getResultSearchElement(substring);
    this.waitForElementAndClick(search_result_xpath, "Cannot find and click searched result with substring " + substring, 5);
  }

  public void waitForCancelButtonToAppear() {
    this.waitForElementPresent(SEARCH_CANCEL_BUTTON, "Cannot find cancel button", 15);

  }

  public void waitForCancelButtonToDisappear() {
    this.waitForElementNotPresent(SEARCH_CANCEL_BUTTON, "Cancel button is still present ", 5);

  }

  public void clickCancelSearch() {
    this.waitForElementAndClick(SEARCH_CANCEL_BUTTON, "Cannot find and click search cancel button ", 5);
  }


  public int getAmountOfFoundArticles() {

    this.waitForElementPresent(
           SEARCH_RESULT_ELEMENT,
            "Cannot find anything by request ",
            25);
    return this.getAmmountOfElements(SEARCH_RESULT_ELEMENT);

  }


  public void waitForEmptyResultsLabel() {

    this.waitForElementPresent(
           SEARCH_EMPTY_RESULT_ELEMENT,
            "cannot find empty result label by the request ",
            15
    );


  }

  public void assertThereIsNoResultOfSearch() {

    this.assertElementNotPresent(SEARCH_RESULT_ELEMENT,
            "We found some results by request");
  }

  public void assertTheSearchArticleResultMoreThen(int count) {

    int actual_number_of_articles_returned = this.getAmmountOfElements(SEARCH_ARTICLES_RETURNED_ELEMENT);
    assertTrue("less than " + count + " articles found",actual_number_of_articles_returned >= count == true);



  }




}