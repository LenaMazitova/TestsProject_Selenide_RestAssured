package selenide;

import com.codeborne.selenide.WebDriverRunner;
import selenide.pages.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static com.codeborne.selenide.Selenide.switchTo;
import static org.junit.jupiter.api.Assertions.*;

class SelenideTests extends BaseTest {

    @Test
    public void scenario1() {
        SbisPage sbisPage = new SbisPage();
        SbisContactsPage sbisContactsPage = sbisPage
                .openContactsWindow()
                .openContactsPage();
        assertTrue(sbisContactsPage.isTenzorBanner());

        TenzorMainPage tenzorMainPage = sbisContactsPage.clickTenzorReference();
        switchTo().window(1);

        assertTrue(tenzorMainPage.isBlogSilaDisplayed());

        TenzorAboutPage tenzorAboutPage = tenzorMainPage.goReferencePodrobnee();
        String currentUrl = WebDriverRunner.url();
        assertEquals("https://tensor.ru/about", currentUrl);

        List<List<String>> values = tenzorAboutPage.getFotoValues();
        Boolean isWidthEqual = values.stream()
                .findFirst() // Получаем первый список (может быть пусто)
                .map(firstList -> firstList.stream().distinct().count() == 1)
                .orElse(false);// Если списков нет, возвращаем false
        assertTrue(isWidthEqual);

        Boolean isHeightEqual = values.stream()
                .skip(1)
                .findFirst() // Получаем первый список (может быть пусто)
                .map(firstList -> firstList.stream().distinct().count() == 1)
                .orElse(false);// Если списков нет, возвращаем false
        assertTrue(isHeightEqual);
    }

    @Test
    public void scenario2() {
        SbisPage sbisPage = new SbisPage();
        SbisContactsPage sbisContactsPage = sbisPage
                .openContactsWindow()
                .openContactsPage();
        String region = sbisContactsPage.getDefaultRegion();
        assertEquals("Калининградская обл.", region);
        assertTrue(sbisContactsPage.isContactsListExists());

        String firstHomeCityPartner = sbisContactsPage.getCityPartner();

        String kamchatskiyRegion = sbisContactsPage.changeRegion().getKamchatskiyRegion();
        assertEquals("Камчатский край", kamchatskiyRegion);

        String firstKamchatskiyCityPartner = sbisContactsPage.getCityPartner();
        assertNotEquals(firstHomeCityPartner, firstKamchatskiyCityPartner);

    }

    @Test
    public void scenario3() {
        SbisPage sbisPage = new SbisPage();
        DownloadPage downloadPage = sbisPage.downloadPlugin();
        File file = downloadPage.downloadWebInstaller();
        assertEquals("sbisplugin-setup-web.exe", file.getName());

        Double expectedFileLength = downloadPage.getFileLengthMegaBytes();
        Double actualFileLength = bytesToMegabytes(file.length());
        assertEquals(expectedFileLength, actualFileLength);
    }

    public double bytesToMegabytes(long bytes) {
        BigDecimal megabytes = BigDecimal.valueOf(bytes)
                .divide(BigDecimal.valueOf(1024 * 1024), 2, RoundingMode.HALF_UP);
        return megabytes.doubleValue();
    }
}
