package com.upt.doccrypt.service;

import com.upt.doccrypt.model.file.Document;
import com.upt.doccrypt.model.file.Folder;
import com.upt.doccrypt.model.user.User;

import java.util.List;

public interface FolderService {
    Folder save(Folder folder);
    List<Folder> getAll();

    void addDocumentInFolder(Folder folder, Document document);
}
