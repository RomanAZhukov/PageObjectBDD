package ru.netology.web.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;
import ru.netology.web.page.TransferPage;

import static com.codeborne.selenide.Selenide.open;

class TransferTest {
    @BeforeEach
    void setup() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
        Configuration.holdBrowserOpen = true;
    }

    private String intToString(int cardBalance) {
        return Integer.toString(cardBalance);
    }

    @Test
    void shouldTransferMoneyFromSecondCardToFirstCard() {
        var dashboardPage = new DashboardPage();
        int expected = dashboardPage.getCardBalance("1") + 1000;
        int expected1 = dashboardPage.getCardBalance("2") - 1000;
        dashboardPage.depositToFirst();
        var transferPage = new TransferPage();
        transferPage.moneyTransfer(DataHelper.getCard("2"), "1000");
        int actual = dashboardPage.getCardBalance("1");
        int actual1 = dashboardPage.getCardBalance("2");

        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected1, actual1);
    }

    @Test
    void shouldTransferMoneyFromFirstCardToSecondCard() {
        var dashboardPage = new DashboardPage();
        int expected = dashboardPage.getCardBalance("2") + 1000;
        int expected1 = dashboardPage.getCardBalance("1") - 1000;
        dashboardPage.depositToSecond();
        var transferPage = new TransferPage();
        transferPage.moneyTransfer(DataHelper.getCard("1"), "1000");
        int actual = dashboardPage.getCardBalance("2");
        int actual1 = dashboardPage.getCardBalance("1");

        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected1, actual1);
    }


    @Test
    void shouldReloadCardBalance() {
        var dashboardPage = new DashboardPage();
        var moneyTransferPage = new TransferPage();

        int expected = dashboardPage.getCardBalance("2");

        dashboardPage.reloadBalance();
        int actual = dashboardPage.getCardBalance("2");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldCancelMoneyTransfer() {
        var dashboardPage = new DashboardPage();

        int expected = dashboardPage.getCardBalance("1");
        int expected1 = dashboardPage.getCardBalance("2");

        dashboardPage.depositToFirst();
        dashboardPage.cancelMoneyTransfer();

        int actual = dashboardPage.getCardBalance("1");
        int actual1 = dashboardPage.getCardBalance("2");

        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected1, actual1);
    }

    @Test
    void shouldNotTransferIfAccountIsNotSpecified() {
        var dashboardPage = new DashboardPage();

        dashboardPage.depositToFirst();
        var moneyTransferPage = new TransferPage();
        moneyTransferPage.moneyTransfer(DataHelper.getCard(""), "1000");

        moneyTransferPage.getError();
    }


    @Test
    void shouldTransferFullAmountFromAccount() {
        var dashboardPage = new DashboardPage();
        String balance = String.valueOf(dashboardPage.getCardBalance("1") + 100);

        int expected = dashboardPage.getCardBalance("1");
        int expected1 = dashboardPage.getCardBalance("2");

        dashboardPage.depositToSecond();
        var moneyTransferPage = new TransferPage();
        moneyTransferPage.moneyTransfer(DataHelper.getCard("1"), balance);

        int actual = dashboardPage.getCardBalance("1");
        int actual1 = dashboardPage.getCardBalance("2");

        Assertions.assertNotEquals(expected, actual);
        Assertions.assertNotEquals(expected1, actual1);
    }

}



