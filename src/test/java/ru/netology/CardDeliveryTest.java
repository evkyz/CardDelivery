package ru.netology;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.selector.ByText;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.managers.ChromeDriverManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

class CardDeliveryTest {

    @BeforeAll
    static void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeDriverManager.getInstance().setup();
    }

    @Test
    void testStandartDay() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $$x("//input[@type=\"text\"]").first().val("Москва");
        $x("//input[@name=\"name\"]").val("Иван Иванов");
        $x("//input[@name=\"phone\"]").val("+79031234567");
        $x("//span[@class=\"checkbox__box\"]").click();
        $x("//*[text()=\"Забронировать\"]").click();
        $x("//div[@data-test-id=\"notification\"]").should(visible, Duration.ofSeconds(15));
    }

    @Test
    void testNextDay() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $$x("//input[@type=\"text\"]").first().val("Москва");
        $x("//span[@class=\"icon icon_size_m icon_name_calendar icon_theme_alfa-on-white\"]").click();
        $x("//div[@class=\"calendar calendar_theme_alfa-on-white\"]").sendKeys(Keys.RIGHT);
        $x("//input[@name=\"name\"]").val("Иван Иванов");
        $x("//input[@name=\"phone\"]").val("+79031234567");
        $x("//span[@class=\"checkbox__box\"]").click();
        $x("//*[text()=\"Забронировать\"]").click();
        $x("//div[@data-test-id=\"notification\"]").should(visible, Duration.ofSeconds(15));
    }

    @Test
    void testNoCity() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $$x("//input[@type=\"text\"]").first().val("");
        $x("//input[@name=\"name\"]").val("Иван Иванов");
        $x("//input[@name=\"phone\"]").val("+79031234567");
        $x("//span[@class=\"checkbox__box\"]").click();
        $x("//*[text()=\"Забронировать\"]").click();
        $(new ByText("Поле обязательно для заполнения")).should(visible);
    }

    @Test
    void testNoName() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $$x("//input[@type=\"text\"]").first().val("Москва");
        $x("//input[@name=\"name\"]").val("");
        $x("//input[@name=\"phone\"]").val("+79031234567");
        $x("//span[@class=\"checkbox__box\"]").click();
        $x("//*[text()=\"Забронировать\"]").click();
        $(new ByText("Поле обязательно для заполнения")).should(visible);
    }

    @Test
    void testNoPhone() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $$x("//input[@type=\"text\"]").first().val("Москва");
        $x("//input[@name=\"name\"]").val("Иван Иванов");
        $x("//input[@name=\"phone\"]").val("");
        $x("//span[@class=\"checkbox__box\"]").click();
        $x("//*[text()=\"Забронировать\"]").click();
        $(new ByText("Поле обязательно для заполнения")).should(visible);
    }

    @Test
    void testNoChekbox() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $$x("//input[@type=\"text\"]").first().val("Москва");
        $x("//input[@name=\"name\"]").val("Иван Иванов");
        $x("//input[@name=\"phone\"]").val("+79031234567");
        $x("//*[text()=\"Забронировать\"]").click();
        $(new ByText("Я соглашаюсь с условиями обработки и использования моих персональных данных")).should(visible);
    }

    @Test
    void testBadCity() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $$x("//input[@type=\"text\"]").first().val("Город");
        $x("//*").click();
        $x("//input[@name=\"name\"]").val("Иван Иванов");
        $x("//input[@name=\"phone\"]").val("+79031234567");
        $x("//span[@class=\"checkbox__box\"]").click();
        $x("//*[text()=\"Забронировать\"]").click();
        $(new ByText("Доставка в выбранный город недоступна")).should(visible);
    }

    @Test
    void testBadName() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $$x("//input[@type=\"text\"]").first().val("Москва");
        $x("//input[@name=\"name\"]").val("Ivan Ivanov");
        $x("//input[@name=\"phone\"]").val("+79031234567");
        $x("//span[@class=\"checkbox__box\"]").click();
        $x("//*[text()=\"Забронировать\"]").click();
        $(new ByText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")).should(visible);
    }

    @Test
    void testBadPhone() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $$x("//input[@type=\"text\"]").first().val("Москва");
        $x("//input[@name=\"name\"]").val("Иван Иванов");
        $x("//input[@name=\"phone\"]").val("89031234567");
        $x("//span[@class=\"checkbox__box\"]").click();
        $x("//*[text()=\"Забронировать\"]").click();
        $(new ByText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")).should(visible);
    }

    @Test
    void testBadDate() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $$x("//input[@type=\"text\"]").first().val("Москва");
        $x("//input[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $x("//input[@placeholder=\"Дата встречи\"]").val("00.00.0000");
        $x("//input[@name=\"name\"]").val("Иван Иванов");
        $x("//input[@name=\"phone\"]").val("+79031234567");
        $x("//span[@class=\"checkbox__box\"]").click();
        $x("//*[text()=\"Забронировать\"]").click();
        $(new ByText("Неверно введена дата")).should(visible);
    }

    @Test
    void testNoDate() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $$x("//input[@type=\"text\"]").first().val("Москва");
        $x("//input[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $x("//input[@name=\"name\"]").val("Иван Иванов");
        $x("//input[@name=\"phone\"]").val("+79031234567");
        $x("//span[@class=\"checkbox__box\"]").click();
        $x("//*[text()=\"Забронировать\"]").click();
        $(new ByText("Неверно введена дата")).should(visible);
    }

    @Test
    void testTwoLetters() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $$x("//input[@type=\"text\"]").first().val("Во");
        $x("//span[text()=\"Воронеж\"]").click();
        $x("//input[@name=\"name\"]").val("Иван Иванов");
        $x("//input[@name=\"phone\"]").val("+79031234567");
        $x("//span[@class=\"checkbox__box\"]").click();
        $x("//*[text()=\"Забронировать\"]").click();
        $x("//div[@data-test-id=\"notification\"]").should(visible, Duration.ofSeconds(15));
    }

    @Test
    void testDateWeek() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $$x("//input[@type=\"text\"]").first().val("Во");
        $x("//span[text()=\"Воронеж\"]").click();
        $x("//span[@class=\"icon icon_size_m icon_name_calendar icon_theme_alfa-on-white\"]").click();
        $x("//div[@class=\"calendar calendar_theme_alfa-on-white\"]").sendKeys(Keys.RIGHT);
        $x("//div[@class=\"calendar calendar_theme_alfa-on-white\"]").sendKeys(Keys.RIGHT);
        $x("//div[@class=\"calendar calendar_theme_alfa-on-white\"]").sendKeys(Keys.RIGHT);
        $x("//div[@class=\"calendar calendar_theme_alfa-on-white\"]").sendKeys(Keys.RIGHT);
        $x("//div[@class=\"calendar calendar_theme_alfa-on-white\"]").sendKeys(Keys.RIGHT);
        $x("//div[@class=\"calendar calendar_theme_alfa-on-white\"]").sendKeys(Keys.RIGHT);
        $x("//div[@class=\"calendar calendar_theme_alfa-on-white\"]").sendKeys(Keys.RIGHT);
        $x("//input[@name=\"name\"]").val("Иван Иванов");
        $x("//input[@name=\"phone\"]").val("+79031234567");
        $x("//span[@class=\"checkbox__box\"]").click();
        $x("//*[text()=\"Забронировать\"]").click();
        $x("//div[@data-test-id=\"notification\"]").should(visible, Duration.ofSeconds(15));
    }
}
