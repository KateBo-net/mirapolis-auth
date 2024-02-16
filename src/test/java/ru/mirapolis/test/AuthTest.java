package ru.mirapolis.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.mirapolis.page.LoginPage;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;
import static ru.mirapolis.data.DataHelper.*;

public class AuthTest {

    @BeforeEach
    void setup() {
        open("https://lmslite47vr.demo.mirapolis.ru/mira");
    }
    @AfterEach
    void after(){
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
        var authInfo = new AuthInfo(" " + getValidAuthInfo().getLogin() + " ", getValidAuthInfo().getPassword());
        loginPage.invalidLogin(authInfo);
    }

    @Test
    void shouldGetErrorIfPasswordWithExtraSpace() {
        var loginPage = new LoginPage();
        var authInfo = new AuthInfo(getValidAuthInfo().getLogin(), " " + getValidAuthInfo().getPassword() + " ");
        loginPage.invalidLogin(authInfo);
    }
}
