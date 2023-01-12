package com.upt.doccrypt.service;

import com.upt.doccrypt.model.file.Document;
import com.upt.doccrypt.model.file.Folder;
import com.upt.doccrypt.model.user.User;

import java.util.List;

public interface DocumentService {
    Document save(Document document);
    Document getById(long id);
    List<Document> getAll();
}

