package selenide;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {

    @BeforeAll
    static void setUp(){
        Configuration.timeout = 8000;
//        default = "1366x768"
        Configuration.browserSize = "1920x1080";
    }

    @AfterAll
    static void tearDown(){
        Selenide.closeWebDriver();
    }

}
