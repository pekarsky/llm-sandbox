package com.pekarsky.llm_sandbox.model;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class InvoiceData {
    private BigDecimal amount;
    private String currency;
    private LocalDate dueDate;
    private LocalDate invoiceDate;
    private String invoiceNumber;
    private String vendorName;
    private String customerName;
    private LocalDate timesheetPeriodStart;
    private LocalDate timesheetPeriodEnd;
} 