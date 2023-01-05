package com.upt.doccrypt.model.file;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "folders")
@Data
public class Folder extends File{
    @OneToMany
    private List<Document> documents;

    public void addDocumentInList(Document document){
        if(documents == null) documents = new ArrayList<>();
        documents.add(document);
    }
}
