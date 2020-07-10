package ru.netology.data;

import com.github.javafaker.Faker;

import java.time.LocalDateTime;
import java.util.Locale;

public class DataGenerator {
    private DataGenerator() {
    }

    public static class OrderCard {
        private OrderCard() {
        }

        public static UserInfo generateByUserInfo(String Locale) {
            Faker faker = new Faker(new Locale(Locale));
            return new UserInfo(
                    faker.name().fullName(),
                    faker.phoneNumber().cellPhone(),
                    faker.address().city(),
                    LocalDateTime.now().plusDays(3L + (long) (Math.random() * (360L - 7L)))
            );
        }
    }

}
