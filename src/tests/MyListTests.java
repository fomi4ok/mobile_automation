package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.MyListPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class MyListTests extends CoreTestCase{

  private String name_of_the_folder = "Learning programming";


  @Test
  public void testSaveFirstArticleToMyList() {

    SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
    SearchPageObject.initSearchInput();
    SearchPageObject.typeSearchLine("Java");
    SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

    ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
    ArticlePageObject.waitForTitleElement();
    String article_title = ArticlePageObject.getArticleTitle();

    if(Platform.getInstance().isAndroid()) {
    ArticlePageObject.addArticleToMyList(name_of_the_folder);
    }
    else {
      ArticlePageObject.addArticleToMySaved();
      ArticlePageObject.closeThePopUp();

    }
    ArticlePageObject.closeArticle();

    NavigationUI NavigationUI = NavigationUIFactory.get(driver);
    NavigationUI.clickMyList();

    MyListPageObject MyListPageObject = MyListPageObjectFactory.get(driver);
    if (Platform.getInstance().isAndroid()) {
      MyListPageObject.openFolderByName(name_of_the_folder);
      MyListPageObject.openFolderByName(name_of_the_folder);
    }
    MyListPageObject.swipeByArticleToDelete(article_title);



  }
  /// ex 5
  @Test
  public void testSaveTwoArticleToMyList() {

    SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
    SearchPageObject.initSearchInput();
    SearchPageObject.typeSearchLine("Java");
    SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

    ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
    ArticlePageObject.waitForTitleElement();
    String article_title = ArticlePageObject.getArticleTitle();
    ArticlePageObject.addArticleToMyList(name_of_the_folder);
    ArticlePageObject.closeArticle();
    SearchPageObject.initSearchInput();
    SearchPageObject.typeSearchLine("Java");
    SearchPageObject.clickByArticleWithSubstring("Programming language");
    ArticlePageObject.waitForTitleElement();
    String second_article_title = ArticlePageObject.getArticleTitle();
    ArticlePageObject.addArticleToExistingList(name_of_the_folder);
    ArticlePageObject.closeArticle();
    NavigationUI NavigationUI = NavigationUIFactory.get(driver);
    NavigationUI.clickMyList();


    MyListPageObject MyListPageObject = MyListPageObjectFactory.get(driver);
    MyListPageObject.openFolderByName(name_of_the_folder);
    MyListPageObject.openFolderByName(name_of_the_folder);
    MyListPageObject.swipeByArticleToDelete(article_title);
    MyListPageObject.openArticleFromFolder(second_article_title);
    String title_of_the_article_saved =  ArticlePageObject.getArticleTitle();
    assertEquals("Title doesn't match", second_article_title, title_of_the_article_saved);

  }
}
