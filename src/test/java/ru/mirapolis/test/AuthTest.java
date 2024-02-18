package ru.mirapolis.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WindowType;
import ru.mirapolis.page.DashboardPage;
import ru.mirapolis.page.LoginPage;

import static com.codeborne.selenide.Selenide.*;
import static ru.mirapolis.data.DataHelper.*;

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

    @Test
    public void shouldLoginValidUser() {
        var loginPage = new LoginPage();
        var authInfo = getValidAuthInfo();
        loginPage.validLogin(authInfo);
    }

    @Test
    public void shouldLoginIfDifferentLoginRegister() {
        var loginPage = new LoginPage();
        var authInfo = new AuthInfo(getValidAuthInfo().getLogin().toUpperCase(), getValidAuthInfo().getPassword());
        loginPage.validLogin(authInfo);
    }

    @Test
    void shouldGetErrorIfEmptyFields() {
        var loginPage = new LoginPage();
        var authInfo = new AuthInfo("", "");
        loginPage.invalidLogin(authInfo);
    }

    @Test
    void shouldGetErrorIfLoginEmpty() {
        var loginPage = new LoginPage();
        var authInfo = new AuthInfo("", getValidAuthInfo().getPassword());
        loginPage.invalidLogin(authInfo);
    }

    @Test
    void shouldGetErrorIfPasswordEmpty() {
        var loginPage = new LoginPage();
        var authInfo = new AuthInfo(getValidAuthInfo().getLogin(), "");
        loginPage.invalidLogin(authInfo);
    }

    @Test
    void shouldGetErrorIfNotRegisteredUser() {
        var loginPage = new LoginPage();
        var authInfo = getInvalidAuthInfo();
        loginPage.invalidLogin(authInfo);
    }

    @Test
    void shouldGetErrorIfWrongLogin() {
        var loginPage = new LoginPage();
        var authInfo = new AuthInfo(getRandomLogin(), getValidAuthInfo().getPassword());
        loginPage.invalidLogin(authInfo);
    }

    @Test
    void shouldGetErrorIfWrongPassword() {
        var loginPage = new LoginPage();
        var authInfo = new AuthInfo(getValidAuthInfo().getLogin(), getRandomPassword());
        loginPage.invalidLogin(authInfo);
    }

    @Test
    public void shouldGetErrorIfWrongPasswordRegister() {
        var loginPage = new LoginPage();
        var authInfo = new AuthInfo(getValidAuthInfo().getLogin(), getValidAuthInfo().getPassword().toLowerCase());
        loginPage.invalidLogin(authInfo);
    }

    @Test
    void shouldGetErrorIfLoginWithExtraSpace() {
        var loginPage = new LoginPage();
        var authInfo = new AuthInfo(getValidAuthInfo().getLogin() + " ", getValidAuthInfo().getPassword());
        loginPage.invalidLogin(authInfo);
    }

    @Test
    void shouldGetErrorIfPasswordWithExtraSpace() {
        var loginPage = new LoginPage();
        var authInfo = new AuthInfo(getValidAuthInfo().getLogin(), " " + getValidAuthInfo().getPassword());
        loginPage.invalidLogin(authInfo);
    }

    @Test
    void shouldOpenDashboardInNewTabIfAlreadyLogin() {
        var loginPage = new LoginPage();
        var authInfo = getValidAuthInfo();
        loginPage.validLogin(authInfo);
        switchTo().newWindow(WindowType.TAB);
        open(loginURL);
        new DashboardPage();
    }

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
        loginPage.checkVisible();
    }

    @Test
    void shouldNotReturnToDashboardAfterLogout() {
        var loginPage = new LoginPage();
        var authInfo = getValidAuthInfo();
        loginPage.validLogin(authInfo);
        var dashboard = new DashboardPage();
        dashboard.logOut();
        back();
        loginPage.checkVisible();
        forward();
        loginPage.checkVisible();
    }
}
