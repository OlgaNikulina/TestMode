package ru.netology.Test;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.dataGenerator.DataGenerator;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

class AuthTest {

    @Test
    void shouldEnterWithNewUser() {
        DataGenerator.RegistrationDto dataGenerator = DataGenerator.getAuthWithCorrectValues();
        open("http://localhost:9999");
        $("input.input__control[type='text']").setValue(dataGenerator.getLogin());
        $("input.input__control[type='text']").sendKeys(Keys.TAB);
        $("input.input__control[type='password']").setValue(dataGenerator.getPassword());
        $$("span.button__text").find(exactText("Продолжить")).click();
        $("div.notification__title").shouldBe(visible);
    }

    @Test
    void shouldEnterWithBlockedStatus() {
        DataGenerator.RegistrationDto dataGenerator = DataGenerator.getAuthWithBlockedStatus();
        open("http://localhost:9999");
        $("input.input__control[type='text']").setValue(dataGenerator.getLogin());
        $("input.input__control[type='text']").sendKeys(Keys.TAB);
        $("input.input__control[type='password']").setValue(dataGenerator.getPassword());
        $$("span.button__text").find(exactText("Продолжить")).click();
        $("div.notification__title").shouldBe(visible);
    }

    @Test
    void shouldEnterWithInvalidLogin() {
        DataGenerator.RegistrationDto dataGenerator = DataGenerator.getNotAuthWithInvalidLogin();
        open("http://localhost:9999");
        $("input.input__control[type='text']").setValue(dataGenerator.getLogin());
        $("input.input__control[type='text']").sendKeys(Keys.TAB);
        $("input.input__control[type='password']").setValue(dataGenerator.getPassword());
        $$("span.button__text").find(exactText("Продолжить")).click();
        $("div.notification__title").shouldBe(visible);
    }

    @Test
    void shouldEnterWithInvalidPassword() {
        DataGenerator.RegistrationDto dataGenerator = DataGenerator.getNotAuthWithInvalidPassword();
        open("http://localhost:9999");
        $("input.input__control[type='text']").setValue(dataGenerator.getLogin());
        $("input.input__control[type='text']").sendKeys(Keys.TAB);
        $("input.input__control[type='password']").setValue(dataGenerator.getPassword());
        $$("span.button__text").find(exactText("Продолжить")).click();
        $("div.notification__title").shouldBe(visible);
    }
}