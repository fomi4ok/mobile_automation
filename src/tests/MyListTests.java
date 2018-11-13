package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MyListPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class MyListTests extends CoreTestCase{

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
    assertEquals("Title doesn't match", second_article_title, title_of_the_article_saved);

  }
}
