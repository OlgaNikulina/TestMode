package ru.netology.datagenerator;


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
        private String login;
        private String password;
        private String status;
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
                .body(dataGenerator)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    static String generateRandomName() {
        Faker faker = new Faker(new Locale("en"));
        return faker.name().firstName();
    }

    static String generatePassword() {
        Faker faker = new Faker(new Locale("en"));
        return faker.internet().password();
    }

    public static RegistrationDto getAuthWithCorrectValues() {
        String login = generateRandomName();
        String password = generatePassword();
        String status = "active";
        RegistrationDto registrationDto = new RegistrationDto(login, password, status);
        setUser(registrationDto);
        return registrationDto;
    }

    public static RegistrationDto getAuthWithBlockedStatus() {
        String login = generateRandomName();
        String password = generatePassword();
        String status = "blocked";
        RegistrationDto registrationDto = new RegistrationDto(login, password, status);
        setUser(registrationDto);
        return registrationDto;
    }

    public static RegistrationDto getNotAuthWithInvalidLogin() {
        String login = generateRandomName();
        String password = generatePassword();;
        String status = "active";
        RegistrationDto registrationDto = new RegistrationDto(login, password, status);
        setUser(registrationDto);
        return new RegistrationDto("l",password, status);
    }

    public static RegistrationDto getNotAuthWithInvalidPassword() {
        String login = generateRandomName();
        String password = generatePassword();;
        String status = "active";
        RegistrationDto registrationDto = new RegistrationDto(login, password, status);
        setUser(registrationDto);
        return new RegistrationDto(login,"l", status);
    }
}
