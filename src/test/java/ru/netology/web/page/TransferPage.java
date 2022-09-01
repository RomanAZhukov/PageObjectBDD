package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Keys;
import ru.netology.web.data.DataHelper;
import ru.netology.web.data.DataHelper.CardInfo;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {


        private SelenideElement amount = $("[data-test-id=amount] input");
        private SelenideElement fromField = $("[data-test-id=from] input");
        private SelenideElement transfer = $("[data-test-id=action-transfer]");
        private SelenideElement error = $("[data-test-id=error-notification]");

        public DashboardPage moneyTransfer(DataHelper.CardInfo from, String amountToTransfer) {
            amount.setValue(amountToTransfer);
            fromField.setValue(from.getNumber());
            transfer.click();
            return new DashboardPage();
        }

        public DashboardPage moneyTransferWithKnownMistake(String from, String amountToTransfer) {
            amount.setValue(amountToTransfer);
            fromField.setValue(from);
            transfer.click();
            return new DashboardPage();
        }

        public SelenideElement getError() {
            return error;
        }
    }