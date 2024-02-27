package ru.mirapolis.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WindowType;
import ru.mirapolis.page.DashboardPage;
import ru.mirapolis.page.LoginPage;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static ru.mirapolis.data.DataHelper.*;

@DisplayName("Тестирование авторизации")
public class AuthTest {
    String loginURL = "https://lmslite47vr.demo.mirapolis.ru/mira";

    @BeforeEach
    void setup() {
        open(loginURL);
    }

    @AfterEach
    void after() {
        closeWebDriver();
    }

    @DisplayName("Авторизация с валидными данными")
    @Test
    public void shouldLoginValidUser() {
        var loginPage = new LoginPage();
        var authInfo = getValidAuthInfo();
        loginPage.validLogin(authInfo);
    }

    @DisplayName("Чувствительность логина к регистру")
    @Test
    public void shouldLoginIfDifferentLoginRegister() {
        var loginPage = new LoginPage();
        var authInfo = new AuthInfo(getValidAuthInfo().getLogin().toUpperCase(), getValidAuthInfo().getPassword());
        loginPage.validLogin(authInfo);
    }

    @DisplayName("Пустые поля ввода")
    @Test
    void shouldGetErrorIfEmptyFields() {
        var loginPage = new LoginPage();
        var authInfo = new AuthInfo("", "");
        loginPage.invalidLogin(authInfo);
    }

    @DisplayName("Пустое поле логина")
    @Test
    void shouldGetErrorIfLoginEmpty() {
        var loginPage = new LoginPage();
        var authInfo = new AuthInfo("", getValidAuthInfo().getPassword());
        loginPage.invalidLogin(authInfo);
    }

    @DisplayName("Пустое поле пароля")
    @Test
    void shouldGetErrorIfPasswordEmpty() {
        var loginPage = new LoginPage();
        var authInfo = new AuthInfo(getValidAuthInfo().getLogin(), "");
        loginPage.invalidLogin(authInfo);
    }

    @DisplayName("Авторизация незарегистрированного пользователя")
    @Test
    void shouldGetErrorIfNotRegisteredUser() {
        var loginPage = new LoginPage();
        var authInfo = getInvalidAuthInfo();
        loginPage.invalidLogin(authInfo);
    }

    @DisplayName("Авторизация с неверным логином")
    @Test
    void shouldGetErrorIfWrongLogin() {
        var loginPage = new LoginPage();
        var authInfo = new AuthInfo(getRandomLogin(), getValidAuthInfo().getPassword());
        loginPage.invalidLogin(authInfo);
    }

    @DisplayName("Авторизация с неверным паролем")
    @Test
    void shouldGetErrorIfWrongPassword() {
        var loginPage = new LoginPage();
        var authInfo = new AuthInfo(getValidAuthInfo().getLogin(), getRandomPassword());
        loginPage.invalidLogin(authInfo);
    }

    @DisplayName("Чувствительность пароля к регистру")
    @Test
    public void shouldGetErrorIfWrongPasswordRegister() {
        var loginPage = new LoginPage();
        var authInfo = new AuthInfo(getValidAuthInfo().getLogin(), getValidAuthInfo().getPassword().toLowerCase());
        loginPage.invalidLogin(authInfo);
    }

    @DisplayName("Лишний пробел в логине")
    @Test
    void shouldGetErrorIfLoginWithExtraSpace() {
        var loginPage = new LoginPage();
        var authInfo = new AuthInfo(getValidAuthInfo().getLogin() + " ", getValidAuthInfo().getPassword());
        loginPage.invalidLogin(authInfo);
    }

    @DisplayName("Лишний пробел в пароле")
    @Test
    void shouldGetErrorIfPasswordWithExtraSpace() {
        var loginPage = new LoginPage();
        var authInfo = new AuthInfo(getValidAuthInfo().getLogin(), " " + getValidAuthInfo().getPassword());
        loginPage.invalidLogin(authInfo);
    }

    @DisplayName("Открытие дашборда по url логина после пройденной авторизации")
    @Test
    void shouldOpenDashboardInNewTabIfAlreadyLogin() {
        var loginPage = new LoginPage();
        var authInfo = getValidAuthInfo();
        loginPage.validLogin(authInfo);
        switchTo().newWindow(WindowType.TAB);
        open(loginURL);
        new DashboardPage();
    }

    @DisplayName("Выход из кабинета сразу во всех вкладках")
    @Test
    void shouldLogoutInAllTabs() {
        var loginPage = new LoginPage();
        var authInfo = getValidAuthInfo();
        loginPage.validLogin(authInfo);
        switchTo().newWindow(WindowType.TAB);
        open(loginURL);
        var dashboard = new DashboardPage();
        dashboard.logOut();
        switchTo().window(0);
        refresh();
        loginPage.getElementLoginPage().shouldBe(visible);
    }

    @DisplayName("Возврат на страницу дашборда после логаута")
    @Test
    void shouldNotReturnToDashboardAfterLogout() {
        var loginPage = new LoginPage();
        var authInfo = getValidAuthInfo();
        loginPage.validLogin(authInfo);
        var dashboard = new DashboardPage();
        dashboard.logOut();
        back();
        loginPage.getElementLoginPage().shouldBe(visible);
        forward();
        loginPage.getElementLoginPage().shouldBe(visible);
    }
}
