package ru.netology.carddelivery;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataGenerator;
import ru.netology.data.UserInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryFormTest {

    private final UserInfo userInfo = DataGenerator.OrderCard.generateByUserInfo("ru");
    private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");


    private String getOrderDateEpochString(LocalDateTime dateTime) {
        Date dt = null;
        try {
            dt = new SimpleDateFormat("dd.MM.yyyy").parse(dateFormat.format(dateTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return String.valueOf(dt.getTime());
    }

    /*
    data-step value:
            "-12" - year's left arrow
            "-1" - month's left arrow
            "12" - year's right arrow "12"
            "1" - month's right arrow "1"
    */
    private void calendarArrowClick(String dataStep) {
        $$(".calendar__arrow")
                .findBy(Condition.attribute("data-step", dataStep)).click();
    }

    private void arrowClick(int count, String dataStep) {
        if (count <= 0) return;
        for (int i = 0; i < count; i++) calendarArrowClick(dataStep);
    }

    private int getMonthArrowClickCount() {
        return userInfo.getOrderDate().getMonth().getValue() - LocalDateTime.now().getMonth().getValue();
    }

    private int getYearArrowClickCount() {
        return userInfo.getOrderDate().getYear() - LocalDateTime.now().getYear();
    }

    private void selectYearMonth(int yearArrowClickCount, int monthArrowClickCount) {
        if (yearArrowClickCount == 0 && monthArrowClickCount == 0) return;

        if (yearArrowClickCount < 0) arrowClick(Math.abs(yearArrowClickCount), "-12");
        if (yearArrowClickCount > 0) arrowClick(yearArrowClickCount, "12");

        if (monthArrowClickCount < 0) arrowClick(Math.abs(monthArrowClickCount), "-1");
        if (monthArrowClickCount > 0) arrowClick(monthArrowClickCount, "1");
    }

    @Test
    public void shouldCreditCardDeliveryOrderByText() {
        open("http://localhost:9999");

        SelenideElement calendar = $(".calendar");
        SelenideElement element = $("form");
        element.$("[data-test-id=city] input").setValue(userInfo.getUserCity().substring(0,2));
        $(byText(userInfo.getUserCity())).click();
        element.$("[data-test-id=date] input").click();

        selectYearMonth(getYearArrowClickCount(), getMonthArrowClickCount());

        calendar.$(byText(String.valueOf(userInfo.getOrderDate().getDayOfMonth()))).click();
        element.$("[data-test-id=name] input").setValue(userInfo.getUserName());
        element.$("[data-test-id=phone] input").setValue(userInfo.getUserPhone());
        element.$("[data-test-id=agreement]").click();
        element.$$("button").find(exactText("Забронировать")).click();

        $(withText("Успешно!")).waitUntil(visible, 15000);
        $(byText("Встреча успешно забронирована на")).shouldBe(visible);
        $(byText(dateFormat.format(userInfo.getOrderDate()))).shouldBe(visible);
    }

    @Test
    public void shouldCreditCardDeliveryOrderByCss() {
        open("http://localhost:9999");

        SelenideElement calendar = $(".calendar");
        SelenideElement element = $("form");
        element.$("[data-test-id=city] input").setValue(userInfo.getUserCity().substring(0,2));
        $(byText(userInfo.getUserCity())).click();
        element.$("[data-test-id=date] input").click();

        selectYearMonth(getYearArrowClickCount(), getMonthArrowClickCount());

        ElementsCollection calendarRows = calendar.$$(".calendar__row .calendar__day");
        calendarRows.findBy(Condition.attribute("data-day",
                getOrderDateEpochString(userInfo.getOrderDate()))).click();

        element.$("[data-test-id=name] input").setValue(userInfo.getUserName());
        element.$("[data-test-id=phone] input").setValue(userInfo.getUserPhone());
        element.$("[data-test-id=agreement]").click();
        element.$$("button").find(exactText("Забронировать")).click();

        $(withText("Успешно!")).waitUntil(visible, 15000);
        $(byText("Встреча успешно забронирована на")).shouldBe(visible);
        $(byText(dateFormat.format(userInfo.getOrderDate()))).shouldBe(visible);
    }
}
