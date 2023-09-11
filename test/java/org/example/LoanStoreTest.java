package org.example;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.LocalDate;
import java.util.Map;
@RunWith(JUnit4.class)
public class LoanStoreTest {

    private LoanStore loanStore;

    @Before
    public void setUp() {
        loanStore = new LoanStore();
    }

    @After
    public void tearDown() {
        loanStore = null;
    }

    @Test
    public void testAddValidLoan() {
        Loan validLoan = new Loan();
        validLoan.setLoanId("L1");
        validLoan.setCustomerId("C1");
        validLoan.setLenderId("LEN1");
        validLoan.setAmount(10000);
        validLoan.setRemainingAmount(10000);
        validLoan.setPaymentDate(LocalDate.of(2023, 6, 5));
        validLoan.setInterestPerDay(0.01);
        validLoan.setDueDate(LocalDate.of(2023, 7, 5));
        validLoan.setPenaltyPerDay(0.01);
        validLoan.setCancelled(false);

        loanStore.addLoan(validLoan);

        Assert.assertEquals(1, loanStore.getLoans().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddLoanWithInvalidPaymentDate() {
        Loan invalidLoan = new Loan();
        invalidLoan.setLoanId("L2");
        invalidLoan.setCustomerId("C1");
        invalidLoan.setLenderId("LEN1");
        invalidLoan.setAmount(20000);
        invalidLoan.setRemainingAmount(5000);
        invalidLoan.setPaymentDate(LocalDate.of(2023, 8, 1)); // Payment date after due date
        invalidLoan.setInterestPerDay(0.01);
        invalidLoan.setDueDate(LocalDate.of(2023, 7, 5));
        invalidLoan.setPenaltyPerDay(0.01);
        invalidLoan.setCancelled(false);

        loanStore.addLoan(invalidLoan);
    }

    @Test
    public void testAggregateByLender() {
        Loan loan1 = new Loan();
        loan1.setLoanId("L1");
        loan1.setCustomerId("C1");
        loan1.setLenderId("LEN1");
        loan1.setAmount(10000);
        loan1.setRemainingAmount(5000); // Set remaining amount differently
        loan1.setPaymentDate(LocalDate.of(2023, 6, 5));
        loan1.setInterestPerDay(0.01);
        loan1.setDueDate(LocalDate.of(2023, 7, 5));
        loan1.setPenaltyPerDay(0.01);
        loan1.setCancelled(false);

        Loan loan2 = new Loan();
        loan2.setLoanId("L2");
        loan2.setCustomerId("C1");
        loan2.setLenderId("LEN1");
        loan2.setAmount(20000);
        loan2.setRemainingAmount(10000); // Set remaining amount differently
        loan2.setPaymentDate(LocalDate.of(2023, 6, 1));
        loan2.setInterestPerDay(0.01);
        loan2.setDueDate(LocalDate.of(2023, 8, 5));
        loan2.setPenaltyPerDay(0.01);
        loan2.setCancelled(false);

        Loan loan3 = new Loan();
        loan3.setLoanId("L3");
        loan3.setCustomerId("C2");
        loan3.setLenderId("LEN2");
        loan3.setAmount(50000);
        loan3.setRemainingAmount(30000);
        loan3.setPaymentDate(LocalDate.of(2023, 4, 4));
        loan3.setInterestPerDay(0.02);
        loan3.setDueDate(LocalDate.of(2023, 5, 4));
        loan3.setPenaltyPerDay(0.02);
        loan3.setCancelled(false);

        loanStore.addLoan(loan1);
        loanStore.addLoan(loan2);
        loanStore.addLoan(loan3);

        Map<String, Double> aggregation = loanStore.aggregateByLender();

        Assert.assertTrue(aggregation.containsKey("LEN1"));
        Assert.assertEquals(15000.0, aggregation.get("LEN1"), 0.001); // Corrected remaining amounts
        Assert.assertTrue(aggregation.containsKey("LEN2"));
        Assert.assertEquals(30000.0, aggregation.get("LEN2"), 0.001);
    }

    @Test
    public void testAggregateByInterest() {
        Loan loan1 = new Loan();
        loan1.setLoanId("L1");
        loan1.setCustomerId("C1");
        loan1.setLenderId("LEN1");
        loan1.setAmount(10000);
        loan1.setRemainingAmount(5000);
        loan1.setPaymentDate(LocalDate.of(2023, 6, 5));
        loan1.setInterestPerDay(0.01); // Same interest rate
        loan1.setDueDate(LocalDate.of(2023, 7, 5));
        loan1.setPenaltyPerDay(0.01);
        loan1.setCancelled(false);

        Loan loan2 = new Loan();
        loan2.setLoanId("L2");
        loan2.setCustomerId("C1");
        loan2.setLenderId("LEN1");
        loan2.setAmount(20000);
        loan2.setRemainingAmount(10000);
        loan2.setPaymentDate(LocalDate.of(2023, 6, 1));
        loan2.setInterestPerDay(0.01); // Same interest rate
        loan2.setDueDate(LocalDate.of(2023, 8, 5));
        loan2.setPenaltyPerDay(0.01);
        loan2.setCancelled(false);

        loanStore.addLoan(loan1);
        loanStore.addLoan(loan2);

        Map<Double, Double> aggregation = loanStore.aggregateByInterest();

        Assert.assertTrue(aggregation.containsKey(0.01));
        Assert.assertEquals(15000.0, aggregation.get(0.01), 0.001); // Corrected remaining amounts
    }

    @Test
    public void testAggregateByCustomerId() {
        Loan loan1 = new Loan();
        loan1.setLoanId("L1");
        loan1.setCustomerId("C1");
        loan1.setLenderId("LEN1");
        loan1.setAmount(10000);
        loan1.setRemainingAmount(5000);
        loan1.setPaymentDate(LocalDate.of(2023, 6, 5));
        loan1.setInterestPerDay(0.01);
        loan1.setDueDate(LocalDate.of(2023, 7, 5));
        loan1.setPenaltyPerDay(0.01);
        loan1.setCancelled(false);

        Loan loan2 = new Loan();
        loan2.setLoanId("L2");
        loan2.setCustomerId("C2"); // Different customer ID
        loan2.setLenderId("LEN1");
        loan2.setAmount(20000);
        loan2.setRemainingAmount(10000);
        loan2.setPaymentDate(LocalDate.of(2023, 6, 1));
        loan2.setInterestPerDay(0.01);
        loan2.setDueDate(LocalDate.of(2023, 8, 5));
        loan2.setPenaltyPerDay(0.01);
        loan2.setCancelled(false);

        loanStore.addLoan(loan1);
        loanStore.addLoan(loan2);

        Map<String, Double> aggregation = loanStore.aggregateByCustomerId();

        Assert.assertTrue(aggregation.containsKey("C1"));
        Assert.assertEquals(5000.0, aggregation.get("C1"), 0.001); // Corrected remaining amounts
        Assert.assertTrue(aggregation.containsKey("C2"));
        Assert.assertEquals(10000.0, aggregation.get("C2"), 0.001);
    }
}