package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class WelcomePageObject extends MainPageObject {

  public WelcomePageObject(AppiumDriver driver) {

    super(driver);
  }

  private static final String
          STEP_MORE_LINK = "id:Learn more about Wikipedia",
          STEP_NEW_WAYS_TO_EXPLORE_TEXT="id:New ways to explore",
  STEP_ADD_OR_EDIT_PREFERRED_LANG_LINK = "id:Add or edit preferred languages",
  STEP_LEARN_MORE_ABOUT_DATA_COLLEVTED_LINK = "id:Learn more about data collected",
          NEXT_LINK = "id:Next",
          GET_STARTED_BUTTON = "id:Get started",
  SKIP = "id:Skip";



  public void waitForLearnMoreLink() {

    this.waitForElementPresent(STEP_MORE_LINK, "Cannot find learn more link", 10);
  }

  public void waitForNewWayToExplore() {

    this.waitForElementPresent(STEP_NEW_WAYS_TO_EXPLORE_TEXT, "Cannot find New ways to explore link", 10);
  }

  public void waitForAddOrEditPreferredLangText() {

    this.waitForElementPresent(STEP_ADD_OR_EDIT_PREFERRED_LANG_LINK, "Cannot find 'Add or edit preferred languages' link", 10);
  }
  public void waitForLearnMoreAboutdataCollectedText() {

    this.waitForElementPresent(STEP_LEARN_MORE_ABOUT_DATA_COLLEVTED_LINK, "Cannot find 'Learn more about data collected' link", 10);
  }

  public void clickGetStartedButton() {

    this.waitForElementAndClick(GET_STARTED_BUTTON, "Cannot find 'Get started' link", 10);
  }

  public void clickNextButton() {

    this.waitForElementAndClick(NEXT_LINK, "Cannot find 'Next' button", 10);
  }

  public void clickSkip() {
    this.waitForElementAndClick(SKIP, "cannot find 'skip' button", 10);

  }


}
