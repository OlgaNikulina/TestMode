package ru.netology.dataGenerator;


import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Data;
import lombok.Value;

import java.util.Locale;

import static io.restassured.RestAssured.given;

@Data
public class DataGenerator {
    private DataGenerator() {
    }

    @Value
    public static class RegistrationDto {
        String login;
        String password;
        String status;
    }

    private static RequestSpecification
            requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    static void setUser(DataGenerator.RegistrationDto dataGenerator) {
        given()
                .spec(requestSpec)
                .body(new RegistrationDto("vasya", "password", "active"))
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    static String generateRandomName() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.name().firstName();
    }

    static String generatePassword() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.letterify("123456");
    }

    public static RegistrationDto getAuthWithCorrectValues() {
        return new RegistrationDto(generateRandomName(), generatePassword(), "active");
    }

    public static RegistrationDto getAuthWithBlockedStatus() {
        return new RegistrationDto(generateRandomName(), generatePassword(), "blocked");
    }

    public static RegistrationDto getNotAuthWithInvalidLogin() {
        return new RegistrationDto("дддд", generatePassword(), "blocked");
    }

    public static RegistrationDto getNotAuthWithInvalidPassword() {
        return new RegistrationDto(generateRandomName(), "llkjkk", "active");
    }
}
