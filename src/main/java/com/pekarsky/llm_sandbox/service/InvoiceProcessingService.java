package com.pekarsky.llm_sandbox.service;

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

    public InvoiceProcessingService(ChatClient chatClient) {
        this.chatClient = chatClient;
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
            Extract invoice data from the following text. Return only the values in JSON format:
            Amount (numeric)
            Currency (3-letter code)
            Due Date (YYYY-MM-DD)
            Invoice Date (YYYY-MM-DD)
            Invoice Number
            Vendor Name
            Customer Name
            Job
            
            Timesheet Period Start (YYYY-MM-DD)
            Timesheet Period End (YYYY-MM-DD)
            
            Text to analyze:
            %s
            """, fullText);
            
        String response = chatClient.call(new Prompt(prompt)).getResult().getOutput().getContent();
        log.info("AI extracted data: {}", response);
        
        // TODO: Parse the JSON response into InvoiceData object
        return new InvoiceData(); // Placeholder
    }
} 