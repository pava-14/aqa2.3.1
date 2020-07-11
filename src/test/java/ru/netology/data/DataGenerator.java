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

        private static String getFullName(Faker faker) {
            String fullName = faker.name().fullName();
            while (fullName.contains("ё") || fullName.contains("Ё")) {
                fullName = faker.name().fullName();
            }
            return fullName;
        }

        private static String getCity(Faker faker) {
            String city = faker.address().city();
            while (!city.contains("ск")) {
                city = faker.address().city();
            }
            return city;
        }

        public static UserInfo generateByUserInfo(String Locale) {
            Faker faker = new Faker(new Locale(Locale));
            faker.address().citySuffix();
            return new UserInfo(
                    getFullName(faker),
                    faker.numerify("+7##########"),
                    getCity(faker),
                    LocalDateTime.now().plusDays(3L + (long) (Math.random() * (360L - 7L)))
            );
        }
    }

}
