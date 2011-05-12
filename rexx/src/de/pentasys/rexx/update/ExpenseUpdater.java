package de.pentasys.rexx.update;

import java.util.List;

import com.thoughtworks.selenium.Selenium;

import de.pentasys.rexx.entities.expenses.Expense;

public class ExpenseUpdater {

    private final Selenium selenium;

    public ExpenseUpdater(Selenium selenium) {
        this.selenium = selenium;
    }

    public void createExpenses(List<Expense> expenses) {
        for (Expense amount : expenses) {
            createExpense(amount);
        }
    }

    private void createExpense(Expense expense) {
        selenium.click("css=img[title=Neuen Beleg anlegen]");
        selenium.waitForPageToLoad("30000");
        selenium.select("6", String.format("label=%s", expense.getVoucherType().getVoucherType()));
        selenium.waitForPageToLoad("30000");
        selenium.type("34_date", expense.getIssueDate().toString("dd.MM.YYYY"));
        selenium.type("34_time", expense.getIssueDate().toString("kk:mm"));
        selenium.type("9", expense.getAmount().toString());
        selenium.select("18", String.format("label=%s", expense.getPayment().getPayment()));

        selenium.click("css=img[title=Speichern]");
        selenium.waitForPageToLoad("30000");
    }

}
