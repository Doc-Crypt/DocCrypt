package com.upt.doccrypt.service.impl;

import com.upt.doccrypt.model.file.Document;
import com.upt.doccrypt.model.file.Folder;
import com.upt.doccrypt.repository.file_repository.FolderRepository;
import com.upt.doccrypt.service.FolderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class FolderServiceImpl implements FolderService {
    private FolderRepository folderRepository;

    @Autowired
    public FolderServiceImpl(FolderRepository folderRepository) {
        this.folderRepository = folderRepository;
    }

    @Override
    public Folder save(Folder folder) {
        return folderRepository.save(folder);
    }

    @Override
    public List<Folder> getAll() {
        return folderRepository.findAll();
    }

    @Override
    public void addDocumentInFolder(Folder folder, Document document) {
        if(folderRepository.findById(folder.getId()) != null){
            log.info("Added as document in folder: {} successfully registered", folder);
            folder.addDocumentInList(document); return;
        }
        log.error("Added as document in folder: {} unsuccessfully registered", folder);
    }
}
