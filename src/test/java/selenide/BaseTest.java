package selenide;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import java.io.File;


@Slf4j
public class BaseTest {

//    public static WebDriver decorated;
//    public static ChromeOptions options;

    @BeforeAll
    static void setUp(){
//        WebDriverManager.chromedriver().setup();
//        options = new ChromeOptions();
//        String version = "latest";
//        options.setBrowserVersion(version);
//        options.addArguments("start-maximized");
//        options.addArguments("enable-automation");
//        options.addArguments("--headless=old");
//        options.addArguments("--no-sandbox");
//        options.addArguments("--disable-dev-shm-usage");
//        options.addArguments("--window-size=1920,1080");
//        options.addArguments("--disable-browser-side-navigation");
//        options.addArguments("--disable-extensions");
//        options.addArguments("--dns-prefetch-disable");
//        options.addArguments("--disable-gpu");
//        WebDriver original = new ChromeDriver(options);
//        options.setPageLoadStrategy(PageLoadStrategy.NONE);
//        Listener listener = new Listener();
//        EventFiringDecorator<WebDriver> decorator =
//                new EventFiringDecorator<>(listener);
//        decorated = decorator.decorate(original);

        Selenide.closeWebDriver();
        Configuration.timeout = 8000;
        Configuration.browserSize = "1920x1080"; //  default = "1366x768
//        Configuration.proxyEnabled = true;
//        Configuration.proxyHost = "0.0.0.0";
//        Configuration.fileDownload = PROXY;
//        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
//                .screenshots(true).savePageSource(false));
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }

    @AfterAll
    static void tearDown(){
        File directory = new File("build/downloads");
        cleanDirectory(directory);

//        decorated.quit();
    }

    public static void cleanDirectory(File directory) {
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();

            if (files != null && files.length > 0) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        cleanDirectory(file);
                    }
                    if (file.delete()) {
                        log.info("Удаленo: {}", file.getAbsolutePath());
                    } else {
                       log.info("Не удалось удалить: {}", file.getAbsolutePath());
                    }
                }
            } else {
                log.info("Папка пуста: {}", directory.getAbsolutePath());
            }
        } else {
            log.info("Указанный путь не является директорией.");
        }
    }

}
