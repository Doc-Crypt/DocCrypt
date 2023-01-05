package com.upt.doccrypt.repository.file_repository;

import com.upt.doccrypt.model.file.Document;
import com.upt.doccrypt.model.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    Document findById(long id);
}
