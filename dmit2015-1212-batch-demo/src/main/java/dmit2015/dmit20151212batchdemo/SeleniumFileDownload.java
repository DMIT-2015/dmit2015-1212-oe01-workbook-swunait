package dmit2015.dmit20151212batchdemo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class SeleniumFileDownload {

    public static void main(String[] args) {
        System.setProperty("webdriver.gecko.driver", "/home/user2015/jdk/web-drivers/geckodriver");
        FirefoxOptions profile = new FirefoxOptions();
        profile.addPreference("browser.download.folderList",2);
        profile.addPreference("browser.download.dir","/home/user2015/Downloads");
        profile.addPreference("browser.helperApps.neverAsk.openFile", "text/csv");
        profile.addPreference("browser.helperApps.neverAsk.saveToDisk", "text/csv");
        WebDriver driver = new FirefoxDriver(profile);
        driver.get("https://data.edmonton.ca/Transportation/Scheduled-Photo-Enforcement-Zone-Centre-Points/akzz-54k3");	// Replace urlForWebSite with the URL that contains the link you need to click on to need to click to download the file
        driver.manage().window().maximize();
        WebElement downloadButton = driver.findElement(By.className("download"));
        downloadButton.click();
        WebElement csvButton = driver.findElement(By.className("download-link"));
        csvButton.click();
        driver.quit();
    }
}
