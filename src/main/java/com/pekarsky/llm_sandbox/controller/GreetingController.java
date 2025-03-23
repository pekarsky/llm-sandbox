package com.pekarsky.llm_sandbox.controller;

import com.pekarsky.llm_sandbox.service.InvoiceProcessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class GreetingController {

    private final InvoiceProcessingService invoiceProcessingService;

    @GetMapping("/greeting")
    public String greeting() {
        return "Hello, world!";
    }

    @PostMapping("/invoice/process")
    public ResponseEntity<String> processInvoice(@RequestParam("file") MultipartFile file) {
        try {
            invoiceProcessingService.processInvoice(file);
            return ResponseEntity.ok("Invoice processed successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error processing invoice: " + e.getMessage());
        }
    }
} 