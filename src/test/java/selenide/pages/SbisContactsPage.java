package selenide.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class SbisContactsPage {
    private final SelenideElement TENZOR_BANNER =
            $x("(//div[contains(@class, 'sbisru-Contacts__border-left')])[1]");
    private final SelenideElement TENZOR_REFERENCE =
            $x("(//a[@href='https://tensor.ru/'])[1]");
    private final SelenideElement regionDefault =
            $x("//span[@class='sbis_ru-Region-Chooser__text sbis_ru-link']");
    private final SelenideElement regionKamcatskiy = $x("//span[text()='Камчатский край']");
    private final SelenideElement contactsList = $x("//div[@id='contacts_list']");
    private final SelenideElement cityPartner =
            $x("//div[@id='contacts_list']" +
                    "//div[@class='sbisru-Contacts-City__item-name sbisru-link pr-4 pr-xm-8 sbisru-text-main']");
    private final SelenideElement chooseKamchatskiy = $x("//span[text()='41 Камчатский край']");

    @Step("Проверяем видимость баннера Тензор")
    public boolean isTenzorBanner(){
        return TENZOR_BANNER.isDisplayed();
    }

    @Step("Нажимаем на баннер Тензор")
    public TenzorMainPage clickTenzorReference(){
        TENZOR_REFERENCE.click();
        return new TenzorMainPage();
    }
    @Step("Возвращаем текст региона(домашний)")
    public String getDefaultRegion(){
        return regionDefault.getText();
    }

    @Step("По клику на регион выбираем из списка Камчатский край")
    public SbisContactsPage changeRegion(){
        regionDefault.click();
        chooseKamchatskiy
                .shouldBe(visible)
                .click();
        return this;
    }

    @Step("Возвращаем текст региона")
    public String getKamchatskiyRegion(){
        return regionKamcatskiy
                .shouldBe(visible)
                .getText();
    }
    @Step("Получаем текст элемента - город места расположения партнера")
    public String getCityPartner(){
        return cityPartner
                .shouldBe(visible)
                .getText();
    }

    @Step("Проверяем видимость блока - список контактов партнеров")
    public boolean isContactsListExists(){
        return contactsList
                .scrollTo()
                .shouldBe(visible)
                .isDisplayed();
    }

}
