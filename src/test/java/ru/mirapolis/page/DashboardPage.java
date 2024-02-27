package ru.mirapolis.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {
    private final SelenideElement logoutMenu = $("[data-mira-id=DropDownContainer28]");
    private final SelenideElement logoutLink = $("[data-mira-id=Link33]");
    private final SelenideElement startPage = $("[data-mira-id=Page7]");

    public DashboardPage() {
        startPage.shouldBe(visible);
    }

    public LoginPage logOut() {
        logoutMenu.click();
        logoutLink.click();
        return new LoginPage();
    }
}
