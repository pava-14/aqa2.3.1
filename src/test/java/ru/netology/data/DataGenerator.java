package ru.netology.data;

import com.github.javafaker.Faker;

import java.time.LocalDateTime;
import java.util.Locale;

public class DataGenerator {
    private DataGenerator() {
    }

    public static class OrderInfo {
        private OrderInfo() {
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

        public static UserInfo generateUserInfo(String Locale) {
            Faker faker = new Faker(new Locale(Locale));
            return new UserInfo(
                    getFullName(faker),
                    faker.numerify("+7##########"),
                    getCity(faker),
                    LocalDateTime.now().plusDays(3L + (long) (Math.random() * (360L - 7L)))
            );
        }

        public static LocalDateTime generateOrderDate() {
            return LocalDateTime.now().plusDays(3L + (long) (Math.random() * (360L - 7L)));
        }
    }
}
