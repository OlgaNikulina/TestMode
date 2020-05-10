package ru.netology;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.github.javafaker.Faker;

import java.util.Locale;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Post {
    String login;
    String password;
    String status;

    void setFaker() {
        Faker faker = new Faker(new Locale("ru"));
        String name = faker.name().firstName();
        String password = faker.numerify("password");
        String status = faker.letterify("active");
    }
}
        
            
    


