package com.upt.doccrypt.repository.file_repository;

import com.upt.doccrypt.model.file.Folder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FolderRepository extends JpaRepository<Folder, Long> {
    Folder findById(long id);
}
