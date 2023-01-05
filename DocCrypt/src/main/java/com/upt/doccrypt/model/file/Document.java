package com.upt.doccrypt.model.file;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "documents")
@Data
public class Document extends File{
    @Column(name = "file")
    private byte[] file;


}
