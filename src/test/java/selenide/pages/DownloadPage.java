package selenide.pages;

import com.codeborne.selenide.DownloadOptions;
import com.codeborne.selenide.FileDownloadMode;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.SneakyThrows;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codeborne.selenide.Selenide.$x;

public class DownloadPage {
    private final SelenideElement downloadWebInstallerRef =
            $x("//a[@href='https://update.sbis.ru/Sbis3Plugin/master/win32/sbisplugin-setup-web.exe']");

    @Step("Скачивается файл (таймаут 30 сек)")
    @SneakyThrows
    public File downloadWebInstaller(){
        return downloadWebInstallerRef
                .download(DownloadOptions.using(FileDownloadMode.FOLDER)
                        .withTimeout(30_000));
    }
    @Step("Возвращается значение в формате 0.00 мегабайт из текста локатора для скачивания файла")
    public Double getFileLengthMegaBytes(){
        String text = downloadWebInstallerRef.getText();
        Pattern compile = Pattern.compile("\\d+\\.\\d+");
        Matcher matcher = compile.matcher(text);
        if (matcher.find()) {
            String matched = matcher.group();
            return Double.valueOf(matched);
        }
        return 0.00;
    }
}
