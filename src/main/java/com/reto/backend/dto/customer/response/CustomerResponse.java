package com.reto.backend.dto.customer.response;

import com.reto.backend.model.DocumentType;

public class CustomerResponse {
    
    private Long id;
    private DocumentType documentType;
    private String documentNumber;
    private String fullName;
    private String email;

    public CustomerResponse(Long id, DocumentType documentType, String documentNumber, String fullName, String email) {
        this.id = id;
        this.documentType = documentType;
        this.documentNumber = documentNumber;
        this.fullName = fullName;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }
}
