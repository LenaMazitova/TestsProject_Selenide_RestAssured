package selenide.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

/**
 * Главная страница сайта sbis.ru
 */
public class SbisPage {
    private final String BASE_URL = "https://sbis.ru/";
    private final SelenideElement contacts = $x("//div[text()='Контакты']");
    private final SelenideElement contactsHref = $x("//a[@href='/contacts']/span");
    private final SelenideElement downloadLocalVersions = $x("//a[@href='/download']");
    private final SelenideElement dialog =
            $x("//span[contains(@class, 'Chat clientWidget-Button__icon_inverted')]");
    private final ElementsCollection dialogFormFields = $$x("//input[@name='ws-input_2024-11-07']");
    private final SelenideElement tick = $x("//*[@x='3' and @width='6']");
    private final SelenideElement fieldTextInput =
            $x("//textarea[contains(@class, 'Field controls-InputBase')]");


    public SbisPage(){
        Selenide.open(BASE_URL);
    }

    @Step("Нажимаем на Контакты на главной странице")
    public SbisPage openContactsWindow(){
        contacts.click();
        return this;
    }

    @Step("Нажимаем на ссылку Контакты в открывшемся окне")
    public SbisContactsPage openContactsPage(){
        contactsHref.click();
        return new SbisContactsPage();
    }

    @Step("Находим в футере Скачать локальные версиии и кликаем")
    public DownloadPage downloadPlugin(){
        downloadLocalVersions
                .scrollTo()
                .shouldBe(visible)
                .click();
        return new DownloadPage();
    }

    @Step("Открываем форму для диалога (нажимаем на значок сообщений на странице)")
    public void openDialogForm() {
        dialog.click();
        switchTo().frame("chat/e66a482c-d37d-4b2e-b3b4-3294218800c1-e66a482c-d37d-4b2e-b3b4-3294218800c1-frame");

    }

    @Step("В форму для диалога вводим имя: {name}")
    public void enterName(String name) {
        dialogFormFields.get(0)
                .shouldBe(visible)
                .shouldBe(enabled)
                .click();
        dialogFormFields.get(0)
                .setValue(name);
    }

    @Step("В форму для диалога вводим телефон: {phone}")
    public void enterPhone(String phone) {
        dialogFormFields.get(1)
                .shouldBe(visible)
                .shouldBe(enabled)
                .click();
        dialogFormFields.get(1)
                .setValue(phone);
    }

    @Step("В форму для диалога вводим название компании: {companyName}")
    public void enterCompanyName(String companyName) {
        dialogFormFields.get(2)
                .shouldBe(visible)
                .shouldBe(enabled)
                .click();
        dialogFormFields.get(2)
                .setValue(companyName);
    }

    @Step("Нажимаем галочку для согласия на обработку введенных данных")
    public void tickAgreement() {
        tick.scrollTo()
                .shouldBe(visible)
                .shouldBe(enabled)
                .click();
    }

    @Step("Вводим текст сообщения {text} и отправляем")
    public void sendMessage(String text) {
        fieldTextInput.click();
        fieldTextInput.setValue("Здравствуйте");
//                .pressEnter();
    }

    @Step("Заполняем форму для диалога: {name}, phone: {phone}, companyName: {companyName}")
    public void performDialog(String name, String phone, String companyName, String text) {
        openDialogForm();
        enterName(name);
        enterPhone(phone);
        enterCompanyName(companyName);
        tickAgreement();
        sendMessage(text);
//        switchTo().defaultContent();
    }

}
