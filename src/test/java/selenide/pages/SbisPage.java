package selenide.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

/**
 * Главная страница сайта sbis.ru
 */
public class SbisPage {
    private final String BASE_URL = "https://sbis.ru/";
    private final SelenideElement searchContacts = $x("");
    private final SelenideElement contacts = $x("//div[text()='Контакты']");
    private final SelenideElement contactsHref = $x("//a[@href='/contacts']/span");
    private final SelenideElement downloadLocalVersions = $x("//a[@href='/download']");

    public SbisPage(){
        Selenide.open(BASE_URL);
    }
    public SbisPage openContactsWindow(){
        contacts.click();
        return this;
    }
    public SbisContactsPage openContactsPage(){
        contactsHref.click();
        return new SbisContactsPage();
    }

    public DownloadPage downloadPlugin(){
        downloadLocalVersions
                .scrollTo()
                .shouldBe(visible)
                .click();
        return new DownloadPage();

    }

}
