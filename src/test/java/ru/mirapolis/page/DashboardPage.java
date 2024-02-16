package ru.mirapolis.page;

import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {
    private SelenideElement logoutMenu = $("[data-mira-id=DropDownContainer28]");
    private SelenideElement logoutLink = $("[data-mira-id=Link33]");
    private SelenideElement startPage = $("[data-mira-id=Page7]");

    public DashboardPage(){
        startPage.shouldBe(visible);
    }
    public boolean checkVisible() {
        startPage.should(visible,Duration.ofSeconds(5));
        return startPage.isDisplayed();
    }
    public LoginPage logOut(){
        logoutMenu.click();
        logoutLink.click();
        return new LoginPage();
    }
}
