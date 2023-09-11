package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class which will handle the storage of loans and their operations.
 * */
public class LoanStore {
    private final List<Loan> loans = new ArrayList<>();
    /**
     * Adds a loan to the collection.
     *
     * @param loan The loan to be added.
     * @throws IllegalArgumentException If the payment date is after the due date.
     */
    public void addLoan(Loan loan) {
        // Perform validation here, e.g., payment date vs. due date
        if (loan.getPaymentDate().isAfter(loan.getDueDate())) {
            throw new IllegalArgumentException("Payment date can't be greater than the due date.");
        }

        loans.add(loan);
        // Log an alert if the loan crosses the due date
        if (LocalDate.now().isAfter(loan.getDueDate())) {
            System.out.println("Alert: Loan " + loan.getLoanId() + " has crossed the due date.");
        }
    }
    /**
     * Method which returns the list of loans
     * */
    public List<Loan> getLoans() {
        return loans;
    }
    /**
     * Aggregates loans by lender, calculating the total remaining amount for each lender.
     *
     * @return A map where the keys are lender IDs and the values are the total remaining amounts for each lender.
     */
    public Map<String, Double> aggregateByLender() {
        // Implement aggregation by lender here
        return loans.stream()
                .collect(Collectors.groupingBy(Loan::getLenderId,
                        Collectors.summingDouble(Loan::getRemainingAmount)));
    }
    /**
     * Aggregates loans by interest rate, calculating the total remaining amount for each interest rate.
     *
     * @return A map where the keys are interest rates (as Double values) and the values are the total remaining amounts for each interest rate.
     */
    public Map<Double, Double> aggregateByInterest() {
        // Implement aggregation by interest here
        return loans.stream()
                .collect(Collectors.groupingBy(Loan::getInterestPerDay,
                        Collectors.summingDouble(Loan::getRemainingAmount)));
    }
    /**
     * Aggregates loans by customer ID, calculating the total remaining amount for each customer.
     *
     * @return A map where the keys are customer IDs (as String values) and the values are the total remaining amounts for each customer.
     */
    public Map<String, Double> aggregateByCustomerId() {
        // Implement aggregation by customer ID here
        return loans.stream()
                .collect(Collectors.groupingBy(Loan::getCustomerId,
                        Collectors.summingDouble(Loan::getRemainingAmount)));
    }
}
