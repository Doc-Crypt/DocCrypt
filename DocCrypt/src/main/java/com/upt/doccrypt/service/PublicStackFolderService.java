package com.upt.doccrypt.service;

import com.upt.doccrypt.model.file.StackFolder;

import java.util.List;

public interface PublicStackFolderService {

    StackFolder post(StackFolder folder) throws Exception;
    List<StackFolder> getAll();
}
