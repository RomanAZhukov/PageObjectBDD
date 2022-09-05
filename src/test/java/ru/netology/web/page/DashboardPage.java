package ru.netology.web.page;



import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class DashboardPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private SelenideElement firstCard = $x("//div[contains(@data-test-id,\"92df3f1c-a033-48e6-8390-206f6b1f56c0\")]");
    private SelenideElement secondCard = $x("//div[contains(@data-test-id,\"0f3f5c2a-249e-4c3d-8287-09f7a039391d\")]");

    private SelenideElement reload = $("[data-test-id=action-reload]");
    private SelenideElement cancel = $x("//*[contains(text(),'Отмена')]");
    private ElementsCollection depositButton = $$x("//span[contains(text(),'Пополнить')]");

    public DashboardPage() {
        heading.shouldBe(visible);
    }

    private final ElementsCollection cards = $$(".list__item div");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public int getCardBalance(String id) {
        String text = cards.findBy(text(DataHelper.getCard(id).getNumber().substring(12, 16))).getText();
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public void depositToSecond() {
        depositButton.last().click();
    }

    public void depositToFirst() {
        depositButton.first().click();
    }
    public void reloadBalance() {
        reload.click();
    }

    public void cancelMoneyTransfer() {
        cancel.click();
    }
}
