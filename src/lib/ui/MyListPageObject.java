package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class MyListPageObject extends MainPageObject {

  public MyListPageObject(AppiumDriver driver) {
    super(driver);
  }

  private static final String
  FOLDER_BY_NAME_TPL = "//*[@text='{FOLDER_NAME}']",
  ARTICLE_TITLE_TPL = "//*[@text='{TITLE}']";


 private static String getFolderXpathByName(String name_of_the_folder) {
   return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}",name_of_the_folder);
 }

  private static String getTitleXpathByName(String article_title) {
    return ARTICLE_TITLE_TPL.replace("{TITLE}",article_title);
  }



  public void openFolderByName(String name_of_the_folder) {

    String folder_name_xpath = getFolderXpathByName(name_of_the_folder);
    this.waitForElementAndClick(
            By.xpath(folder_name_xpath),
            "cannot find folder by name " + name_of_the_folder,
            15);
  }





  public void swipeByArticleToDelete(String article_title) {

    this.waitForArticleToAppearByTitle(article_title);

    String article_xpath = getTitleXpathByName(article_title);

    this.swipeElementToLeft(By.xpath(article_xpath),
            "Cannot find saved article");
    this.waitForArticleToDisappearByTitle(article_title);


  }



  public void waitForArticleToDisappearByTitle(String article_title) {

    String article_xpath = getTitleXpathByName(article_title);


    this.waitForElementNotPresent(By.xpath(article_xpath),
            "cannot delete saved article " + article_title ,
            15);
  }


  public void waitForArticleToAppearByTitle(String article_title) {

    String article_xpath = getTitleXpathByName(article_title);


    this.waitForElementPresent(By.xpath(article_xpath),
            "cannot find saved article by title " + article_title,
            20);
  }




  public void openArticleFromFolder(String article_title) {

    String article_xpath = getTitleXpathByName(article_title);

   this.waitForElementAndClick(By.xpath(article_xpath),
           "cannot find saved article by title " + article_title,
           20);


  }
}