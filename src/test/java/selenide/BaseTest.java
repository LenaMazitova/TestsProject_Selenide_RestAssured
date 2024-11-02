package selenide;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.FileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.env.Environment;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseTest {

//    public static final File RECORDINGS_DIR = new File("target/selenide");

    // Проверяем, доступен ли Docker
    public static boolean isDockerAvailable() {
        try {
            return Runtime.getRuntime().exec("docker info").waitFor() == 0;
        } catch (Exception e) {
            return false;
        }
    }

    @Container
    public static BrowserWebDriverContainer<?> webDriverContainer =
            new BrowserWebDriverContainer<>(
                    System.getProperty("os.arch").equals("aarch64")
                            ? DockerImageName.parse("seleniarm/standalone-chromium")
                            .asCompatibleSubstituteFor("selenium/standalone-chrome")
                            : DockerImageName.parse("selenium/standalone-chrome"))
                    .withCapabilities(
                            new ChromeOptions()
                                    .addArguments("--no-sandbox")
                                    .addArguments("--disable-dev-shm-usage")
                                    .addArguments("download.default_directory=/home/seluser/Downloads")
                                    .addArguments("download.prompt_for_download=false")
                                    .addArguments("safebrowsing.enabled")
                    );
//                    .withRecordingMode(BrowserWebDriverContainer.VncRecordingMode.RECORD_FAILING,
//                            RECORDINGS_DIR);

    @AfterAll
    static void tearDown() {
        if (webDriverContainer != null) {
            webDriverContainer.stop();
        }
//        Selenide.closeWebDriver();
    }
    @BeforeAll
    static void beforeAll() {
        webDriverContainer.start();
        RemoteWebDriver remoteWebDriver = webDriverContainer.getWebDriver();
        WebDriverRunner.setWebDriver(remoteWebDriver);

//        Configuration.downloadsFolder = "/home/seluser/Downloads";

//        Configuration.reportsFolder = "target/selenide";

        Configuration.timeout = 8000;
        Configuration.browserSize = "1920x1080";  // default = "1366x768"
    }


}
