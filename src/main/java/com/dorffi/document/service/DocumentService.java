package com.dorffi.document.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dorffi.document.model.Document;
import com.dorffi.document.model.DocumentRepository;

@Component
public class DocumentService {
	
	private DocumentRepository repository;
	
	@Autowired
	public DocumentService(DocumentRepository repository) {
		this.repository = repository;
	}
	
	public Iterable<Document> getDocuments(List<String> documentIds) {
		return repository.findAll(documentIds);
	}
	
	public Iterable<Document> getAllDocuments() {
		return repository.findAll();		
	}
	
	public Document saveDocument(Document document) {
		return repository.save(document);
	}
}
