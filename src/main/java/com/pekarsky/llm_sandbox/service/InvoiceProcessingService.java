package com.pekarsky.llm_sandbox.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pekarsky.llm_sandbox.model.InvoiceData;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
public class InvoiceProcessingService {

    private final ChatClient chatClient;
    private final ObjectMapper objectMapper;

    public InvoiceProcessingService(ChatClient chatClient) {
        this.chatClient = chatClient;
        this.objectMapper = new ObjectMapper();
    }

    public InvoiceData processInvoice(MultipartFile file) throws IOException {
        // Read PDF content
        String fullText;
        try (PDDocument document = Loader.loadPDF(file.getBytes())) {
            PDFTextStripper stripper = new PDFTextStripper();
            fullText = stripper.getText(document);
        }
        
        // Use AI to extract invoice data
        String prompt = String.format("""
            Extract invoice and timesheet data from the following text. 
            In "Additional info" section, there is a calendar in format - array ("Day of the week abbreviated\nday of the current month") - for every day of the week in a current month
            and then array of values - one of 0.00, 0.50, 1.00, indicating whether this was a working day or not. Collect days, where values are other than 0.00 as a "working dates"
            
            Return only the values in JSON format:
            Amount (numeric)
            Currency (3-letter code)
            Due Date (YYYY-MM-DD)
            Invoice Date (YYYY-MM-DD)
            Invoice Number
            Vendor Name and Address
            Customer Name and Address
            VAT number
            Job
            Timesheet Period Start (YYYY-MM-DD)
            Timesheet Period End (YYYY-MM-DD)
            Timesheet Working Days (amount)
            Timesheet Working dates (array of dates)
            
            Text to analyze:
            %s
            """, fullText);
            
        String response = chatClient.call(new Prompt(prompt)).getResult().getOutput().getContent();
        log.info("AI extracted data: {}", response);
        
        return objectMapper.readValue(response, InvoiceData.class);
    }
} 