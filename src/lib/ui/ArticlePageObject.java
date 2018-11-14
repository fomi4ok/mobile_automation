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
  TITLE = "id:org.wikipedia:id/view_page_title_text",
  FOOTER_ELEMENT = "xpath://*[@text='View page in browser']",
  OPTIONS_BUTTON = "xpath://android.widget.ImageView[@content-desc='More options']",
  OPTIONS_ADD_TO_MY_LIST_BUTTON = "xpath://*[@text='Add to reading list']",
          ADD_TO_MY_LIST_OVERLAY = "id:org.wikipedia:id/onboarding_button",
  MY_LIST_NAME_INPUT = "id:org.wikipedia:id/text_input",
  MY_LIST_OK_BUTTON_INPUT= "xpath://*[@text='OK']",
  CLOSE_ARTICLE_BUTTON = "xpath://android.widget.ImageButton[@content-desc='Navigate up']",
  FOLDER_BY_NAME_TPL = "xpath://android.widget.LinearLayout//*[@text='{FOLDER_NAME}']";


  private static String getFolderXpathByName(String name_of_the_folder) {
    return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}",name_of_the_folder);
  }



  public WebElement waitForTitleElement() {
    return this.waitForElementPresent(TITLE, "cannot find article title on page", 15);
  }

  public String getArticleTitle() {
    WebElement title_element = waitForTitleElement();
    return title_element.getAttribute("text");
  }

  public void swipeToFooter() {
    this.swipeUpToFindElement(FOOTER_ELEMENT, "Cannot find the end of article", 20);
  }

  public void addArticleToMyList(String name_of_the_folder) {

    this.waitForElementAndClick
            (OPTIONS_BUTTON,
            "cannot find button to open article options",
            15);
    this.waitForElementAndClick(
            OPTIONS_ADD_TO_MY_LIST_BUTTON,
            "cannot find option to add to the reading list",
            5
    );

    this.waitForElementAndClick(
            ADD_TO_MY_LIST_OVERLAY,
            "cannot find 'got it' tip overlay",
            5);

    this.waitForElementClear(MY_LIST_NAME_INPUT,
            "cannot find input to set name of articles folder",
            5);


    this.waitForElementAndSendKeys(MY_LIST_NAME_INPUT,
            name_of_the_folder,
            "cannot put text in the article folder input",
            5);
    this.waitForElementAndClick(
            MY_LIST_OK_BUTTON_INPUT,
            "Cannot press OK button",
            5);

  }

  public void addArticleToExistingList(String name_of_the_folder) {

    this.waitForElementAndClick
            (OPTIONS_BUTTON,
            "cannot find button to open article options",
            15);
    this.waitForElementAndClick(
            OPTIONS_ADD_TO_MY_LIST_BUTTON,
            "cannot find option to add to the reading list",
            5
    );
    this.selectExistingFolder(name_of_the_folder);
  }



    public void selectExistingFolder(String name_of_the_folder) {
    String folder_name_xpath = getFolderXpathByName(name_of_the_folder);
    this.waitForElementAndClick(
            (folder_name_xpath),
            "cannot find folder by name " + name_of_the_folder,
            15);

  }

  public void closeArticle() {
    this.waitForElementAndClick(
            CLOSE_ARTICLE_BUTTON,
            "cannot close article, cannot find X link",
            5);
  }

  public void assertThereIsATitle() {
    this.assertElementPresent(TITLE,
            "We didnt' find any results by request ");


  }



}
