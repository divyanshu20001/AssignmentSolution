package org.example;

import java.util.Map;
import java.time.LocalDate;


public class Runner {
    public static void main(String[] args) {
        LoanStore loanStore = new LoanStore();

        // Create Loan objects
        Loan loan1 = new Loan();
        loan1.setLoanId("L1");
        loan1.setCustomerId("C1");
        loan1.setLenderId("LEN1");
        loan1.setAmount(10000);
        loan1.setRemainingAmount(10000);
        loan1.setPaymentDate(LocalDate.of(2023, 6, 5));
        loan1.setInterestPerDay(0.01); // 0.01%
        loan1.setDueDate(LocalDate.of(2023, 7, 5));
        loan1.setPenaltyPerDay(0.01); // 0.01%
        loan1.setCancelled(false);

        // Loan 2 Details
        Loan loan2 = new Loan();
        loan2.setLoanId("L2");
        loan2.setCustomerId("C1");
        loan2.setLenderId("LEN1");
        loan2.setAmount(20000);
        loan2.setRemainingAmount(5000);
        loan2.setPaymentDate(LocalDate.of(2023, 6, 1));
        loan2.setInterestPerDay(0.01); // 0.01%
        loan2.setDueDate(LocalDate.of(2023, 8, 5));
        loan2.setPenaltyPerDay(0.01); // 0.01%
        loan2.setCancelled(false);
        // Add loans to the store
        loanStore.addLoan(loan1);
        loanStore.addLoan(loan2);

        // Perform aggregations
        Map<String, Double> lenderAggregation = loanStore.aggregateByLender();
        Map<Double, Double> interestAggregation = loanStore.aggregateByInterest();
        Map<String, Double> customerIdAggregation = loanStore.aggregateByCustomerId();

        // Print the aggregations
        System.out.println("Aggregation by Lender: " + lenderAggregation);
        System.out.println("Aggregation by Interest: " + interestAggregation);
        System.out.println("Aggregation by Customer ID: " + customerIdAggregation);
    }
}
