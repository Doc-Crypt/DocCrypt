package com.upt.doccrypt.service.impl;

import com.upt.doccrypt.model.file.StackFolder;
import com.upt.doccrypt.repository.file_repository.PublicStackFolderRepository;
import com.upt.doccrypt.service.PublicStackFolderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Slf4j
public class PublicStackFolderServiceImpl implements PublicStackFolderService {
    private final PublicStackFolderRepository publicStackFolderRepository;

    @Autowired
    public PublicStackFolderServiceImpl(PublicStackFolderRepository publicStackFolderRepository) {
        this.publicStackFolderRepository = publicStackFolderRepository;
    }

    @Override
    public StackFolder post(StackFolder folder) throws Exception{
        for (StackFolder stackFolder : publicStackFolderRepository.findAll()) {
            if (stackFolder.getFolder().getId() == folder.getFolder().getId()) {
                log.info("This folder is already exist in Public Stack Folder");
                throw new Exception("This folder is already exist in Public Stack Folder");
            }
        }
        return publicStackFolderRepository.save(folder);
    }

    @Override
    public List<StackFolder> getAll() {
        return publicStackFolderRepository.findAll();
    }
}
