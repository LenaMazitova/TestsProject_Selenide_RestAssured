package selenide.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class SbisContactsPage {
    private final SelenideElement TENZOR_BANNER =
            $x("(//div[contains(@class, 'sbisru-Contacts__border-left')])[1]");
    private final SelenideElement TENZOR_REFERENCE =
            $x("(//a[@href='https://tensor.ru/'])[1]");
    private final SelenideElement regionDefault = $x("//span[@class='sbis_ru-Region-Chooser__text sbis_ru-link']");
    private final SelenideElement regionKamcatskiy = $x("//span[text()='Камчатский край']");
    private final SelenideElement contactsList = $x("//div[@id='contacts_list']");
    private final SelenideElement cityPartner =
            $x("//div[@id='contacts_list']" +
                    "//div[@class='sbisru-Contacts-City__item-name sbisru-link pr-4 pr-xm-8 sbisru-text-main']");
    private final SelenideElement chooseKamchatskiy = $x("//span[text()='41 Камчатский край']");
    public boolean isTenzorBanner(){
        return TENZOR_BANNER.isDisplayed();
    }

    public TenzorMainPage clickTenzorReference(){
        TENZOR_REFERENCE.click();
        return new TenzorMainPage();
    }

    public String getDefaultRegion(){
        return regionDefault.getText();
    }
    public SbisContactsPage changeRegion(){
        regionDefault.click();
        chooseKamchatskiy
                .shouldBe(visible)
                .click();
        return this;
    }

    public String getKamchatskiyRegion(){
        return regionKamcatskiy
                .shouldBe(visible)
                .getText();
    }

    public String getCityPartner(){
        return cityPartner
                .shouldBe(visible)
                .getText();
    }

    public boolean isContactsListExists(){
        return contactsList
                .scrollTo()
                .shouldBe(visible)
                .isDisplayed();
    }

}
