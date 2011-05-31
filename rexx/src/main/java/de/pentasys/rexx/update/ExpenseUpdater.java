package de.pentasys.rexx.update;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.selenium.Selenium;

import de.pentasys.rexx.entities.expenses.Expense;
import de.pentasys.selenium.check.SeleniumBase;

public class ExpenseUpdater extends SeleniumBase {
	private final Logger log = LoggerFactory.getLogger(getClass());
	private final DecimalFormat amountFormat;

	public ExpenseUpdater(final Selenium selenium) {
		super(selenium);
		amountFormat = (DecimalFormat) NumberFormat.getInstance(Locale.GERMAN);
		amountFormat.applyPattern("#,###,##0.00");
	}

	private void createExpense(final Expense expense) {
		log.debug(String.format("about to create %s", expense));
		selenium.click("//img[@title='Neuen Beleg anlegen']");
		selenium.waitForPageToLoad("30000");
		selenium.select("6", String.format("label=%s", expense.getVoucherType().getVoucherType()));
		selenium.waitForPageToLoad("30000");
		selenium.type("34_date", expense.getIssueDate().toString("dd.MM.YYYY"));
		selenium.type("34_time", expense.getIssueDate().toString("kk:mm"));
		selenium.type("9", amountFormat.format(expense.getAmount()));
		selenium.select("18", String.format("label=%s", expense.getPayment().getPayment()));

		selenium.click("css=img[title=Speichern]");
		selenium.waitForPageToLoad("30000");
	}

	public void createExpenses(final List<Expense> expenses) {
		for (final Expense amount : expenses) {
			createExpense(amount);
		}
	}
}
