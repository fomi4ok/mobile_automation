package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import junit.framework.TestCase;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class CoreTestCase extends TestCase{

  private static final String PLATFORM_IOS = "iOS",
          PLATFORM_ANDROID = "android";


  protected AppiumDriver driver;
  private static String AppiumURL = "http://127.0.0.1:4723/wd/hub";
  @Override

  protected void setUp() throws Exception{

    super.setUp();

    DesiredCapabilities capabilities = this.getCapabilitiesByPlatformEnv();
    this.getdriver();
    this.rotateScreenPortrait();

  }


  @Override
  protected void tearDown() throws Exception{
    driver.quit();
    super.tearDown();
  }

  protected void rotateScreenPortrait() {

    driver.rotate(ScreenOrientation.PORTRAIT);

  }
  protected void rotateScreenLandscape() {

    driver.rotate(ScreenOrientation.LANDSCAPE);

  }

  protected void backgroundApp(int seconds) {

    driver.runAppInBackground(2);
  }

  private DesiredCapabilities getCapabilitiesByPlatformEnv() throws Exception {

    String platform = System.getenv("platform");
    DesiredCapabilities capabilities = new DesiredCapabilities();

    if(platform.equals(PLATFORM_ANDROID)) {
      capabilities.setCapability("platformName", "Android");
      capabilities.setCapability("deviceName", "AndroidTestDevice");
      capabilities.setCapability("platformVersion", "6.0");
      capabilities.setCapability("automationName", "Appium");
      capabilities.setCapability("appPackage", "org.wikipedia");
      capabilities.setCapability("appActivity", ".main.MainActivity");
      capabilities.setCapability("app", "/Users/mfomicheva/Documents/GitHub/mobile_automation/apks/org.wikipedia.apk");

    } else if (platform.equals(PLATFORM_IOS)) {
      capabilities.setCapability("platformName", "iOS");
      capabilities.setCapability("deviceName", "iPhone SE");
      capabilities.setCapability("platformVersion", "11.4");
      capabilities.setCapability("app", "/Users/mfomicheva/Documents/GitHub/mobile_automation/apks/Wikipedia.app");

    }else {
      throw new Exception("Cannot get run platform from env variable. Platform value " + platform);
    }

    return capabilities;

}

   private AppiumDriver getdriver() throws Exception{

     String platform = System.getenv("platform");
     if(platform.equals(PLATFORM_ANDROID)) {
       driver = new AndroidDriver(new URL(AppiumURL), this.getCapabilitiesByPlatformEnv());

   } else if (platform.equals(PLATFORM_IOS)) {
       driver = new IOSDriver(new URL(AppiumURL), this.getCapabilitiesByPlatformEnv());

     }

     return driver;
   }
}
