package selenide.pages;

import com.codeborne.selenide.ElementsCollection;
import java.util.Arrays;
import java.util.List;

import static com.codeborne.selenide.Selenide.$$x;

public class TenzorAboutPage {
    private final ElementsCollection fotos =
            $$x("//div[@class='tensor_ru-container tensor_ru-section tensor_ru-About__block3']//img");

    public List<List<String>> getFotoValues(){
        List<String> widths = fotos.attributes("width");
        List<String> heights = fotos.attributes("height");
        return Arrays.asList(widths, heights);
    }

}