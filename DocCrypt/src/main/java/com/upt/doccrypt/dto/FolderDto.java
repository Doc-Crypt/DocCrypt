package com.upt.doccrypt.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.upt.doccrypt.model.file.FileStatus;
import com.upt.doccrypt.model.file.Folder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FolderDto {
    private String fileName;
    private String ownerUsername;


    public Folder toFolder(){
        Folder folder = new Folder();
        folder.setFileName(fileName);
        folder.setDocuments(new ArrayList<>());
        folder.setUpdated(new Date());
        folder.setCreated(new Date());
        folder.setStatus(FileStatus.PENDING);
        return folder;
    }
}
