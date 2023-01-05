package com.upt.doccrypt.model.file;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "public_stack_folder")
@Data
public class StackFolder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(name = "created")
    private Date created;

    @LastModifiedDate
    @Column(name = "updated")
    private Date updated;

    @OneToOne
    private Folder folder;

    public static StackFolder createStackFolder(Folder folder){
        StackFolder stackFolder = new StackFolder();
        stackFolder.setFolder(folder);
        stackFolder.setCreated(new Date());
        stackFolder.setUpdated(new Date());
        return stackFolder;
    }


}
