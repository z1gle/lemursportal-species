package org.wcs.lemurs.util;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScreenShot {

    public static final Logger LOGGER = LoggerFactory.getLogger(ScreenShot.class);

  public static void main(final String[] args) throws Exception {
    final String link = "https://www.lemursportal.org/";
    final File screenShot = new File("C:\\Users\\WCS\\Documents\\LinkPreviewTest\\screenshot.png").getAbsoluteFile();

    ScreenShot.LOGGER.debug("Creating Firefox Driver");
    final WebDriver driver = new FirefoxDriver();
    try {
      ScreenShot.LOGGER.debug("Opening page: {}", link);
      driver.get(link);

      ScreenShot.LOGGER.debug("Wait a bit for the page to render");
      TimeUnit.SECONDS.sleep(5);

      ScreenShot.LOGGER.debug("Taking Screenshot");
      final File outputFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
      FileUtils.copyFile(outputFile, screenShot);
      ScreenShot.LOGGER.debug("Screenshot saved: {}", screenShot);
    } finally {
      driver.close();
    }

    ScreenShot.LOGGER.debug("done");
  }

}
