package ru.netology.delivery.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

class DeliveryTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
        Configuration.holdBrowserOpen = true;
    }

    @Test
    void shouldSuccessfulPlanAndRePlanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $x("//span[@data-test-id='city']//input").setValue(validUser.getCity());
        $x("//span[@data-test-id='date']//input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//span[@data-test-id='date']//input").sendKeys(firstMeetingDate);
        $x("//span[@data-test-id='name']//input").setValue(validUser.getName());
        $x("//span[@data-test-id='phone']//input").setValue(validUser.getPhone());
        $x("//label[@data-test-id='agreement']").click();
        $x("//span[contains(text(), 'Запланировать')]").click();
        $x("//div[@class='notification__content']").shouldHave(Condition.text("Встреча успешно запланирована на " + firstMeetingDate));
        $x("//span[@data-test-id='date']//input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//span[@data-test-id='date']//input").setValue(secondMeetingDate);
        $x("//span[contains(text(), 'Запланировать')]").click();
        $x("//div[@data-test-id='replan-notification']").shouldBe(visible);
        $x("//span[contains(text(), 'Перепланировать')]").click();
        $x("//div[@class='notification__content']").shouldHave(Condition.text("Встреча успешно запланирована на " + secondMeetingDate));
    }
}
