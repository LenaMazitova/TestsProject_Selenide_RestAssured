package selenide.pages;


import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class TenzorMainPage {
    private final SelenideElement contentBlogSila =
            $x("//div[@class='tensor_ru-Index__block4-content tensor_ru-Index__card']");

    private final SelenideElement referencePodrobnee =
            $x("//a[@href='/about' and text()='Подробнее']");

    public boolean isBlogSilaDisplayed(){
        return contentBlogSila
                .scrollTo()
                .shouldBe(visible)
                .isDisplayed();
    }
    public void waitForVisibility(){
        int attempts = 0;
        final int maxAttempts = 10; // Максимальное количество попыток прокрутки
        final int scrollAmount = 300; // Параметр прокрутки (в пикселях)

        while (attempts < maxAttempts) {
            if (contentBlogSila.is(visible)) {
                return; // Если элемент видим, выходим из метода
            }

            // Прокручиваем вниз
            executeJavaScript("window.scrollBy(0, " + scrollAmount + ");");
            sleep(500); // Небольшая задержка перед следующей проверкой

            attempts++;
        }

        // Если элемент не был найден за максимальное количество попыток, можно выбросить исключение или логировать
        throw new RuntimeException("Element contentBlogSila is not visible after scrolling.");
    }

   public TenzorAboutPage goReferencePodrobnee(){
        referencePodrobnee.click();
        return new TenzorAboutPage();
    }
}
