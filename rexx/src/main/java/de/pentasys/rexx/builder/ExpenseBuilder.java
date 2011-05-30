package de.pentasys.rexx.builder;

import java.util.List;

import org.joda.time.DateTime;

import de.pentasys.rexx.entities.expenses.Expense;
import de.pentasys.rexx.entities.expenses.Payment;
import de.pentasys.rexx.entities.expenses.TaxiExpense;
import de.pentasys.rexx.entities.expenses.TrainExpense;

public class ExpenseBuilder {
    public static Expense train(final double amount, final Payment payment) {
        return new TrainExpense(amount, payment);
    }

    public static Expense taxi(final double amount, final Payment payment) {
        return new TaxiExpense(amount, payment);
    }

    private final List<Expense> expenses;
    private final DateTime inboundDate;
    private final DateTime outboundDate;

    public ExpenseBuilder(final List<Expense> expenses, final DateTime inboundDate, final DateTime outboundDate) {
        this.expenses = expenses;
        this.inboundDate = inboundDate;
        this.outboundDate = outboundDate;
    }

    public ExpenseBuilder inboundCosts(final Expense expense) {
        expense.setIssueDate(inboundDate);
        expenses.add(expense);
        return this;
    }

    public ExpenseBuilder outboundCosts(final Expense expense) {
        expense.setIssueDate(outboundDate);
        expenses.add(expense);
        return this;
    }
}
