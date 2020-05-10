package ru.netology;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import java.io.File;
import java.time.LocalDate;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;
import static io.restassured.RestAssured.given;

class AuthTest {
    LocalDate date = LocalDate.now();
    LocalDate localDate;
    Faker faker;

    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    private static class RegistrationDTo extends File {
        public RegistrationDTo(String name, String password, String status) {
            super("http://localhost:9999");
        }
    }

    @BeforeAll
    static void setUpAll() {
        given()
                .spec(requestSpec)
                .body(new RegistrationDTo("vasya", "password", "active"))
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    @Test
    void shouldEnterWithNewUser() {
        open("http://localhost:9999");
        $("input.input__control[type='text']").setValue(faker.letterify("vasya"));
        $("input.input__control[type='password']").setValue(faker.letterify("password"));
        $$("#root > div > div > form > fieldset > div:nth-child(3) > button > span > span > span").find(exactText("Продолжить")).click();
        //$("div.notification__title").waitUntil(visible, 15000);
    }
}
