package com.github.ubaifadhli.util;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DriverFactory {
    public static AppiumDriver createMobileDriver() {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

        Map<String, Object> capabilityMap = Stream.of(
                new AbstractMap.SimpleImmutableEntry<>("platformName", "Android"),
                new AbstractMap.SimpleImmutableEntry<>("udid", "emulator-5554"),
                new AbstractMap.SimpleImmutableEntry<>("appPackage", "com.medium.reader"),
                new AbstractMap.SimpleImmutableEntry<>("appActivity", "com.medium.android.donkey.start.SplashActivity"),
                new AbstractMap.SimpleImmutableEntry<>("noReset", true)
        ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        capabilityMap.forEach(desiredCapabilities::setCapability);

        try {
            return new AndroidDriver(URI.create("http://127.0.0.1:4723/wd/hub").toURL(), desiredCapabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();

            return null;
        }
    }

    public static WebDriver createWebDriver(DriverManagerType type) {
        WebDriverManager.getInstance(type).setup();

        ChromeOptions chromeOptions = new ChromeOptions();
        String optionArgument = String.format("--user-data-dir=%s", PropertiesReader.getFolderAbsolutePath("browser-session/chrome-cache-dir"));
        chromeOptions.addArguments(optionArgument);

        return new ChromeDriver(chromeOptions);
    }
}
