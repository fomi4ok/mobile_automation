package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;


abstract public class MyListPageObject extends MainPageObject {

  public MyListPageObject(AppiumDriver driver) {
    super(driver);
  }

  protected static String
  FOLDER_BY_NAME_TPL,
  ARTICLE_TITLE_TPL;


 private static String getFolderXpathByName(String name_of_the_folder) {
   return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}",name_of_the_folder);
 }

  private static String getTitleXpathByName(String article_title) {
    return ARTICLE_TITLE_TPL.replace("{TITLE}",article_title);
  }



  public void openFolderByName(String name_of_the_folder) {

    String folder_name_xpath = getFolderXpathByName(name_of_the_folder);
    this.waitForElementAndClick(
            (folder_name_xpath),
            "cannot find folder by name " + name_of_the_folder,
            15);
  }





  public void swipeByArticleToDelete(String article_title) {



    this.waitForArticleToAppearByTitle(article_title);

    String article_xpath = getTitleXpathByName(article_title);

    this.swipeElementToLeft((article_xpath),
            "Cannot find saved article");

    if (lib.Platform.getInstance().isOS()) {

      this.clickElementToTheRightUpperCorner(article_xpath, "cannot find saved article");

    }
    this.waitForArticleToDisappearByTitle(article_title);



 }



  public void waitForArticleToDisappearByTitle(String article_title) {

    String article_xpath = getTitleXpathByName(article_title);


    this.waitForElementNotPresent((article_xpath),
            "cannot delete saved article " + article_title ,
            15);
  }


  public void waitForArticleToAppearByTitle(String article_title) {

    String article_xpath = getTitleXpathByName(article_title);


    this.waitForElementPresent((article_xpath),
            "cannot find saved article by title " + article_title,
            20);
  }




  public void openArticleFromFolder(String article_title) {

    String article_xpath = getTitleXpathByName(article_title);

   this.waitForElementAndClick((article_xpath),
           "cannot find saved article by title " + article_title,
           20);


  }
}
