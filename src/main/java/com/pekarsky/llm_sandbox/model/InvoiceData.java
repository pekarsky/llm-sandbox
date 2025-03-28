package com.pekarsky.llm_sandbox.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class InvoiceData {
    private BigDecimal amount;
    private String currency;
    
    @JsonProperty("due_date")
    private LocalDate dueDate;
    
    @JsonProperty("invoice_date")
    private LocalDate invoiceDate;
    
    @JsonProperty("invoice_number")
    private String invoiceNumber;
    
    @JsonProperty("vendor_name")
    private String vendorName;
    
    @JsonProperty("customer_name")
    private String customerName;
    
    private String job;
    
    @JsonProperty("timesheet_period_start")
    private LocalDate timesheetPeriodStart;
    
    @JsonProperty("timesheet_period_end")
    private LocalDate timesheetPeriodEnd;
} 