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

        /*
        Валидация поля на странице реализована с ошибкой
         if (!/^[- А-Яа-я]+$/.test(name.trim())) {
            setNameError('Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.');
            return;
        }
        Буква «ё» в кодировках расположена отдельно и её необходbимо
        указывать явно: [- А-Яа-яёЁ]
         */
        private static String getFullName(Faker faker) {
            String fullName = faker.name().fullName();
            if(fullName.contains("ё")) {
                fullName = fullName.replace("ё","е");
            }
            if(fullName.contains("Ё")) {
                fullName = fullName.replace("Ё","Е");
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
