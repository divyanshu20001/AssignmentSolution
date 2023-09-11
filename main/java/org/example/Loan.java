package org.example;

import lombok.*;

import java.time.LocalDate;

/**
 * Create a Loan class with all the entities given in the problem statement.
 * */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Loan {
    private String loanId;
    private String customerId;
    private String lenderId;
    private double amount;
    private double remainingAmount;
    private LocalDate paymentDate;
    private double interestPerDay;
    private LocalDate dueDate;
    private double penaltyPerDay;
    private boolean cancelled;
}
