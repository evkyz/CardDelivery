package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.selector.ByText;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

class CardDeliveryTest {

    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
    }

    @Test
    void testStandartDay() {
        String planningDate = generateDate(3);
        $x("//input[@placeholder=\"Город\"]").val("Москва");
        $x("//input[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $x("//input[@name=\"name\"]").val("Иван Иванов");
        $x("//input[@name=\"phone\"]").val("+79031234567");
        $x("//span[@class=\"checkbox__box\"]").click();
        $x("//*[text()=\"Забронировать\"]").click();
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15));
    }

    @Test
    void testNextDay() {
        String planningDate = generateDate(4);
        $x("//input[@placeholder=\"Город\"]").val("Москва");
        $x("//input[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $x("//input[@name=\"name\"]").val("Иван Иванов");
        $x("//input[@name=\"phone\"]").val("+79031234567");
        $x("//span[@class=\"checkbox__box\"]").click();
        $x("//*[text()=\"Забронировать\"]").click();
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15));
    }

    @Test
    void testNoCity() {
        String planningDate = generateDate(3);
        $$x("//input[@type=\"text\"]").first().val("");
        $x("//input[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $x("//input[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $x("//input[@name=\"name\"]").val("Иван Иванов");
        $x("//input[@name=\"phone\"]").val("+79031234567");
        $x("//span[@class=\"checkbox__box\"]").click();
        $x("//*[text()=\"Забронировать\"]").click();
        $(new ByText("Поле обязательно для заполнения")).should(visible);
    }

    @Test
    void testNoName() {
        String planningDate = generateDate(3);
        $x("//input[@placeholder=\"Город\"]").val("Москва");
        $x("//input[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $x("//input[@name=\"name\"]").val("");
        $x("//input[@name=\"phone\"]").val("+79031234567");
        $x("//span[@class=\"checkbox__box\"]").click();
        $x("//*[text()=\"Забронировать\"]").click();
        $(new ByText("Поле обязательно для заполнения")).should(visible);
    }

    @Test
    void testNoPhone() {
        String planningDate = generateDate(3);
        $x("//input[@placeholder=\"Город\"]").val("Москва");
        $x("//input[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $x("//input[@name=\"name\"]").val("Иван Иванов");
        $x("//input[@name=\"phone\"]").val("");
        $x("//span[@class=\"checkbox__box\"]").click();
        $x("//*[text()=\"Забронировать\"]").click();
        $(new ByText("Поле обязательно для заполнения")).should(visible);
    }

    @Test
    void testNoChekbox() {
        String planningDate = generateDate(3);
        $x("//input[@placeholder=\"Город\"]").val("Москва");
        $x("//input[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $x("//input[@name=\"name\"]").val("Иван Иванов");
        $x("//input[@name=\"phone\"]").val("+79031234567");
        $x("//*[text()=\"Забронировать\"]").click();
        $(new ByText("Я соглашаюсь с условиями обработки и использования моих персональных данных")).should(visible);
    }

    @Test
    void testBadCity() {
        String planningDate = generateDate(3);
        $x("//input[@placeholder=\"Город\"]").val("Город");
        $x("//*").click();
        $x("//input[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $x("//input[@name=\"name\"]").val("Иван Иванов");
        $x("//input[@name=\"phone\"]").val("+79031234567");
        $x("//span[@class=\"checkbox__box\"]").click();
        $x("//*[text()=\"Забронировать\"]").click();
        $(new ByText("Доставка в выбранный город недоступна")).should(visible);
    }

    @Test
    void testBadName() {
        String planningDate = generateDate(3);
        $x("//input[@placeholder=\"Город\"]").val("Москва");
        $x("//input[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $x("//input[@name=\"name\"]").val("Ivan Ivanov");
        $x("//input[@name=\"phone\"]").val("+79031234567");
        $x("//span[@class=\"checkbox__box\"]").click();
        $x("//*[text()=\"Забронировать\"]").click();
        $(new ByText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")).should(visible);
    }

    @Test
    void testBadPhone() {
        String planningDate = generateDate(3);
        $x("//input[@placeholder=\"Город\"]").val("Москва");
        $x("//input[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $x("//input[@name=\"name\"]").val("Иван Иванов");
        $x("//input[@name=\"phone\"]").val("89031234567");
        $x("//span[@class=\"checkbox__box\"]").click();
        $x("//*[text()=\"Забронировать\"]").click();
        $(new ByText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")).should(visible);
    }

    @Test
    void testBadDate() {
        String planningDate = generateDate(1);
        $x("//input[@placeholder=\"Город\"]").val("Москва");
        $x("//input[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $x("//input[@name=\"name\"]").val("Иван Иванов");
        $x("//input[@name=\"phone\"]").val("+79031234567");
        $x("//span[@class=\"checkbox__box\"]").click();
        $x("//*[text()=\"Забронировать\"]").click();
        $(new ByText("Заказ на выбранную дату невозможен")).should(visible);
    }

    @Test
    void testNoDate() {
        $x("//input[@placeholder=\"Город\"]").val("Москва");
        $x("//input[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $x("//input[@name=\"name\"]").val("Иван Иванов");
        $x("//input[@name=\"phone\"]").val("+79031234567");
        $x("//span[@class=\"checkbox__box\"]").click();
        $x("//*[text()=\"Забронировать\"]").click();
        $(new ByText("Неверно введена дата")).should(visible);
    }

    @Test
    void testTwoLetters() {
        String planningDate = generateDate(3);
        $x("//input[@placeholder=\"Город\"]").val("Во");
        $x("//span[text()=\"Воронеж\"]").click();
        $("[data-test-id='date'] input").setValue(planningDate);
        $x("//input[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $x("//input[@name=\"name\"]").val("Иван Иванов");
        $x("//input[@name=\"phone\"]").val("+79031234567");
        $x("//span[@class=\"checkbox__box\"]").click();
        $x("//*[text()=\"Забронировать\"]").click();
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15));
    }

    @Test
    void testDateWeek() {
        String planningDate = generateDate(7);
        $x("//input[@placeholder=\"Город\"]").val("Во");
        $x("//span[text()=\"Воронеж\"]").click();
        $x("//span[@class=\"icon-button__text\"]").click();
        $x("//div[@class=\"calendar calendar_theme_alfa-on-white\"]").sendKeys(Keys.RIGHT);
        $x("//div[@class=\"calendar calendar_theme_alfa-on-white\"]").sendKeys(Keys.RIGHT);
        $x("//div[@class=\"calendar calendar_theme_alfa-on-white\"]").sendKeys(Keys.RIGHT);
        $x("//div[@class=\"calendar calendar_theme_alfa-on-white\"]").sendKeys(Keys.RIGHT);
        $x("//input[@name=\"name\"]").val("Иван Иванов");
        $x("//input[@name=\"phone\"]").val("+79031234567");
        $x("//span[@class=\"checkbox__box\"]").click();
        $x("//*[text()=\"Забронировать\"]").click();
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15));
    }
}
