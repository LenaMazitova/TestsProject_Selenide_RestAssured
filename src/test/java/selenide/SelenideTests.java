package selenide;

import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.junit5.TextReportExtension;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import selenide.pages.*;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static com.codeborne.selenide.Selenide.switchTo;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(TextReportExtension.class) // Для табличных консольных логов
@Feature("UI-тестирование")
@Epic("User-stories на сайтах sbis.ru и tenzor.ru")
class SelenideTests extends BaseTest {

    @Issue(value = "FAT-128627")
    @Link(name = "https://sbis.ru/", url = "https://sbis.ru/")
    @Link(name = "https://tensor.ru/", url = "https://tensor.ru/")
    @Owner(value = "Lena Mazitova")
    @DisplayName("""
            Проверка перехода с сайта sbis.ru на сайт tensor.ru, проверка отображения баннера,
             блока 'Сила в людях', перехода по ссылке 'Подробнее', проверка фотографий
            """)
    @Description("""
            На главной странице sbis.ru нажимаем 'Контакты', проверяем видимость баннера,
            нажимаем на него (переход на сайт tensor.ru), проверяем наличие баннера 'Сила в людях',
            нажимаем на 'Подробнее' (переход на другую страницу), проверяем одинаковый размер фотографий в блоке
            """)
    @Story("Позитивные")
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

    @Issue(value = "FAT-128627")
    @Link(name = "https://sbis.ru/", url = "https://sbis.ru/")
    @Owner(value = "Lena Mazitova")
    @DisplayName("Проверка автогеолокации и возможности смены региона")
    @Description("""
            На главной странице sbis.ru нажимаем 'Контакты', проверяем видимость отображения домашнего региона, 
            через выпадающий список меняем на Камчатский регион, проверяем отображение на странице региона 
            и другого списка партнеров
            """)
    @Story("Позитивные")
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

    @Issue(value = "FAT-128627")
    @Link(name = "https://sbis.ru/", url = "https://sbis.ru/")
    @Owner(value = "Lena Mazitova")
    @DisplayName("Проверка корректного скачивания вэб-плагина")
    @Description("""
            На главной странице нажимаем 'Скачать локальные версии', напротив надписи 'Веб-установщик' 
            нажимаем 'Скачать (Exe 11.48 МБ)', проверяем, что скачанный файл имеет указанный на сайте размер
            """)
    @Story("Позитивные")
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

    @Issue(value = "FAT-128627")
    @Link(name = "https://sbis.ru/", url = "https://sbis.ru/")
    @Owner(value = "Lena Mazitova")
    @DisplayName("Проверка отправки сообщения в диалог")
    @Description("""
            На главной странице нажимаем на иконку сообщений, заполняем все поля валидными значениями 
            (имя, телефон, название компании), отмечаем согласие на обработку данных, вводим текст
            сообщения и нажимаем Enter
            """)
    @Story("Позитивные")
    @Test
    public void scenario4() {
        SbisPage sbisPage = new SbisPage();
        sbisPage.performDialog("Алиса", "9998889988", "Яндекс", "Здравствуйте!");
    }
}
