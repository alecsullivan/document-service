package com.dorffi.document.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dorffi.document.model.Document;
import com.dorffi.document.service.DocumentService;

@RestController
@RequestMapping("/documents")
public class DocumentResource {

	private DocumentService service;

	@Autowired
	public DocumentResource(DocumentService service) {
		this.service = service;
	}

	@RequestMapping(method = RequestMethod.GET)
	public Iterable<Document> getDocuments(@RequestParam(required = false) List<String> documentIds) {
		Iterable<Document> documents;
		if (documentIds != null) {
			documents = service.getDocuments(documentIds);
		} else {
			documents = service.getAllDocuments();
		}
		return documents;
	}
}
