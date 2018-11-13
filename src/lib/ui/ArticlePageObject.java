package lib.ui;


import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class ArticlePageObject extends MainPageObject {

  public ArticlePageObject(AppiumDriver driver) {

    super(driver);

  }

  private static final String
  TITLE = "org.wikipedia:id/view_page_title_text",
  FOOTER_ELEMENT = "//*[@text='View page in browser']",
  OPTIONS_BUTTON = "//android.widget.ImageView[@content-desc='More options']",
  OPTIONS_ADD_TO_MY_LIST_BUTTON = "//*[@text='Add to reading list']",
          ADD_TO_MY_LIST_OVERLAY = "org.wikipedia:id/onboarding_button",
  MY_LIST_NAME_INPUT = "org.wikipedia:id/text_input",
  MY_LIST_OK_BUTTON_INPUT= "//*[@text='OK']",
  CLOSE_ARTICLE_BUTTON = "//android.widget.ImageButton[@content-desc='Navigate up']",
  FOLDER_BY_NAME_TPL = "//android.widget.LinearLayout//*[@text='{FOLDER_NAME}']";


  private static String getFolderXpathByName(String name_of_the_folder) {
    return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}",name_of_the_folder);
  }



  public WebElement waitForTitleElement() {
    return this.waitForElementPresent(By.id(TITLE), "cannot find article title on page", 15);
  }

  public String getArticleTitle() {
    WebElement title_element = waitForTitleElement();
    return title_element.getAttribute("text");
  }

  public void swipeToFooter() {
    this.swipeUpToFindElement(By.xpath(FOOTER_ELEMENT), "Cannot find the end of article", 20);
  }

  public void addArticleToMyList(String name_of_the_folder) {

    this.waitForElementAndClick(
            By.xpath(OPTIONS_BUTTON),
            "cannot find button to open article options",
            15);
    this.waitForElementAndClick(
            By.xpath(OPTIONS_ADD_TO_MY_LIST_BUTTON),
            "cannot find option to add to the reading list",
            5
    );

    this.waitForElementAndClick(
            By.id(ADD_TO_MY_LIST_OVERLAY),
            "cannot find 'got it' tip overlay",
            5);

    this.waitForElementClear(
            By.id(MY_LIST_NAME_INPUT),
            "cannot find input to set name of articles folder",
            5);


    this.waitForElementAndSendKeys(By.id(MY_LIST_NAME_INPUT),
            name_of_the_folder,
            "cannot put text in the article folder input",
            5);
    this.waitForElementAndClick(
            By.xpath(MY_LIST_OK_BUTTON_INPUT),
            "Cannot press OK button",
            5);

  }

  public void addArticleToExistingList(String name_of_the_folder) {

    this.waitForElementAndClick(
            By.xpath(OPTIONS_BUTTON),
            "cannot find button to open article options",
            15);
    this.waitForElementAndClick(
            By.xpath(OPTIONS_ADD_TO_MY_LIST_BUTTON),
            "cannot find option to add to the reading list",
            5
    );
    this.selectExistingFolder(name_of_the_folder);
  }



    public void selectExistingFolder(String name_of_the_folder) {
    String folder_name_xpath = getFolderXpathByName(name_of_the_folder);
    this.waitForElementAndClick(
            By.xpath(folder_name_xpath),
            "cannot find folder by name " + name_of_the_folder,
            15);

  }

  public void closeArticle() {
    this.waitForElementAndClick(
            By.xpath(CLOSE_ARTICLE_BUTTON),
            "cannot close article, cannot find X link",
            5);
  }

  public void assertThereIsATitle() {
    this.assertElementPresent(By.id(TITLE),
            "We didnt' find any results by request ");


  }



}
