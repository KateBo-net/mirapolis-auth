package ru.mirapolis.page;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Alert;
import ru.mirapolis.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.switchTo;

public class LoginPage {

    private final SelenideElement loginField = $("input[name=user]");
    private final SelenideElement passwordField = $("input[name=password]");
    private final SelenideElement loginButton = $("button[type=submit]");
    private final SelenideElement loginPage = $(".mira-page-login");

    public LoginPage() {
        loginPage.shouldBe(visible);
    }

    public SelenideElement getElementLoginPage() {
        return loginPage;
    }

    private void login(DataHelper.AuthInfo info){
        loginField.setValue(info.getLogin());
        passwordField.setValue(info.getPassword());
        loginButton.click();
    }

    public DashboardPage validLogin(DataHelper.AuthInfo info) {
        login(info);
        return new DashboardPage();
    }

    public void invalidLogin(DataHelper.AuthInfo info) {
        login(info);
        Alert alert = switchTo().alert();
        Assertions.assertTrue(alert.getText().contains("Неверные данные для авторизации"));
        alert.accept();
    }
}
