package ru.mirapolis.data;

import lombok.Value;
import com.github.javafaker.Faker;

import java.util.Locale;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    private static final Faker faker = new Faker(new Locale("en"));

    public static String getRandomLogin() {
        return faker.name().username();
    }

    public static String getRandomPassword() {
        return faker.internet().password();
    }

    public static AuthInfo getValidAuthInfo() {
        return new AuthInfo("fominaelena", "1P73BP4Z");
    }

    public static AuthInfo getInvalidAuthInfo() {
        return new AuthInfo(getRandomLogin(), getRandomPassword());
    }

}
