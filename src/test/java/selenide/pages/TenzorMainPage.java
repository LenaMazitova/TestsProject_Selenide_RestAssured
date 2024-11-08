package selenide.pages;


import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class TenzorMainPage {
    private final SelenideElement contentBlogSila =
            $x("//div[@class='tensor_ru-Index__block4-content tensor_ru-Index__card']");

    private final SelenideElement referencePodrobnee =
            $x("//a[@href='/about' and text()='Подробнее']");

    @Step("Проверяем видимость блока 'Сила в людях' ")
    public boolean isBlogSilaDisplayed(){
        return contentBlogSila
                .scrollTo()
                .shouldBe(visible)
                .isDisplayed();
    }

    @Step("Нажимаем на ссылку 'Подробнее'")
    public TenzorAboutPage goReferencePodrobnee(){
        referencePodrobnee.click();
        return new TenzorAboutPage();
    }
}
