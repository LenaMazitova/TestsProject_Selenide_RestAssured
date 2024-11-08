package selenide;

//import io.qameta.allure.Allure;
//import org.junit.jupiter.api.extension.TestWatcher;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.events.WebDriverListener;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;

public class Listener extends BaseTest implements WebDriverListener {
//    private static Logger logger = LoggerFactory.getLogger(EventFiringDecorator.class);
//
//    @Override
//    public void beforeAnyWebDriverCall(WebDriver driver, Method method, Object[] args) {
//        Allure.step("beforeWebDriverCall");
//    }
//    @Override
//    public void beforeGet(WebDriver driver, String url) {
//        Allure.step("before get");
//        logger.info("Собираюсь открыть страницу {}", url);
//    }
//    @Override
//    public void onError(Object target, Method method, Object[] args, InvocationTargetException e) {
//        Allure.step("Ошибка = {"+e.getMessage()+"} на target = {"+target.toString()+"}, method = "+ method.getName());
//    }
//    @Override
//    public void beforeClick(WebElement element) {
//        Allure.step("before click on webelement: " + element.getText());
//        logger.info("Собираюсь кликнуть на элемент {}", element);
//    }
}
