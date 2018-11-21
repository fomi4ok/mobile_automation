package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.SearchPageObject;

public class iOSSearchPageObject extends SearchPageObject {

  public iOSSearchPageObject(AppiumDriver driver) {
    super(driver);
  }

  static {

    SEARCH_INIT_ELEMENT = "xpath://XCUIElementTypeSearchField[@name='Search Wikipedia']";
    SEARCH_INPUT = "xpath://XCUIElementTypeSearchField[@value='Search Wikipedia']";
    SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://XCUIElementTypeLink[contains(@name,'{SUBSTRING}')]";
    SEARCH_CANCEL_BUTTON = "id:Close";
    SEARCH_RESULT_ELEMENT = "xpath://XCUIElementTypeLink";
    SEARCH_EMPTY_RESULT_ELEMENT = "xpath://XCUIElementTypeStaticText[@name='No results found']";
    SEARCH_TITLE_DESCRIPTION_ELEMENT = "xpath://XCUIElementTypeLink[contains(@name,'{TITLE}')][contains(@name,'{DESCRIPTION}')]";
    SEARCH_ARTICLES_RETURNED_ELEMENT = "xpath://XCUIElementTypeLink";

  }
}
